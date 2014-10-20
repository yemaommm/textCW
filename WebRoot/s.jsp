<%@page import="org.spring.dbc"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
//response.getWriter().write(dbc.cus());
 %>
单位：<%=session.getAttribute("customname") %>，用户名：<%=session.getAttribute("username") %>，会计期间：<%=session.getAttribute("time") %>