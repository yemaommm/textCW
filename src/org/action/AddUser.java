package org.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class AddUser extends ActionSupport {

	/**
	 * @return
	 */
	private String use;
	private String password;
	private String ZD;
	private String CX;
	private String ZZ;
	
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		if(ZD==null)
			ZD="0";
		else
			ZD="1";
		
		if(CX==null)
			CX="0";
		else
			CX="1";
		
		if(ZZ==null)
			ZZ="0";
		else
			ZZ="1";
				
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("Iuse");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		String value=String.format(dbc.getAddsql(),"",
				"'"+use+"','"+password+"','"+ZD+"','"+CX+"','"+ZZ+"',getdate()");
				
		//dbc.setAddsql(str);
		System.out.println(value);
		List<String> addvalue=new ArrayList<String>();
		addvalue.add(value);
		
		try {
			int i=dbc.sql_add_del_update(addvalue);
			out.print("{success:true,successMessage:'增加成功,已更新"+String.valueOf(i)+"行'}");
			System.out.println(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.print("{success:false,errorMessage:'增加失败，原因："+e.getMessage().replaceAll("'", "")+"'}");
			System.out.println(e.getMessage().replaceAll("'", ""));
		}
		
		//System.out.println(ZD+CX+ZZ);
		
		return SUCCESS;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getZD() {
		return ZD;
	}

	public void setZD(String zD) {
		ZD = zD;
	}

	public String getCX() {
		return CX;
	}

	public void setCX(String cX) {
		CX = cX;
	}

	public String getZZ() {
		return ZZ;
	}

	public void setZZ(String zZ) {
		ZZ = zZ;
	}
}