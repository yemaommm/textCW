package org.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class DelUser extends ActionSupport {

	/**
	 * @return
	 * @throws IOException 
	 */
	String username;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("Iuse");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));

		String value=String.format(dbc.getDelsql(),"where ZH='"+username+"'");
		
		System.out.println(value);
		List<String> addvalue=new ArrayList<String>();
		addvalue.add(value);
		
		try {
			dbc.sql_add_del_update(addvalue);
			out.print("³É¹¦É¾³ý");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.print(e.getMessage());
		} 
		
		return SUCCESS;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}