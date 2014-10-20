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

public class SH_PZ extends ActionSupport {

	/**
	 * @return
	 * @throws IOException 
	 */
	private String xz;
	private String date;
	private String PZH;
	private String SH;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("pz");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		
		
		if(PZH==null){
			date=(String) session.getAttribute("time");
			String year=date.substring(0, 4);
			String mouth=date.substring(4);
			
			String updataSql=null;
			
			if(xz.equals("1")){
				updataSql=String.format(dbc.getUpdatesql(), "SH='True' where year="+year
						+" and mouth="+mouth+" and SH!='True' or SH is NULL");
			}else{
				updataSql=String.format(dbc.getUpdatesql(), "SH='False' where year="+year
						+" and mouth="+mouth+" and SH='True'");
			}
			List<String> sqlarray=new ArrayList<String>();
			sqlarray.add(updataSql);
			
			try {
				int i=dbc.sql_add_del_update(sqlarray);
				out.print("{success:true,successMessage:'审核成功,已审核"+String.valueOf(i)+"行'}");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				out.print("{success:false,errorMessage:'审核失败，原因："+e.getMessage().replaceAll("'", "")+"'}");
				e.printStackTrace();
				return SUCCESS;
			} 
			return SUCCESS;
		}
		
		String year=date.split("-")[0];
		String mouth=date.split("-")[1];//System.out.println(123);
		
		String updataSql=String.format(dbc.getUpdatesql(), "SH='"+SH+"' where year="+year
					+" and mouth="+mouth+"and PZH="+PZH);
		
		List<String> sqlarray=new ArrayList<String>();
		sqlarray.add(updataSql);
		
		try {
			int i=dbc.sql_add_del_update(sqlarray);
			out.print("审核成功"+String.valueOf(i)+"行");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.print(e.getMessage());
			e.printStackTrace();
			return ERROR;
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
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
	}
	public String getSH() {
		return SH;
	}
	public void setSH(String sH) {
		SH = sH;
	}
}