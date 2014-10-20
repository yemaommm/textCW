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
     DL('Loginss',errormassage);
     </script>
</head>
<body style="background-image: url('image/BJ.jpg'); background-size:100% 100%;">
<div align="center" id="form"></div>

</body>
</html>
