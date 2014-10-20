<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="ext/resources/css/ext-all.css" >

     <script type="text/javascript" src="ext/bootstrap.js"></script>
     <script type="text/javascript" src="ext/locale/ext-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="ext/examples/shared/example.css" >
 <script type="text/javascript" src="ext/examples/shared/examples.js"></script>
</head>
  <body>
  <div id='kmmxz'></div>
    <script>
    Ext.onReady(function(){  
    	
       
        Ext.define('Book', {  
            extend: 'Ext.data.Model',  
            fields: [  
				{name: 'BM' , type: 'string'},
				{name: 'KMMC' , type: 'string'},
				{name: 'YEFX' , type: 'string'},
				{name: 'YE' , type: 'string'}
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
                url: 'YELL',// 向该URL发送Ajax请求  
                reader: { // 使用Ext.data.reader.Json读取服务器数据  
                    type: 'json',  
                    root: 'data' // 直接读取服务器响应的data数据  
                },  
            },  
            autoLoad:true// 自动加载服务器数据  
        });  
        var grids=Ext.create('Ext.grid.Panel', {  
            title: '初始科目余额录入',  
            //columnWidth:1,
            //Height:599,   
            renderTo: 'kmmxz',  
            // 定义该表格包含的所有数据列  
            columns: [  
                { text: '科目编码', dataIndex: 'BM' , flex: 1 },
                { text: '科目名称', dataIndex: 'KMMC' , flex: 1 },
                { text: '科目方向', dataIndex: 'YEFX' , flex: 1 ,hidden:true},
                { text: '初始科目余额', dataIndex: 'YE' , flex: 1 ,
                editor: {xtype: 'numberfield',allowBlank: false,hideTrigger:true,}}
            ],  
            store: bookStore  ,
            plugins: [
          			ce=Ext.create('Ext.grid.plugin.CellEditing', {
          				clicksToEdit: 1
          			})
          		],
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [{
                    text: '保存',
    				icon:'ext/resources/ext-theme-neptune/images/grid/refresh.png',
                    handler: function(){
                    	Ext.MessageBox.confirm('提示','是否保存，保存之后，科目初始余额将不能再变动',function(id){
                    		if(id=='yes'){
                    			var rowsData = [];
                            	var count = grids.store.getCount(); 
                            	var record; 
                            	for (var i = 0; i < count; i++) { 
        	                		record = grids.store.getAt(i); 
            	                	
            	                	if (record.dirty) { 
            	                		rowsData.push(record.data); 
            	                	} 
                            	} 
                            	
                            	var jsonrowData=Ext.encode(rowsData);
                            	//alert(msg=='借贷方不平衡')
                           		Ext.Ajax.request({
           	                		url:'ADD_YELL',
           	                		params:{data:jsonrowData},
           	                		customer:'shuxing',
           	                		success:function(response){
           	                			var msg=response.responseText;
           	                			//alert(msg=='借贷方不平衡')
           	                			if(msg=='借贷方不平衡'){
           	                				Ext.Msg.alert('提示',msg);
           	                			}else{
           	   	                			Ext.Msg.alert('提示',msg,function(){  
           	   									window.location.href = "index.jsp";
           	   						        });  
           	                				
           	                			}
           	                			
           	                		},
           	                		error:function(response){
           	                			var msg=response.responseText;
           	                			Ext.Msg.alert('提示',msg);
           	                		}
           	            		});
                    		}
    					});
                    	
                    }
    			}]
    		}]
        });  
    });
    </script>
  </body>
</html>
