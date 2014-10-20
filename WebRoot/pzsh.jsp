<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <body>
  	<div id='pzsh'></div>
 	 <script>
Ext.onReady(function(){  
	var jm_year_month=document.getElementById('downc').innerHTML.
		split('：')[document.getElementById('downc').innerHTML.split('：').length-1];
	
    var SH=new Ext.form.Panel({
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
            html: '<font color="red">提示：只能审核/反审核当月的凭证</font><br>'
		},{
			xtype:'label',
            text: jm_year_month
		},{
     		xtype:'radio',
     		inputValue: '1',
			name:'xz',
			boxLabel:'审核未审核的凭证'
     	},{
     		xtype:'radio',
     		inputValue: '2',
			name:'xz',
			boxLabel:'取消已审核的凭证'
     	}],
     	buttons:[{
 				text:'保存',
 				handler:function(){
 					SH.getForm().submit({
						clientValidation:true,
						url:'SH_PZ',
						method:'post',
						success:function(form,action){
							Ext.Msg.alert('提示',action.result.successMessage);	
							SH.getForm( ).reset( );
							Ext.getCmp('凭证审核').close()
							win.close();
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
 					Ext.getCmp('凭证审核').close()
 					win.close()
 				}
 			}]
     });
    
    win=new Ext.Window({  
        title:'审核凭证',  
        width:300,  
        height:160,  
        closeAction:'hide',  
        plain:true,  
        layout:'fit',
        constrain:true,
        //resizable:true,
        modal:true,
        items:[SH]
	});
   win.show();

	
});
 	</script>
  </body>
</html>
