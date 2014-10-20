package org.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class BIAO extends ActionSupport {

	/**
	 * @return
	 * @throws IOException 
	 */
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("jzye");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		
//		System.out.println(dbc.getSql());
		dbc.setSql(String.format(dbc.getSql(), 
				"XH kmxh,MC",
				"biao",
				""));
		
		String str=null;
		try {
			str = dbc.StableQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
//		String[] stmp=str.split("\\},\\{");
//		List<String> jsonarray=Arrays.asList(stmp);
//		str="";
//		
//		for (String string : jsonarray) {
//			int i=string.indexOf("':'")+3;
//			int j=string.indexOf("','");
//			int x=string.lastIndexOf("':'")+3;
//			
//			String BM=string.substring(i,j);
////			int len=8-BM.length();
////			for(int y=1;y<=len;y++)
////				BM+="&nbsp&nbsp";
//			string=string.substring(0, x)+BM+"��"+string.substring(x);
//			str+=string+"},{";
//		}
//		
//		str=str.substring(0, str.length()-3);
		str=str.replace("'kmxh':", "");
		str=str.replace("'MC':", "");
		str=str.replace('{', '[');
		str=str.replace("}", "]");
		System.out.println(str);
		
		out.print(str);
		out.flush();
		out.close();
		
		return SUCCESS;
	}
}