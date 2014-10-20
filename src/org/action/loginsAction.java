package org.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class loginsAction extends ActionSupport {

	/**
	 * @return
	 */
	private String username;
	private String password;
	private String custom;
	private String errormassage;
	public String execute() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		session.setAttribute("custom", custom);
		
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("Iuse");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		Class.forName(dbc.driver);
		Connection conn=DriverManager.getConnection(dbc.getDrivermanager(),dbc.getUse(),dbc.getPassword());
		
		PreparedStatement pstat=conn.prepareStatement(dbc.getSql()+"where ZH='"+username+"' and MM='"
				+password+"'");
		System.out.println(dbc.getSql()+"where ZH='"+username+"' and MM='"+password+"'");
		ResultSet rs=pstat.executeQuery();
		
		int i=0;
		while(rs.next()){
			session.setAttribute("ZD", rs.getString("ZD"));
			session.setAttribute("CX", rs.getString("CX"));
			session.setAttribute("ZZ", rs.getString("ZZ"));
			i++;
		}
		
		if(i<=0){
			errormassage="用户名或密码错误";
			return "error";
		}
			
		rs.close();
		pstat.close();
		conn.close();
		
		
		ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc=(dbc)ac.getBean("d");
		conn=DriverManager.getConnection(dbc.getDrivermanager(),dbc.getUse(),dbc.getPassword());
		pstat=conn.prepareStatement("select KH,XZQJ,KSQJ from ZT where BH='"+custom+"'");
		
		rs=pstat.executeQuery();
		
		while(rs.next()){
			session.setAttribute("customname", rs.getString(1).trim());
			session.setAttribute("time", rs.getString(2).trim());
			session.setAttribute("KSQJ", rs.getString(3).trim());
		}
		
		rs.close();
		pstat.close();
		conn.close();
		

		if(i>0){
			return "ok";
		}
		return ERROR;

	}
	public String getCustom() {
		return custom;
	}
	public void setCustom(String custom) {
		this.custom = custom;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getErrormassage() {
		return errormassage;
	}
	public void setErrormassage(String errormassage) {
		this.errormassage = errormassage;
	}
}