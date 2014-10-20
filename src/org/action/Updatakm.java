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

public class Updatakm extends ActionSupport {

	/**
	 * @return
	 */
	private String BM;
	private String KMMC;
	private String FL;
	private String YEFX;
	private String isleaf;
	private String lerver;
	private String biao;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("yh");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		
		if(YEFX.equals("借"))
			YEFX="1";
		else
			YEFX="-1";
		
		int i=0;
		List<String> addvalue=new ArrayList<String>();
		String value="";
		value=String.format(dbc.getUpdatesql(),
				"KMMC='"+KMMC+"',biao="+biao+",FL='"+FL+"',YEFX="+YEFX+" WHERE BM='"+BM+"'");
		addvalue.add(value);
		
		try {
				i=dbc.sql_add_del_update(addvalue);
			out.print("{success:true,successMessage:'增加成功,已更新"+String.valueOf(i)+"行'}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.print("{success:false,errorMessage:'增加失败，原因："+e.getMessage().replaceAll("'", "")+"'}");
		} 
		
		
		return SUCCESS;
	}
	public void setFL(String fL) {
		FL = fL;
	}
	public String getFL() {
		return FL;
	}
	public void setYEFX(String yEFX) {
		YEFX = yEFX;
	}
	public String getYEFX() {
		return YEFX;
	}
	public String getBM() {
		return BM;
	}
	public void setBM(String bM) {
		BM = bM;
	}
	public String getKMMC() {
		return KMMC;
	}
	public void setKMMC(String kMMC) {
		KMMC = kMMC;
	}
	public String getBiao() {
		return biao;
	}
	public void setBiao(String biao) {
		this.biao = biao;
	}
}