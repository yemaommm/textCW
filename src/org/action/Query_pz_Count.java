package org.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class Query_pz_Count extends ActionSupport {

	/**
	 * @return
	 */
	private String year;
	private String mouth;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("pz");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		
		String Query=dbc.getSql();
		
		dbc.setSql("select count(*) from "+
				"(select PZH from pz where [year]="+year+" and [mouth]="+mouth
				+" group by PZH) as kk");
		
		int PZH=0;
		try {
			PZH=Integer.valueOf(dbc.conutQuery());
			PZH++;
			out.print(PZH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.print(e.getMessage());
		}
		
		out.flush();
		out.close();
		
		return SUCCESS;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getYear() {
		return year;
	}
	public String getMouth() {
		return mouth;
	}
	public void setMouth(String mouth) {
		this.mouth = mouth;
	}
}