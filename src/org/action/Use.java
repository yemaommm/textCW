package org.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class Use extends ActionSupport {

	/**
	 * @return
	 * @throws Exception 
	 */
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("Iuse");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		
		String str=dbc.StableQuery();

		out.print(str);
		out.flush();
		out.close();
		
		return SUCCESS;
	}
}