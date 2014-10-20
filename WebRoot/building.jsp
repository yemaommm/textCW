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
				//width:330,
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
				fieldLabel:'请输入单位名称',
				//width:330,
				minLength:4,
				maxLength:25,
				name:'cus'
			},{
				xtype:'numberfield',
				fieldLabel:'请输入会计初始期间，（例：201301）',
				//width:330,
				minLength:6,
				maxLength:6,
				hideTrigger:true,
				allowDecimals: false,
				allowNegative: false,
				//inputType:'password',
				name:'time'
			}],
			buttons:[{
				text:'确定',
				handler:function(){
					var time=form.getForm().findField('time').getValue();
					time=time.toString().substring(4);
					var x=parseInt(time);
					//alert(x);
					if(x>12||x<1){
						Ext.Msg.alert('提示','月份不对，请重新输入');
						return;
					}
					var p=Ext.MessageBox.wait({title:'请稍后',msg:'服务器处理中……',closable:false});
					form.getForm().submit({
						//clientValidation:true,
						waitMsg:'正在建立帐套，请稍候。', 
						waitTitle:'提示',
						url:url,
						method:'POST',
						success:function(form,action){ 
							Ext.Msg.alert('提示',action.result.successMessage,function(){  
								window.location.href = "build_kmgl.jsp";
					        });  
						},
						failure:function(form,action){
							Ext.Msg.alert('提示',action.result.errorMessage);
						}
					});
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
			},{
				text:'删除帐套',
				handler:function(){
					window.location.href = "delbuild.jsp";
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
DL('Add_DB',errormassage);
</script>
</body>
</html>
