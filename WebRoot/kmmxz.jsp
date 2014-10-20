<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head> </head>
  <body>
  <div id='kmmxz'></div>
    <script>
    Ext.onReady(function(){  
    	var jm_year_month=document.getElementById('downc').innerHTML.
			split('：')[document.getElementById('downc').innerHTML.split('：').length-1];
    	var mouth=[{"name":"1"},{"name":"2"},{"name":"3"},{"name":"4"},{"name":"5"},{"name":"6"},{"name":"7"},{"name":"8"},{"name":"9"},{"name":"10"},{"name":"11"},{"name":"12"}];
    	
    	var mxz=new Ext.form.Panel({
         	frame:true,
         	defaults:{
         		labelSeparator:'：',
         		allowBlank:false,
         		blankText:'不允许为空',
         		labelAlign:'left',
         		msgTarget:'side'
         	},
         	items:[{
    			xtype:'label',
                html: '请选择初始年月<br>'
    		},{
    			xtype:'combo',
         		fieldLabel:'年',
         		store: Ext.create('Ext.data.Store', {
         		    fields: [{name:'year'}],
         		   proxy:{
    					type:'ajax',
    					url:'Year',
    					reader:new Ext.data.ArrayReader({
    						fields: [{name:'year'}]
    					})
    				},
    				autoLoad:true
         		}),
         		editable:false,
        		value:jm_year_month.substring(0,4),
         		autoSelect:true,
         	    queryMode: 'local',
    			//hiddenName:'BM',
         	    displayField: 'year',
         	    name:'ksyear'
    		},{
         		xtype:'combo',
         		fieldLabel:'月',
         		store: Ext.create('Ext.data.Store', {
         		    fields: ['abbr', 'name'],
         		    data : mouth
         		}),
         		editable:false,
        		value:jm_year_month.substring(4),
         		autoSelect:true,
         	    queryMode: 'local',
         	    displayField: 'name',
         	    //valueField: 'abbr',
         		name:'ksmouth'
         	},{
    			xtype:'label',
                html: '请选择截止年月<br>'
    		},{
    			xtype:'combo',
         		fieldLabel:'年',
         		store: Ext.create('Ext.data.Store', {
         		    fields: [{name:'year'}],
         		   proxy:{
    					type:'ajax',
    					url:'Year',
    					reader:new Ext.data.ArrayReader({
    						fields: [{name:'year'}]
    					})
    				},
    				autoLoad:true
         		}),
         		editable:false,
        		value:jm_year_month.substring(0,4),
         		autoSelect:true,
         	    queryMode: 'local',
    			//hiddenName:'BM',
         	    displayField: 'year',
         	   name:'jzyear'
    		},{
         		xtype:'combo',
         		fieldLabel:'月',
         		store: Ext.create('Ext.data.Store', {
         		    fields: ['abbr', 'name'],
         		    data : mouth
         		}),
         		editable:false,
        		value:jm_year_month.substring(4),
         		autoSelect:true,
         	    queryMode: 'local',
         	    displayField: 'name',
         	    //valueField: 'abbr',
         	   name:'jzmouth'
         	},{
    			xtype:'combo',
         		fieldLabel:'科目',
    			//hideTrigger :true,
         		store: Ext.create('Ext.data.Store', {
         		    fields: [{name:'BMb'},{name:'KMMCb'}],
         		   proxy:{
    					type:'ajax',
    					url:'ALL_JSON_KM',
    					reader:new Ext.data.ArrayReader({
    						fields: [{name:'BMb'},{name:'KMMCb'}]
    					})
    				},
    				autoLoad:true
         		}),
         		forceSelection:true,
         		autoSelect:true,
         	    queryMode: 'local',
         	    valueField:'BMb',
         	    displayField: 'KMMCb',
         	    name:'BM'
    		}],
         	buttons:[{
     				text:'确定',
     				handler:function(){
     					//alert(mxz.getForm().findField('ksyear').getValue());
     					if(mxz.getForm().findField('BM').getSubmitValue( )==""){
     						Ext.Msg.alert('提示','请选择科目');
     						return;
     					}
     					bookStore.load({
     					    params: {
     					    	ksyear: mxz.getForm().findField('ksyear').getValue(),
     					    	ksmouth: mxz.getForm().findField('ksmouth').getValue(),
     					    	jzyear: mxz.getForm().findField('jzyear').getValue(),
     					    	jzmouth: mxz.getForm().findField('jzmouth').getValue(),
     					    	BM: mxz.getForm().findField('BM').getSubmitValue( ),
     					    },
     					    callback: function(records, operation, success) {
     					        // do something after the load finishes
     					    },
     					    scope: this
     					});
     					//bookStore.load();
     					win.close()
     				}
     			}]
         });
        
        win=new Ext.Window({  
            title:'科目明细账',  
            width:300,  
            height:270,  
            closeAction:'hide',  
            plain:true,  
            layout:'fit',
            constrain:true,
            //resizable:true,
            modal:true,
            items:[mxz]
    	});
       win.show();
       
        Ext.define('Book', {  
            extend: 'Ext.data.Model',  
            fields: [  
				{name: 'year' , type: 'string'},
				{name: 'mouth' , type: 'string'},
				{name: 'day' , type: 'string'},
				{name: 'PZH' , type: 'string'},
                {name: 'ZY' , type: 'string'},  
                {name: 'BM', type: 'string'},  
                {name: 'KMMC', type: 'string'},  
                {name: 'JF', type: 'string'}, 
                {name: 'DF', type: 'string'},  
                {name: 'HJ', type: 'string'}
            ]  
        });  
        // 创建一个Ext.data.Store对象  
        var bookStore = Ext.create('Ext.data.Store',   
        {  
            // 指定使用Book Model管理记录  
            model: 'Book',  
            // 使用proxy指定加载远程数据  
            proxy:   
            {  
                type: 'ajax',  
                url: 'Detail_Account',// 向该URL发送Ajax请求  
                reader: { // 使用Ext.data.reader.Json读取服务器数据  
                    type: 'json',  
                    root: 'data' // 直接读取服务器响应的data数据  
                },  
            },  
            //autoLoad:true// 自动加载服务器数据  
        });  
        var grids=Ext.create('Ext.grid.Panel', {  
            title: '科目明细账',  
            //columnWidth:1,
            //columnHeight:.99,   
            renderTo: 'kmmxz',  
            // 定义该表格包含的所有数据列  
            columns: [  
                { text: '年', dataIndex: 'year' ,width: 50},
                { text: '月', dataIndex: 'mouth' , width: 30},
                { text: '日', dataIndex: 'day' , width: 30},
                { text: '凭证号', dataIndex: 'PZH' , width: 50},
                { text: '摘要', dataIndex: 'ZY' , flex: 1 }, // 第1个数据列  
                { text: '编码', dataIndex: 'BM', width: 90}, // 第3个数据列  
                { text: '科目名称', dataIndex: 'KMMC' , flex: 1 },
                { text: '借', dataIndex: 'JF' , flex: 1 },
                { text: '贷', dataIndex: 'DF' , flex: 1 },
                { text: '合计金额', dataIndex: 'HJ' , flex: 1 }
            ],  
            store: bookStore  ,
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [{
                    text: '查找',
    				icon:'ext/resources/ext-theme-neptune/images/grid/refresh.png',
                    handler: function(){
    					win.show()
                    }
    			}]
    		}]
        });  
    });
    </script>
  </body>
</html>
