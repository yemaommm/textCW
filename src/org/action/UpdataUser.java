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

public class UpdataUser extends ActionSupport {

	/**
	 * @return
	 */
	private String use;
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
		String value=String.format(dbc.getUpdatesql(),
				"ZD='"+ZD+"',CX='"+CX+"',ZZ='"+ZZ+"' WHERE ZH='"+use+"'");
		
		List<String> addvalue=new ArrayList<String>();
		addvalue.add(value);
		
		try {
			int i=dbc.sql_add_del_update(addvalue);
			out.print("{success:true,successMessage:'增加成功,已更新"+String.valueOf(i)+"行'}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.print("{success:false,errorMessage:'增加失败，原因："+e.getMessage().replaceAll("'", "")+"'}");
		}
		
		
		
		return SUCCESS;
	}
	public void setUse(String use) {
		this.use = use;
	}public void setZD(String zD) {
		ZD = zD;
	}public void setZZ(String zZ) {
		ZZ = zZ;
	}public void setCX(String cX) {
		CX = cX;
	}public String getUse() {
		return use;
	}public String getZD() {
		return ZD;
	}public String getZZ() {
		return ZZ;
	}public String getCX() {
		return CX;
	}
}