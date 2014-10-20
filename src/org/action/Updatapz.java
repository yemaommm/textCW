package org.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class Updatapz extends ActionSupport {

	/**
	 * @return
	 */
	private String data;
	private String datePZ;
	private String PZH;
	private List<String> sqlArray=new ArrayList<String>();
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("pz");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
				
		System.out.println(data);
		
		String year=datePZ.split("-")[0];
		String mouth=datePZ.split("-")[1];
		String day=datePZ.split("-")[2];
		
		sqlArray.add(String.format(dbc.getDelsql(), 
				"where [year]="+year+" and [mouth]="+mouth+" and [PZH]="+PZH));
		
				
		String add=dbc.getAddsql();
		JSONArray array = JSONArray.fromObject(data); 
		for(int i = 0; i < array.size(); i++){ 
			JSONObject jsonObject = array.getJSONObject(i); 
			
			String JD="1";
			String JE=jsonObject.getString("JF");
			if(JE.equals("0")){
				JE=jsonObject.getString("DF");
				JD="-1";
			}
			String KM=jsonObject.getString("KM").split("：")[0];
			String x="[year],[mouth],[day],[PZH],[XH],[BM],[JD],[JE],[CZRQ],[ZY],[users],[XJLLH]";
			String y=String.format("%s,%s,%s,%s,%s,'%s',%s,%s,getdate(),'%s','%s','%s'",year,mouth,day,
					PZH,i+1,KM,JD,JE,
					jsonObject.getString("ZY"),session.getAttribute("username"),jsonObject.getString("XJLLH"));
			
			String str=String.format(add, x,y);
			
			sqlArray.add(str);
		} 
		
		try {
			int i=dbc.sql_add_del_update(sqlArray);
			out.print("保存成功"+String.valueOf(i)+"行");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.print(e.getMessage());
			e.printStackTrace();
			out.flush();
			out.close();
			return SUCCESS;
		}
		
		
		
		out.flush();
		out.close();
		
		return SUCCESS;
	}
	public String getPZH() {
		return PZH;
	}
	public void setPZH(String pZH) {
		PZH = pZH;
	}
	public String getData() {
		return data;
	}
	public String getDatePZ() {
		return datePZ;
	}
	public void setData(String data) {
		this.data = data;
	}
	public void setDatePZ(String datePZ) {
		this.datePZ = datePZ;
	}
}