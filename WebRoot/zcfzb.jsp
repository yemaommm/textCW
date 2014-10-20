<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head> </head>
<script language="JavaScript">
function WinOpen(ss) {
   mesg=open("cnrose","Window");
   mesg.document.write(ss);
}
</script>
  <body>
  <div style="width:100%;height:100%;padding:0px;margin:0px;"  align="center" id='zcfzb'></div>
    <script>
    Ext.onReady(function(){  
    	var jm_year_month=document.getElementById('downc').innerHTML.
			split('：')[document.getElementById('downc').innerHTML.split('：').length-1];
    	var mouth=[{"name":"1"},{"name":"2"},{"name":"3"},{"name":"4"},{"name":"5"},{"name":"6"},{"name":"7"},{"name":"8"},{"name":"9"},{"name":"10"},{"name":"11"},{"name":"12"}];
    	
    	var zcfzb=new Ext.form.Panel({
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
                html: '请选择年月<br>'
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
         	}],
         	buttons:[{
     				text:'确定',
     				handler:function(){
     					//alert(mxz.getForm().findField('ksyear').getValue());
     					/*
     					zcfzbStore.load({
     					    params: {
     					    	ksyear: zcfzb.getForm().findField('ksyear').getValue(),
     					    	ksmouth: zcfzb.getForm().findField('ksmouth').getValue()
     					    },
     					    callback: function(records, operation, success) {
     					        // do something after the load finishes
     					    },
     					    scope: this
     					});
     					*/
     					//bookStore.load();
     					Ext.Ajax.request({
     						url:'ZCFZB',
     						params:{
     							ksyear: zcfzb.getForm().findField('ksyear').getValue(),
     					    	ksmouth: zcfzb.getForm().findField('ksmouth').getValue()
     					    	},
     						success:function(response){
     							zcfzbtxt=document.getElementById('zcfzbDIV').innerHTML=response.responseText;
     						}
     					});
     					zcfzbwin.close()
     				}
     			}]
         });
        
    	zcfzbwin=new Ext.Window({  
            title:'资产负债表',  
            width:300,  
            height:270,  
            closeAction:'hide',  
            plain:true,  
            layout:'fit',
            constrain:true,
            //resizable:true,
            modal:true,
            items:[zcfzb]
    	});
    	zcfzbwin.show();
       
    	var zcfzbP=Ext.create('Ext.panel.Panel', {  
            html:'<div id="zcfzbDIV"></div>',
            layout:'fit',
            height:Ext.get('zcfzb').getViewSize().height,
            autoScroll:true,
            renderTo: 'zcfzb',  
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [{
                    text: '查找',
    				icon:'ext/resources/ext-theme-neptune/images/grid/refresh.png',
                    handler: function(){
                    	zcfzbwin.show()
                    }
    			},{
                    text: '打印',
    				//icon:'ext/resources/ext-theme-neptune/images/grid/refresh.png',
                    handler: function(){
                    	WinOpen(zcfzbtxt);
                    }
    			}]
    		}]
        });  
    	
    });
    </script>
  </body>
</html>
