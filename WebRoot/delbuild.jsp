<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<link rel="stylesheet" type="text/css" href="ext/resources/css/ext-all.css" >
     <script type="text/javascript" src="ext/bootstrap.js"></script>
     <script type="text/javascript" src="ext/locale/ext-lang-zh_CN.js"></script>
     
     <script type="text/javascript" src="CW.js"></script>
     
     <script>
     var errormassage='<%=request.getAttribute("errormassage") %>';
     if(errormassage=='null')
    	 errormassage='';
     
     </script>
</head>
<body style="background-image: url('image/BJ.jpg'); background-size:100% 100%;">
<div align="center" id="form"></div>
<div align="center" id="form2"></div>
<script type="text/javascript">
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
			//standardSubmit:true,
			//renderTo:'form',//form
			defaults:{
				labelSeparator:':',
				//labelWidth:50,
				width:330,
				allowBlank:false,
				blankText:'不能为空',
				labelAlign:'center',
				msgTarget:'side'
			},
			items:[{
				xtype:'label',
				html:'<b><font color="red">'+errormassage+'</font></b>'
			},{
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
			}],
			buttons:[{
				text:'删除',
				handler:function(){
					Ext.MessageBox.confirm('提示','请确认是否删除',function(id){
                		if(id=='yes'){
                			Ext.MessageBox.confirm('提示','再次确认是否删除',function(id){
                        		if(id=='yes'){
                        			form.getForm().submit({
                						//clientValidation:true,
                						waitMsg:'正在删除帐套，请稍候。', 
                						waitTitle:'提示',
                						url:url,
                						method:'POST',
                						success:function(form,action){ 
                							Ext.Msg.alert('提示',action.result.successMessage,function(){  
                								window.location.href = "index.jsp";
                					        });  
                						},
                						failure:function(form,action){
                							Ext.Msg.alert('提示',action.result.errorMessage);
                						}
                					});
                        		}
        					});
                		}
					});
					
				}
			},{
				text:'返回',
				handler:function(){
					window.location.href = "index.jsp";
				}
			},{
				text:'新建帐套',
				handler:function(){
					window.location.href = "building.jsp";
				}
			}]
		});
		
		var mypanel=new Ext.Panel({
			title:'删除帐套',
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
DL('DEL_DB',errormassage);
</script>
</body>
</html>
