function DL(url,errormassage){
	Ext.onReady(function(){
		Ext.regModel('BookInfo',{
			fields:[{name:'ids'},{name:'customs'}]
		});
		
		
		var store = Ext.create('Ext.data.Store',{
			model:'BookInfo',
			proxy:{
				type:'ajax',
				url:'Listss',
				reader:new Ext.data.ArrayReader({
					model:'BookInfo'
				})
			}
		});
	
		var form=new Ext.form.Panel({
			
			type:'vbox',
			align:'center',
			height:150,
			width:380,
			frame:true,
			standardSubmit:true,
			//renderTo:'form',//form
			defaults:{
				labelSeparator:':',
				labelWidth:50,
				width:330,
				allowBlank:false,
				blankText:'不能为空',
				labelAlign:'center',
				msgTarget:'side'
			},
			items:[{
				xtype:'label',
				html:'<b><font color="red">'+errormassage+'</font></b>'
			},
			       {
				id:'customs',
				name:'custom',
				xtype:'combo',
				fieldLabel:'单位',	
				listConfig:{
					loadingText:'正在加载',
					emptyTest:'未找到',
					maxHeight:160
				},
				//allQuery:'allbook',
				//minChars:3,
				//queryParam:'searchbook',
				//queryDelay:300,
				triggerAction:'all',
				hiddenName:'custom',
				displayField:'customs',
				valueField:'ids',
				store:store,
				queryMode:'remote'
			},
			{
				xtype:'textfield',
				fieldLabel:'用户',
				width:330,
				minLength:4,
				maxLength:25,
				name:'username'
			},{
				xtype:'textfield',
				fieldLabel:'密码',
				width:330,
				minLength:5,
				maxLength:30,
				allowBlank:true,
				inputType:'password',
				name:'password'
			}],
			buttons:[{
				text:'登录',
				handler:function(){
					form.getForm().submit({
						clientValidation:true,
						url:url,
						method:'POST'
					})
				}
			},{
				text:'重置',
				handler:function(){
					form.form.reset();
				}
			},{
				text:'帐套管理',
				handler:function(){
					window.location.href = "build.jsp";
				}
			}]
		});
		
		var mypanel=new Ext.Panel({
			title:'登录',
			id:'mypanel',
			renderTo:Ext.getBody(),
			collapsible:true,
			floating:true,
			items:[form],
			width:382,
			height:175
		});
	
	});
}