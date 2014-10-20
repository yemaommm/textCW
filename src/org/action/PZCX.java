package org.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.JSON.SSPHB;
import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class PZCX extends ActionSupport {

	/**
	 * @return
	 */
	private String year;
	private String mouth;
	private String date;
	private String PZH;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		String str=null;
		
		if(year==null){
			List<org.JSON.PZCX> pzcx=new ArrayList<org.JSON.PZCX>();
			for(int i=0;i<=1;i++){
				org.JSON.PZCX pz=new org.JSON.PZCX();
				pz.setZY("");
				pz.setKM("");
				pz.setJF("0");
				pz.setDF("0");
				pzcx.add(pz);
//				System.out.println(222);
			}
			JSONArray jsonObject = JSONArray.fromObject(pzcx);   
			   
			str = jsonObject.toString();
		}else{
			if(year.equals("0")){
				str=(String) session.getAttribute("PZ");
				out.print(str);
				return SUCCESS;
			}
			ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
			dbc dbc=(dbc)ac.getBean("jzye");
			dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
			
			String Query=dbc.getSql();
			
			dbc.setSql(String.format(dbc.getSql(), "ZY,pz.BM,KMMC,JD,JE,SH,ZF,PZH,[year],[mouth],[day],XJLLH,KHBH"
					,"pz,km"
					,"where pz.BM=km.BM and [year]="+year+" and [mouth]="+mouth+" and [PZH]="+PZH+" order by XH"));
			try {
				str=dbc.PZ();
				String stmp=str.split("//<>//")[1];
				session.setAttribute("PZ", stmp);
				str=str.split("//<>//")[0];
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		out.print(str);
		System.out.println(str);
		return SUCCESS;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMouth() {
		return mouth;
	}
	public void setMouth(String mouth) {
		this.mouth = mouth;
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