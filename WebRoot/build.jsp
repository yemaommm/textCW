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
<script type="text/javascript">
function DL(url,errormassage){
	Ext.onReady(function(){
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
				//allowBlank:true,
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
				text:'返回',
				handler:function(){
					window.location.href = "index.jsp";
				}
			}]
		});
		
		var mypanel=new Ext.Panel({
			title:'建账',
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
DL('Building',errormassage);
</script>
</body>
</html>
