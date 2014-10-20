<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
<div align="center" id="yhgltable" name="yhgltable"></div>
<script>
Ext.onReady(function(){  
	    
    Ext.define('Book', {  
        extend: 'Ext.data.Model',  
        fields: [  
            {name: 'ZH' , type: 'string'},  
            {name: 'MM', type: 'string'},  
            {name: 'ZD', type: 'string'},  
            {name: 'CX', type: 'string'}, 
            {name: 'ZZ', type: 'string'}, 
            {name: 'SJ', type: 'string'},  
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
            url: 'Use',// 向该URL发送Ajax请求  
            reader: { // 使用Ext.data.reader.Json读取服务器数据  
                type: 'json',  
                root: 'data' // 直接读取服务器响应的data数据  
            },  
        },  
        autoLoad:true// 自动加载服务器数据  
    });  
    var grids=Ext.create('Ext.grid.Panel', {  
        title: '用户查询',  
        //columnWidth:1,
        //columnHeight:.99,   
        renderTo: 'yhgltable',  
        // 定义该表格包含的所有数据列  
        columns: [  
            { text: '用户名', dataIndex: 'ZH' , flex: 1 }, // 第1个数据列  
            { text: '制单权限', dataIndex: 'ZD', flex: 1 }, // 第3个数据列  
            { text: '查询权限', dataIndex: 'CX' , flex: 1 },
            { text: '结转/审核权限', dataIndex: 'ZZ' , flex: 1 },
            { text: '建立时间', dataIndex: 'SJ' , flex: 1 }
        ],  
        store: bookStore  ,
        dockedItems: [{
            dock: 'top',
            xtype: 'toolbar',
            items: [{
                //tooltip: 'Toggle the visibility of the summary row',
                text: '添加',
                //enableToggle: true,
                //pressed: true,
				icon:'ext/resources/ext-theme-neptune/images/grid/group-expand.png',
                handler: function(){
					win.show();
                }
            },{
                text: '删除',
				icon:'ext/resources/ext-theme-neptune/images/grid/group-collapse.png',
                handler: function(){
                	var stmp;
                	if(grids.getSelectionModel().getSelection().length!=0){
                		stmp=grids.getSelectionModel().getSelection()[0].get('ZH');
	                	Ext.MessageBox.confirm('提示','请确认是否删除',function(id){
	                		if(id=='yes'){
	                			Ext.Ajax.request({
			                		url:'DelUser',
			                		params:{username:stmp},
			                		customer:'shuxing',
			                		success:function(response){
			                			var msg=response.responseText;
			                			Ext.Msg.alert('提示',msg);
			                			bookStore.load();
			                		}
		                		});
	                		}
	                	});
                	}else{
                		Ext.Msg.alert('提示','请选择删除的条目');
                	}
                }
			},{
                text: '刷新',
				icon:'ext/resources/ext-theme-neptune/images/grid/refresh.png',
                handler: function(){
					bookStore.load();
                }
			},{
                text: '修改权限',
				icon:'ext/resources/ext-theme-neptune/images/grid/drop-yes.png',
                handler: function(){
                	if(grids.getSelectionModel().getSelection().length!=0){
                		ZH=grids.getSelectionModel().getSelection()[0].get('ZH');
                		update1form.getForm().findField('use').setValue(ZH)
                		ZD=grids.getSelectionModel().getSelection()[0].get('ZD');
                		if(ZD=='1')
                			update1form.getForm().findField('ZD').setValue(true);
                		else
                			update1form.getForm().findField('ZD').setValue(false);
                		CX=grids.getSelectionModel().getSelection()[0].get('CX');
                		if(CX=='1')
                			update1form.getForm().findField('CX').setValue(true);
                		else
                			update1form.getForm().findField('CX').setValue(false);
                		ZZ=grids.getSelectionModel().getSelection()[0].get('ZZ');
                		if(ZZ=='1')
                			update1form.getForm().findField('ZZ').setValue(true);
                		else
                			update1form.getForm().findField('ZZ').setValue(false);
						//wind=null;
                		
	                	wind.show();
	                	
                	}
                }
			},{
                text: '修改密码',
				icon:'ext/resources/ext-theme-neptune/images/grid/drop-yes.png',
                handler: function(){
                	updateP.form.reset();
                	if(grids.getSelectionModel().getSelection().length!=0){
                		ZH=grids.getSelectionModel().getSelection()[0].get('ZH');
                		updateP.getForm().findField('use').setValue(ZH)
						//wind=null;
                		
	                	winP.show();
	                	
                	}
                }
			},{
				xtype:'label',
                html: '   <font color="red">提示：1为拥有权限，0为没有权限</font>'
			}]
		}]
    });  
    
    
    var addform=new Ext.form.Panel({
    	id:'addform',
    	frame:true,
    	defaults:{
    		labelSeparator:'：',
    		allowBlank:false,
    		blankText:'不允许为空',
    		labelAlign:'left',
    		msgTarget:'side'
    	},
    	items:[{
    		xtype:'textfield',
    		fieldLabel:'用户名',
			minLength:4,
			maxLength:25,
    		name:'use'
    	},{
    		xtype:'textfield',
    		fieldLabel:'密码',
    		allowBlank:true,
    		inputType:'password',
			minLength:5,
			maxLength:30,
    		name:'password'
    	},{
    		xtype:'checkboxgroup',
    		fieldLabel:'权限',
    		columns:2,
    		items:[
    			{boxLabel:'制单权限',name:'ZD'},
    			{boxLabel:'查询权限',name:'CX'},
    			{boxLabel:'结转权限',name:'ZZ'}
    		]
    	}],
    	buttons:[{
				text:'保存',
				handler:bc
			},{
				text:'取消',
				handler:function(){
					addform.form.reset();
					win.close();
				}
			}]
    });
	function bc(){
		addform.getForm().submit({
			clientValidation:true,
			url:'AddUser',
			method:'post',
			success:function(form,action){
				Ext.Msg.alert('提示',action.result.successMessage);
				addform.form.reset();
				win.close();
				bookStore.load();
			},
			failure:function(form,action){
				Ext.Msg.alert('提示',action.result.errorMessage);
				addform.form.reset();
			}
		});
	}
	
	
    var update1form=new Ext.form.Panel({
    	frame:true,
    	defaults:{
    		labelSeparator:'：',
    		allowBlank:false,
    		blankText:'不允许为空',
    		labelAlign:'left',
    		msgTarget:'side'
    	},
    	items:[{
    		xtype:'textfield',
    		readOnly:true,
    		fieldLabel:'用户名',
			minLength:4,
			maxLength:25,
    		name:'use'
    	},{
    		xtype:'checkboxgroup',
    		fieldLabel:'权限',
    		columns:2,
    		items:[
    			{boxLabel:'制单权限',name:'ZD'},
    			{boxLabel:'查询权限',name:'CX'},
    			{boxLabel:'结转权限',name:'ZZ'}
    		]
    	}],
    	buttons:[{
				text:'保存',
				handler:function(){
					update1form.getForm().submit({
						clientValidation:true,
						url:'UpdataUser',
						method:'post',
						success:function(form,action){
							Ext.Msg.alert('提示',action.result.successMessage);
							update1form.form.reset();
							wind.close();
							bookStore.load();
						},
						failure:function(form,action){
							Ext.Msg.alert('提示',action.result.errorMessage);
							update1form.form.reset();
							wind.close();
							bookStore.load();
						}
					});
				}
			}]
    });
    
    var updateP=new Ext.form.Panel({
    	frame:true,
    	defaults:{
    		labelSeparator:'：',
    		allowBlank:false,
    		blankText:'不允许为空',
    		labelAlign:'left',
    		msgTarget:'side'
    	},
    	items:[{
    		xtype:'textfield',
    		readOnly:true,
    		fieldLabel:'用户名',
			minLength:4,
			maxLength:25,
    		name:'use'
    	},{
    		xtype:'textfield',
    		fieldLabel:'原密码',
    		allowBlank:true,
    		inputType:'password',
			minLength:5,
			maxLength:30,
    		name:'Ypassword'
    	},{
    		xtype:'textfield',
    		fieldLabel:'新密码',
    		allowBlank:true,
    		inputType:'password',
			minLength:5,
			maxLength:30,
    		name:'password'
    	},{
    		xtype:'textfield',
    		fieldLabel:'再次确认密码',
    		allowBlank:true,
    		inputType:'password',
			minLength:5,
			maxLength:30,
    		name:'passworded'
    	}],
    	buttons:[{
				text:'保存',
				handler:function(){
                	password0=grids.getSelectionModel().getSelection()[0].get('MM');
					var use=updateP.getForm().findField('use').getValue()
					var password1=updateP.getForm().findField('Ypassword').getValue()
					var password2=updateP.getForm().findField('password').getValue()
					var password3=updateP.getForm().findField('passworded').getValue()
					//alert(password1);
					if(password1!=password0){
						Ext.Msg.alert('提示','原密码不一致，请重新输入');
						return;
					}
					if(password2!=password3){
						Ext.Msg.alert('提示','两次输入的密码不一致，请重新输入');
						return;
					}
					updateP.getForm().submit({
						clientValidation:true,
						url:'Updatepassword',
						method:'post',
						success:function(form,action){
							Ext.Msg.alert('提示',action.result.successMessage);
							updateP.form.reset();
							winP.close();
							bookStore.load();
						},
						failure:function(form,action){
							Ext.Msg.alert('提示',action.result.errorMessage);
							updateP.form.reset();
							winP.close();
							bookStore.load();
						}
				})
			}
		}]
    });
    win=new Ext.Window({  
        title:'添加用户',  
        width:300,  
        height:175,  
        closeAction:'hide',  
        plain:true,  
        layout:'fit',
        constrain:true,
        resizable:true,
        modal:true,
        //maximizable:true,
        items:[addform]
	});
    
    wind=new Ext.Window({  
        title:'修改权限',  
        width:300,  
        height:175,  
        closeAction:'hide',  
        plain:true,  
        layout:'fit',
        constrain:true,
        resizable:true,
        modal:true,
        //maximizable:true,
        items:[update1form]
	});
    
    winP=new Ext.Window({  
        title:'修改密码',  
        width:300,  
        height:175,  
        closeAction:'hide',  
        plain:true,  
        layout:'fit',
        constrain:true,
        resizable:true,
        modal:true,
        //maximizable:true,
        items:[updateP]
	});
});
</script>
</body>
</html>
