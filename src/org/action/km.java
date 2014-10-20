package org.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class km extends ActionSupport {

	/**
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("yh");
		System.out.println(dbc.getDrivermanager()+session.getAttribute("custom"));
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		
		dbc.setSql("select * from km " +
				"left join " +
				"(select XH,MC AS AMC from biao where BLX=0) a " +
				"on a.XH=km.biao " +
				"left join " +
				"(select XH,MC AS BMC from biao where BLX=1) b " +
				"on b.XH=km.biao " +
				"order by BM");
		System.out.println(dbc.getSql());
		
		String str=dbc.kmTreeQuery();

		out.print(str);
		out.flush();
		out.close();
		
		return SUCCESS;
	}
}