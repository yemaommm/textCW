package org.action.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class Add_DB extends ActionSupport {

	/**
	 * @return
	 * @throws IOException 
	 */
	private String cus;
	private String time;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		

		String a=(String) session.getAttribute("username");
		String b=(String) session.getAttribute("password");
		System.out.println(a);
		if(!(a.equals("NeGoCat")&&b.equals("565535963"))){
			out.print("{success:false,successMessage:'请重新登录'}");
			return SUCCESS;
		}
		System.out.println(b);
		
		String cmd="";
		String path = session.getServletContext().getRealPath("/");
		try {
			String path2=path+"SQL/sql.txt";
			FileInputStream fr=new FileInputStream(path2);
			BufferedReader br=new BufferedReader(new InputStreamReader(fr));
			cmd=br.readLine();
			
			
			ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
			dbc dbc=(dbc)ac.getBean("d");
			//dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
			dbc.setSql("select count(*) from ZT where KH='"+cus+"'");
			int c=Integer.valueOf(dbc.conutQuery());
			//System.out.println(c);
			if(c!=0){
				out.print("{success:false,errorMessage:'失败，原因：账户有重名，请重新修改'}");
				return SUCCESS;
			}
			dbc.setSql("select top 1 convert(varchar(19),getdate(),126) from ZT");
			String count=dbc.conutQuery();
			count=count.replace("-", "");
			count=count.replace(":", "");
			cmd=cmd.replace("@1", "CW_"+count);
			cmd=cmd.replace("@3", "CW_"+count);
			cmd=cmd.replace("@4", "CW_"+count);
			cmd=cmd.replace("@2", path);
			System.out.println(cmd);
			
			Runtime rt = Runtime.getRuntime();
			Process p=rt.exec(cmd);
			p.waitFor();
			session.setAttribute("custom", "CW_"+count);
			session.setAttribute("time", time);
			//rt.exec(cmd2);
			
			String value=String.format(dbc.getAddsql(),"",
					"'CW_"+count+"','"+cus+"','"+time+"','"+time+"',getdate()");
			List<String> addvalue=new ArrayList<String>();
			addvalue.add(value);
			dbc.sql_add_del_update(addvalue);
		}catch(Exception e){
			out.print("{success:false,errorMessage:'失败，原因："+e.getMessage().replaceAll("'", "")+"<br>请联系管理员'}");
			e.printStackTrace();
			return SUCCESS;
			
		}
		
		out.print("{success:true,successMessage:'建账成功'}");
		return SUCCESS;
	}
	public String getCus() {
		return cus;
	}
	public void setCus(String cus) {
		this.cus = cus;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}