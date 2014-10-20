package org.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class Updatepassword extends ActionSupport {

	/**
	 * @return
	 * @throws IOException 
	 */
	private String use;
	private String password;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("Iuse");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		String value=String.format(dbc.getUpdatesql(),
				"MM='"+password+"' WHERE ZH='"+use+"'");
		
		List<String> addvalue=new ArrayList<String>();
		addvalue.add(value);
		
		try {
			int i=dbc.sql_add_del_update(addvalue);
			out.print("{success:true,successMessage:'更改密码成功'}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.print("{success:false,errorMessage:'增加失败，原因："+e.getMessage().replaceAll("'", "")+"'}");
		}
		
		return SUCCESS;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
}