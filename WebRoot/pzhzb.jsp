<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head> </head>
  <body>
  <div id='pzhzb'></div>
    <script>
    Ext.onReady(function(){  
    	var jm_year_month=document.getElementById('downc').innerHTML.
			split('：')[document.getElementById('downc').innerHTML.split('：').length-1];
    	var mouth=[{"name":"1"},{"name":"2"},{"name":"3"},{"name":"4"},{"name":"5"},{"name":"6"},{"name":"7"},{"name":"8"},{"name":"9"},{"name":"10"},{"name":"11"},{"name":"12"}];
    	
    	var pzhz=new Ext.form.Panel({
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
    			xtype:'label',
                html: '<font color="red">以下为可选</font><br>'
    		},{
    			xtype:'combo',
         		fieldLabel:'科目（包含）',
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
         		//forceSelection:true,
         		allowBlank:true,
         		autoSelect:true,
         	    queryMode: 'local',
         	    valueField:'BMb',
         	    displayField: 'KMMCb',
         	    name:'BM'
    		},{
				xtype:'textfield',
				fieldLabel:'摘要（包含）',
				allowBlank:true,
				width:330,
				name:'ZY'
			},{
				xtype:'numberfield',
				hideTrigger:true,
				allowBlank:true,
				fieldLabel:'金额',
				width:330,
				name:'JE'
			}],
         	buttons:[{
     				text:'确定',
     				handler:function(){
     					//alert(mxz.getForm().findField('ksyear').getValue());
     					pzhzStore.load({
     					    params: {
     					    	ksyear: pzhz.getForm().findField('ksyear').getValue(),
     					    	ksmouth: pzhz.getForm().findField('ksmouth').getValue(),
     					    	jzyear: pzhz.getForm().findField('jzyear').getValue(),
     					    	jzmouth: pzhz.getForm().findField('jzmouth').getValue(),
     					    	BM: pzhz.getForm().findField('BM').getValue(),
     					    	ZY: pzhz.getForm().findField('ZY').getValue(),
     					    	JE: pzhz.getForm().findField('JE').getValue(),
     					    },
     					    callback: function(records, operation, success) {
     					        // do something after the load finishes
     					    },
     					    scope: this
     					});
     					//bookStore.load();
     					pzhzwin.close()
     				}
     			}]
         });
        
    	pzhzwin=new Ext.Window({  
            title:'科目汇总表',  
            width:360,  
            height:310,  
            closeAction:'hide',  
            plain:true,  
            layout:'fit',
            constrain:true,
            //resizable:true,
            modal:true,
            items:[pzhz]
    	});
    	pzhzwin.show();
       
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
                {name: 'DF', type: 'string'}
            ]  
        });  
        // 创建一个Ext.data.Store对象  
        var pzhzStore = Ext.create('Ext.data.Store',   
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
        var pzhzgrids=Ext.create('Ext.grid.Panel', {  
            title: '凭证汇总表',  
            //columnWidth:1,
            //columnHeight:.99,   
            renderTo: 'pzhzb',  
            // 定义该表格包含的所有数据列  
            columns: [  
                { text: '年', dataIndex: 'year' , width: 50 },
                { text: '月', dataIndex: 'mouth' , width: 30 },
                { text: '日', dataIndex: 'day' , width: 30 },
                { text: '凭证号', dataIndex: 'PZH' , width: 50 },
                { text: '摘要', dataIndex: 'ZY' , flex: 1 }, // 第1个数据列  
                { text: '编码', dataIndex: 'BM', width: 90 }, // 第3个数据列  
                { text: '科目名称', dataIndex: 'KMMC' , flex: 1 },
                { text: '借', dataIndex: 'JF' , flex: 1 },
                { text: '贷', dataIndex: 'DF' , flex: 1 }
            ],  
            store: pzhzStore  ,
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [{
                    text: '查找',
    				icon:'ext/resources/ext-theme-neptune/images/grid/refresh.png',
                    handler: function(){
                    	pzhzwin.show()
                    }
    			}]
    		}]
        });  
    });
    </script>
  </body>
</html>
