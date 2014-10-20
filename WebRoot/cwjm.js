
Ext.onReady(function(){
	Ext.Loader.setConfig({enabled: true});

	Ext.Loader.setPath('Ext.ux', 'ext/ux/');
	Ext.Loader.setPath('Ext.example','ext/shared/');
	Ext.require([
		'Ext.ux.TabReorderer',
		'Ext.example.msg'
	]);
	tabs = Ext.create('Ext.tab.Panel', {
		id:'tabss',
	region:'center',
	//frame:true,
	//closable:true,
	//layout:'column',
	autoScroll:true,
	enableTabScroll: true,
	//bodyStyle: 'padding:5px',
	plugins: [Ext.create('Ext.ux.TabReorderer')],
    items: [
        {
		//closable:true,
		title: '欢迎!~~',
		html : '欢迎!~~',
        }
    ],
    //renderTo : 'tab'
});


	 var boardcenter = new Ext.Panel({
		layout:'border',
		id : 'boardcenter',
	  //autoLoad : {url : 'index.html',scripts: true},
	  //title : '公告',
		region : 'center',
		frame:true,
		items:[tabs]
	 });
	 var store = Ext.create('Ext.data.TreeStore', {
			//model:'OrgInfo',
			//nodeParam:'BM',
          proxy: {
              type: 'ajax',
              url: 'CW' 
          },
          autoLoad:true,
          //root: {
          //    text: '科目',
          //    id: 'id'                     
          //}
         
          
      });
	 
	 var tree=Ext.create('Ext.tree.Panel', {
		 region:'center',
		 //frame:true,
		//width: 150,
	    //height: 100%,
		store: store,
	    rootVisible: false,
		autoScroll:true,
	    //renderTo: Ext.getBody(),
	    listeners:{itemclick:function(v,r,item){
			if(Ext.getCmp(r.data.text)){
				tabs.setActiveTab(r.data.text);
				return;
			}
			if(r.data.leaf){
				tab = tabs.add({
				// we use the tabs.items property to get the length of current items/tabs
					id: r.data.text,
					title: r.data.text,
					autoScroll:true,
					layout:'fit',
					closable:true,
					html:'<center><br><br><br><br><br><img src="ext/resources/ext-theme-classic/images/grid/wait.gif" /><font size="3" color="red">请稍后....</font></center>',
					loader: { url: r.data.id+'.jsp', params: { data: "从客户端传入的参数" }, method: 'GET',scripts:true},
					listeners: { 
						activate: function(tab){ 
							//tab.loader.load(); 
						} 
					}
				});
				tabs.setActiveTab(r.data.text);
				Ext.example.msg('提示','\"'+r.data.text+'\" 标签创建成功');
				document.getElementById('downw').innerHTML='\"'+r.data.text+'\" 标签创建成功';
				tab.loader.load();
			}
	    }}
            
	});
	
	 var boardleft=new Ext.Panel({
		 id : 'boardleft',region:'west',width:180,
		 layout:'border', 
		 title:'选项：',
		 frame:true,
		 //layout:'border',
		 items:[tree]
	 });
	 
	 var image = Ext.create('Ext.Img', {
		 frame:true,
		src: 'image/BT2.jpg'
	});
	 
	 //var bsl1=new Ext.Panel({id:'bsl1',region:'left',html:"<"});
	 var d=new Date();
	 var boardsouth=new Ext.Panel({
			//html:"<div id='down' name='down'>dowm</div>",
			id:'boardsouth',
			layout:'border', 
			region:'south',
			height:25,
			frame:true,
			items:[{
				xtype: 'label',
				html: "<div id='downw' name='downw'>west</div>",
				width:200,
				region:'west'
			}, {
				xtype: 'label',
				html: d.toLocaleDateString(),
				region:'east',
				width:90
			}, {
				xtype: 'label',
				html: "<b><div id='downc' name='downc'>center</div></b>",
				region:'center'
			}]
		});
	 
	 
	Ext.create('Ext.container.Viewport',{
		frame:true,
		layout:'border',
		items:[{region:'north',height:70,items:[image],frame:true,},
		boardsouth,boardleft,boardcenter]
	});
	
	//}
	Ext.Ajax.request({
		url: 's.jsp',
		callback: function(options,success,response){
			Ext.example.msg('提示',response.responseText);
			//Ext.Msg.alert('登录信息',response.responseText);
			document.getElementById('downc').innerHTML = response.responseText;
			// process server response here
    }
});
	window.onresize = function () {
		tab.loader.load();
    };
});

