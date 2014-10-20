<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><html>
<head>
<link rel="stylesheet" type="text/css" href="ext/resources/css/ext-all.css" >

     <script type="text/javascript" src="ext/bootstrap.js"></script>
     <script type="text/javascript" src="ext/locale/ext-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="ext/examples/shared/example.css" >
 <script type="text/javascript" src="ext/examples/shared/examples.js"></script>
</head>
<body>
<div style="width:100%;height:100%;padding:0px;margin:0px;"  align="center" id="kmgltable" name="kmgltable"></div>
<script>
Ext.onReady(function(){  
	Ext.regModel('OrgInfo',{
		fields:['BM','KMMC','FL','YEFX','biao']
	});
	var FL=[{"name":"资产类"},{"name":"负债类"},{"name":"损益类"},{"name":"所有者权益类"},{"name":"成本类"}];
	var YEFX=[{"name":"借"},{"name":"贷"}];
    var store = Ext.create('Ext.data.TreeStore', {
    			model:'OrgInfo',
    			nodeParam:'BM',
                 proxy: {
                     type: 'ajax',
                     url: 'KM' 
                 },
                 autoLoad:true,
                 root: {
                     text: '科目',
                     id: 'id'                     
                 }
                
                 
             });

             var tree = Ext.create('Ext.tree.Panel', {
             	//title:'科目查看',
                 id: 'tree',
                 store: store,
                 rootVisible:false,
                 useArrows:true,
                 autoScroll:true,
                 //region:'center',
                 //columnWidth:1,
                 //columnHeight:.99,  
                 width:Ext.get('kmgltable').getViewSize().width,
                 height:Ext.get('kmgltable').getViewSize().height,
                 renderTo: 'kmgltable',
                 columns:[{
                 	xtype:'treecolumn',
                 	text:'科目代码',
                 	dataIndex:'BM',
                 	width:150,
                 	sortable:true
                 },{
                 	text:'科目名称',
                 	dataIndex:'KMMC',
                 	flex:1,
                 	sortable:true
                 },{
                 	text:'类别',
                 	dataIndex:'FL',
                 	flex:1,
                 	sortable:true
                 },{
                 	text:'余额方向',
                 	dataIndex:'YEFX',
                 	flex:1,
                 	sortable:true
                 },{
                 	text:'资产负债表/损益表',
                 	dataIndex:'biao',
                 	flex:1,
                 	sortable:true
                 }],
                 dockedItems: [{
		            dock: 'top',
		            xtype: 'toolbar',
		            items: [{
		                //tooltip: 'Toggle the visibility of the summary row',
		                text: '添加',
		                //disabled : !ZZ,
		                //enableToggle: true,
		                //pressed: true,
						icon:'ext/resources/ext-theme-neptune/images/grid/group-expand.png',
		                handler: function(){
		                	w.show();
		                	//alert(add.getForm( ).findField('YEFX').getstore.store.getCount());
		                }
		            },{
		                text: '删除',
		                //disabled : !ZZ,
						icon:'ext/resources/ext-theme-neptune/images/grid/group-collapse.png',
		                handler: function(){
		                	var stmp;
		                	if(tree.getSelectionModel().getSelection().length!=0){
		                		stmp=tree.getSelectionModel().getSelection()[0].get('BM');
			                	Ext.MessageBox.confirm('提示','请确认是否删除',function(id){
			                		if(id=='yes'){
			                			Ext.Ajax.request({
					                		url:'Delkm',
					                		params:{BM:stmp},
					                		customer:'shuxing',
					                		success:function(response){
					                			var msg=response.responseText;
					                			Ext.Msg.alert('提示',msg);
					                			store.load();
					                		}
				                		});
			                		}
			                	});
		                	}else{
		                		Ext.Msg.alert('提示','请选择删除的科目');
		                	}
		                }
					},{
		                text: '刷新',
						icon:'ext/resources/ext-theme-neptune/images/grid/refresh.png',
		                handler: function(){
							store.load();
		                }
					},{
		                text: '修改',
		                //disabled : !ZZ,
						icon:'ext/resources/ext-theme-neptune/images/grid/drop-yes.png',
		                handler: function(){
		                	if(tree.getSelectionModel().getSelection().length!=0){
		                		cBM=tree.getSelectionModel().getSelection()[0].get('BM');
		                		cKMMC=tree.getSelectionModel().getSelection()[0].get('KMMC');
		                		cFL=tree.getSelectionModel().getSelection()[0].get('FL');
		                		cYEFX=tree.getSelectionModel().getSelection()[0].get('YEFX');
		                		cBIAO=tree.getSelectionModel().getSelection()[0].get('biao');
		                		kmupdata.getForm().findField('BM').setValue(cBM);
		                		kmupdata.getForm().findField('KMMC').setValue(cKMMC);
		                		kmupdata.getForm().findField('FL').setValue(cFL);
		                		kmupdata.getForm().findField('YEFX').setValue(cYEFX);
		                		//kmupdata.getForm().findField('biao').setValue(cbiao);
		                		//alert(1);
								//wind=null;
			                	kmupdataw.show();
			                	
		                	}
		                	
		                }
					},{
						xtype:'label',
		                html: '<font color="red"></font>'
					},{
		                text: '<font color="red">下一步</font>',
						icon:'ext/resources/ext-theme-neptune/images/grid/refresh.png',
		                handler: function(){
		                	window.location.href = "YELL.jsp";
		                }
					}]
				}]
             });
             
     var add=new Ext.form.Panel({
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
            html: '   <font color="red">提示：科目编码4位为一级科目，6位为二级科目，<br>8位为三级科目，最高为三级科目</font>'
		},{
     		xtype:'numberfield',
     		fieldLabel:'科目编码',
 			allowDectimals:false,
 			minValue:1001,
 			maxValue:99999999,
 			hideTrigger:true,
     		name:'BM'
     	},{
     		xtype:'textfield',
     		fieldLabel:'科目名称',
 			maxLength:30,
     		name:'KMMC'
     	},{
     		xtype:'combo',
     		fieldLabel:'科目分类',
     		store: Ext.create('Ext.data.Store', {
     		    fields: ['abbr', 'name'],
     		    data : FL
     		}),
     		forceSelection:true,
     		autoSelect:true,
     	    queryMode: 'local',
     	    displayField: 'name',
     	    //valueField: 'abbr',
     		name:'FL'
     	},{
     		xtype:'combo',
     		fieldLabel:'金额方向',
     		store: Ext.create('Ext.data.Store', {
     		    fields: ['abbr', 'name'],
     		    data : YEFX
     		}),
     		forceSelection:true,
     		autoSelect:true,
     	    queryMode: 'local',
     	    displayField: 'name',
     		name:'YEFX'
     	},{
			xtype:'combo',
			fieldLabel:'资产负债表/损益表',
     		store: Ext.create('Ext.data.Store', {
     		    fields: [{name:'kmxh'},{name:'MC'}],
     		   proxy:{
					type:'ajax',
					url:'BIAO',
					reader:new Ext.data.ArrayReader({
						fields: [{name:'kmxh'},{name:'MC'}]
					})
				},
				autoLoad:true
     		}),
     		forceSelection:true,
     		autoSelect:true,
     	    queryMode: 'local',
			//hiddenName:'BM',
			valueField:'kmxh',
     	    displayField: 'MC',
			allowBlank: false,
			name:'biao'
     	}],
     	buttons:[{
 				text:'保存',
 				handler:function(){
 					var stmp=add.getForm().findField('BM').getValue();
 					stmp=stmp.toString();
 					if(stmp.length!=4&&stmp.length!=6&&stmp.length!=8){
 						Ext.Msg.alert('提示','请输入正确的科目编码，科目编码4位为一级科目，6位为二级科目，8位为三级科目，最高为三级科目');
 						return;
 					}
 						
 					add.getForm().submit({
						clientValidation:true,
						url:'Addkm',
						method:'post',
						success:function(form,action){
							Ext.Msg.alert('提示',action.result.successMessage);
							add.getForm( ).reset( );
							w.close();
							store.load();
						},
						failure:function(form,action){
							Ext.Msg.alert('提示',action.result.errorMessage);
							//add.form.reset();
							//store.load();
						}
 					});
 				}
 			},{
 				text:'取消',
 				handler:function(){
 					//alert(add.getForm( ).findField('BM'));
 					add.getForm( ).reset( );
 					w.close();
 				}
 			}]
     });
     
     w=new Ext.Window({  
         title:'添加科目',  
         width:300,  
         height:240,  
         closeAction:'hide',  
         plain:true,  
         layout:'fit',
         constrain:true,
         resizable:true,
         modal:true,
         items:[add]
 	});
     
     var kmupdata=new Ext.form.Panel({
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
             html: '   <font color="red">提示：科目编码4位为一级科目，6位为二级科目，<br>8位为三级科目，最高为三级科目</font>'
 		},{
      		xtype:'numberfield',
      		fieldLabel:'科目编码',
      		readOnly:true,
  			allowDectimals:false,
  			minValue:1001,
  			maxValue:99999999,
  			hideTrigger:true,
      		name:'BM'
      	},{
      		xtype:'textfield',
      		fieldLabel:'科目名称',
  			maxLength:30,
      		name:'KMMC'
      	},{
      		xtype:'combo',
      		fieldLabel:'科目分类',
      		store: Ext.create('Ext.data.Store', {
      		    fields: ['abbr', 'name'],
      		    data : FL
      		}),
      		forceSelection:true,
      		autoSelect:true,
      	    queryMode: 'local',
      	    displayField: 'name',
      	    //valueField: 'abbr',
      		name:'FL'
      	},{
      		xtype:'combo',
      		fieldLabel:'金额方向',
      		store: Ext.create('Ext.data.Store', {
      		    fields: ['abbr', 'name'],
      		    data : YEFX
      		}),
      		forceSelection:true,
      		autoSelect:true,
      	    queryMode: 'local',
      	    displayField: 'name',
      		name:'YEFX'
      	},{
 			xtype:'combo',
 			fieldLabel:'资产负债表/损益表',
      		store: Ext.create('Ext.data.Store', {
      		    fields: [{name:'kmxh'},{name:'MC'}],
      		   proxy:{
 					type:'ajax',
 					url:'BIAO',
 					reader:new Ext.data.ArrayReader({
 						fields: [{name:'kmxh'},{name:'MC'}]
 					})
 				},
 				autoLoad:true
      		}),
      		forceSelection:true,
      		autoSelect:true,
      	    queryMode: 'local',
 			//hiddenName:'BM',
 			valueField:'kmxh',
      	    displayField: 'MC',
 			name:'biao'
      	}],
      	buttons:[{
  				text:'保存',
  				handler:function(){
  					var stmp=kmupdata.getForm().findField('BM').getValue();
  					stmp=stmp.toString();
  					if(stmp.length!=4&&stmp.length!=6&&stmp.length!=8){
  						Ext.Msg.alert('提示','请输入正确的科目编码，科目编码4位为一级科目，6位为二级科目，8位为三级科目，最高为三级科目');
  						return;
  					}
  					
  						
  					kmupdata.getForm().submit({
 						clientValidation:true,
 						url:'Updatakm',
 						method:'post',
 						success:function(form,action){
 							Ext.Msg.alert('提示',action.result.successMessage);
 							kmupdata.getForm( ).reset( );
 							kmupdataw.close();
 							store.load();
 						},
 						failure:function(form,action){
 							Ext.Msg.alert('提示',action.result.errorMessage);
 							//add.form.reset();
 							//store.load();
 						}
  					});
  				}
  			},{
  				text:'取消',
  				handler:function(){
  					//alert(add.getForm( ).findField('BM'));
  					kmupdata.getForm( ).reset( );
  					kmupdataw.close();
  				}
  			}]
      });
      
      kmupdataw=new Ext.Window({  
          title:'修改科目',  
          width:300,  
          height:240,  
          closeAction:'hide',  
          plain:true,  
          layout:'fit',
          constrain:true,
          resizable:true,
          modal:true,
          items:[kmupdata]
  	});
});
</script>
</body>
</html>
