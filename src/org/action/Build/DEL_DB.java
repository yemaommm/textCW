package org.action.Build;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class DEL_DB extends ActionSupport {

	/**
	 * @return
	 */
	private String custom;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		String a=(String) session.getAttribute("username");
		String b=(String) session.getAttribute("password");
		if(!(a.equals("NeGoCat")&&b.equals("565535963"))){
			out.print("{success:false,successMessage:'请重新登录'}");
			return SUCCESS;
		}
		
		String cmd="osql -U\"sa\" -P\"\" -Q\"drop database @1\"";
		//String path = session.getServletContext().getRealPath("/");
		try {
//			String path2=path+"SQL/sql.txt";
//			FileInputStream fr=new FileInputStream(path2);
//			BufferedReader br=new BufferedReader(new InputStreamReader(fr));
//			cmd=br.readLine();
//			
//			
			ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
			dbc dbc=(dbc)ac.getBean("d");
			//dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
			cmd=cmd.replace("@1", custom);
			System.out.println(cmd);
			
			Runtime rt = Runtime.getRuntime();
			Process p=rt.exec(cmd);
			p.waitFor();
			//rt.exec(cmd2);
			
			String value=String.format(dbc.getDelsql(),"where BH='"+custom+"'");
			List<String> addvalue=new ArrayList<String>();
			addvalue.add(value);
			dbc.sql_add_del_update(addvalue);
		}catch(Exception e){
			out.print("{success:false,errorMessage:'失败，原因："+e.getMessage().replaceAll("'", "")+"<br>请联系管理员'}");
			e.printStackTrace();
			return SUCCESS;
			
		}
		
		out.print("{success:true,successMessage:'删除成功'}");
		return SUCCESS;
	}
	public String getCustom() {
		return custom;
	}
	public void setCustom(String custom) {
		this.custom = custom;
	}
}