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

public class ZF_PZ extends ActionSupport {

	/**
	 * @return
	 */
	private String date;
	private String PZH;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("pz");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		
		String year=date.split("-")[0];
		String mouth=date.split("-")[1];System.out.println(123);
		String zf="True";
		String k=SUCCESS;
		String updataSql=null;
		
		
		updataSql=String.format(dbc.getDelsql(), "where year="+year
					+"and mouth="+mouth+" and PZH="+PZH);
		
		List<String> sqlarray=new ArrayList<String>();
		sqlarray.add(updataSql);
		
		try {
			int i=dbc.sql_add_del_update(sqlarray);
			out.print("ÒÑÉ¾³ý"+String.valueOf(i)+"ÐÐ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.print(e.getMessage());
			e.printStackTrace();
			return SUCCESS;
		} 
		
		try {
			dbc.PZPX(year, mouth);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		return SUCCESS;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPZH() {
		return PZH;
	}
	public void setPZH(String pZH) {
		PZH = pZH;
	}
}