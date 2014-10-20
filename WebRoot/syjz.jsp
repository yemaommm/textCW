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
  	<div id='syjz'></div>
 	 <script>
 	function stoday()
    { 
        var now = new Date();

        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
        
        var jm_year_month=document.getElementById('downc').innerHTML.
        	split('：')[document.getElementById('downc').innerHTML.split('：').length-1];

        if(month < 10)
            month = "0"+month;
		if(year+month>jm_year_month){
			year=jm_year_month.substr(0,4);
			month=jm_year_month.substr(4,2);
			day=new Date(year, month,0).getDate();
		}
        var clock = year + "-";
        clock += month + "-";
        if(day < 10)
            clock += "0";
        clock += day ;
        
		
        return(clock); 
    } 
Ext.onReady(function(){  
	var jm_year_month=document.getElementById('downc').innerHTML.
		split('：')[document.getElementById('downc').innerHTML.split('：').length-1];
	
    var syjz=new Ext.form.Panel({
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
            html: '<font color="red"></font><br>'
		},{
			name:'datePZ',
			xtype:'datefield',
			format:'Y-m-d',
			value:stoday(),
			readOnly : true,
		},{
			xtype:'label',
            text: '是否结转当月损益'
     	}],
     	buttons:[{
 				text:'确定',
 				handler:function(){
 					syjz.getForm().submit({
						clientValidation:true,
						url:'SYJZ',
						method:'post',
						success:function(form,action){
							Ext.Msg.alert('提示',action.result.successMessage);	
							syjz.getForm( ).reset( );
							Ext.getCmp('损益结转').close()
							syjzwin.close();
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
 					Ext.getCmp('损益结转').close()
 					syjzwin.close()
 				}
 			}]
     });
    
    syjzwin=new Ext.Window({  
        title:'损益结转',  
        width:300,  
        height:160,  
        closeAction:'hide',  
        plain:true,  
        layout:'fit',
        constrain:true,
        //resizable:true,
        modal:true,
        items:[syjz]
	});
    syjzwin.show();
});
 	</script>
  </body>
</html>
