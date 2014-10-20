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

public class Addkm extends ActionSupport {

	/**
	 * @return
	 * @throws IOException 
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
		
//		System.out.println(biao);
		
		int i=0;
		int len=BM.length();
		String m="";
		if(len==4){
			lerver="1";
			m=BM;
		}else if(len==6){
			lerver="2";
			m=BM.substring(0,4);
		}else if(len==8){
			lerver="3";
			m=BM.substring(0,4);
		}
		
		if(YEFX.equals("借"))
			YEFX="1";
		else
			YEFX="-1";
		
		isleaf="True";
		
		if(len!=4){
			String stmp=BM.substring(0, len-2);
			dbc.setSql(dbc.getSql()+"WHERE BM='"+stmp+"'");
			
			try {
				if(!dbc.sqlQ()){
					out.print("{success:false,errorMessage:'增加失败，原因：此科目编号上级科目未找到'}");
					return SUCCESS;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				out.print("{success:false,errorMessage:'失败，原因："+e.getMessage().replaceAll("'", "")+"'}");
				return SUCCESS;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				out.print("{success:false,errorMessage:'失败，原因："+e.getMessage().replaceAll("'", "")+"'}");
				return SUCCESS;
			}
		}
		
		String value=String.format(dbc.getAddsql(),"",
				"'"+BM+"','"+KMMC+"',"+biao+","+m+",'"+FL+"',"+YEFX+",'"+isleaf+"',"+lerver+"");
		String items="";
		//dbc.setAddsql(str);
		List<String> addvalue=new ArrayList<String>();
		addvalue.add(value);
		
		try {
			
			if(len!=4){
				String stmp=BM.substring(0, len-2);
				value=String.format(dbc.getUpdatesql(),"isleaf='False' WHERE BM='"+stmp+"'");
				addvalue.add(value);
				i=dbc.sql_add_del_update(addvalue);
			}else{
				i=dbc.sql_add_del_update(addvalue);
			}
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