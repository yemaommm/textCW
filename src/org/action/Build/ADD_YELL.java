package org.action.Build;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
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

public class ADD_YELL extends ActionSupport {

	/**
	 * @return
	 */
	private String data;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		

		String a=(String) session.getAttribute("username");
		String b=(String) session.getAttribute("password");
		if(!(a.equals("NeGoCat")&&b.equals("565535963"))){
			out.print("请重新登录");
			return ERROR;
		}
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("jzye");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		
		System.out.println(data);
		String add=dbc.getAddsql();
		String time=(String) session.getAttribute("time");
		String year=time.substring(0, 4);
		String mouth=time.substring(4);
		
		int Cmouth=Integer.valueOf(mouth)-1;
		mouth=String.valueOf(Cmouth);
		if(Cmouth<=0){
			mouth="12";
			year=String.valueOf(Integer.valueOf(year)-1);
		}
		
		JSONArray array = JSONArray.fromObject(data); 
		List<String> sqlArray=new ArrayList<String>();
		double sum=0;
		
		for(int i = 0; i < array.size(); i++){ 
			JSONObject jsonObject = array.getJSONObject(i); 
			
			sum+=Double.valueOf(jsonObject.getString("YEFX"))*Double.valueOf(jsonObject.getString("YE"));
			sum=Double.valueOf(new DecimalFormat("#.##").format(sum));
			System.out.println(sum);
			String BM=jsonObject.getString("BM");
			String KMMC=jsonObject.getString("KMMC");
			String YE=jsonObject.getString("YE");
			String y=String.format("'%s','%s','%s','%s'",year,mouth,YE,BM);
			
			String str=String.format(add, "kmye","values ("+y+")");
			
			sqlArray.add(str);
		} 
		if(sum!=0){
			out.print("借贷方不平衡");
			return SUCCESS;
		}
		
		try {
			int i=dbc.sql_add_del_update(sqlArray);
			out.print("保存成功  "+String.valueOf(i)+"行");
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}