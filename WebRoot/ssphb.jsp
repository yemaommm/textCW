<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head> </head>
  <body>
  <div id='ssphb'></div>
    <script>
    Ext.onReady(function(){  
    	var jm_year_month=document.getElementById('downc').innerHTML.
			split('：')[document.getElementById('downc').innerHTML.split('：').length-1];
    	var mouth=[{"name":"1"},{"name":"2"},{"name":"3"},{"name":"4"},{"name":"5"},{"name":"6"},{"name":"7"},{"name":"8"},{"name":"9"},{"name":"10"},{"name":"11"},{"name":"12"}];
    	
    	var ssphb=new Ext.form.Panel({
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
         	}],
         	buttons:[{
     				text:'确定',
     				handler:function(){
     					//alert(mxz.getForm().findField('ksyear').getValue());
     					ssphbStore.load({
     					    params: {
     					    	ksyear: ssphb.getForm().findField('ksyear').getValue(),
     					    	ksmouth: ssphb.getForm().findField('ksmouth').getValue(),
     					    	jzyear: ssphb.getForm().findField('jzyear').getValue(),
     					    	jzmouth: ssphb.getForm().findField('jzmouth').getValue()
     					    },
     					    callback: function(records, operation, success) {
     					        // do something after the load finishes
     					    },
     					    scope: this
     					});
     					//bookStore.load();
     					ssphbwin.close();
     				}
     			}]
         });
        
    	ssphbwin=new Ext.Window({  
            title:'查询',  
            width:300,  
            height:270,  
            closeAction:'hide',  
            plain:true,  
            layout:'fit',
            constrain:true,
            //resizable:true,
            modal:true,
            items:[ssphb]
    	});
    	ssphbwin.show();
       
        Ext.define('Book', {  
            extend: 'Ext.data.Model',  
            fields: [  
				{name: 'BM' , type: 'string'},
				{name: 'KMMC' , type: 'string'},
				{name: 'QCJF' , type: 'string'},
				{name: 'QCDF' , type: 'string'},
                {name: 'BQJF' , type: 'string'},  
                {name: 'BQDF', type: 'string'},  
                {name: 'QMJF', type: 'string'},  
                {name: 'QMDF', type: 'string'}
            ]  
        });  
        // 创建一个Ext.data.Store对象  
        var ssphbStore = Ext.create('Ext.data.Store',   
        {  
            // 指定使用Book Model管理记录  
            model: 'Book',  
            // 使用proxy指定加载远程数据  
            proxy:   
            {  
                type: 'ajax',  
                url: 'SSPHB',// 向该URL发送Ajax请求  
                reader: { // 使用Ext.data.reader.Json读取服务器数据  
                    type: 'json',  
                    root: 'data' // 直接读取服务器响应的data数据  
                },  
            },  
            //autoLoad:true// 自动加载服务器数据  
        });  
        var grids=Ext.create('Ext.grid.Panel', {  
            title: '试算平衡表',  
            //columnWidth:1,
            //columnHeight:.99,   
            renderTo: 'ssphb',  
            // 定义该表格包含的所有数据列  
            columns: [  
                { text: '编码', dataIndex: 'BM' , width: 90 },
                { text: '科目名称', dataIndex: 'KMMC' , flex: 1 },
                { text: '期初借方', dataIndex: 'QCJF' , flex: 1 },
                { text: '期初贷方', dataIndex: 'QCDF' , flex: 1 },
                { text: '本期借方', dataIndex: 'BQJF' , flex: 1 }, // 第1个数据列  
                { text: '本期贷方', dataIndex: 'BQDF', flex: 1 }, // 第3个数据列  
                { text: '期末借方', dataIndex: 'QMJF' , flex: 1 },
                { text: '期末贷方', dataIndex: 'QMDF' , flex: 1 }
            ],  
            store: ssphbStore  ,
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [{
                    text: '查找',
    				icon:'ext/resources/ext-theme-neptune/images/grid/refresh.png',
                    handler: function(){
                    	ssphbwin.show();
                    }
    			}]
    		}]
        });  
    });
    </script>
  </body>
</html>
