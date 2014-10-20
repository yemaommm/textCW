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

public class Year extends ActionSupport {

	/**
	 * @return
	 * @throws IOException 
	 */
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		String xzqj=(String) session.getAttribute("time");
		String ksqj=(String) session.getAttribute("KSQJ");
		
		xzqj=xzqj.substring(0,4);
		ksqj=ksqj.substring(0,4);
		
		int time1=Integer.valueOf(xzqj);
		int time2=Integer.valueOf(ksqj);
		
		String str="[";
		
		for(int i=0;time2+i<=time1;i++){
			if(str.charAt(str.length()-1)==']')
				str+=",";
			str+="['";
			str+=String.valueOf(time2+i);
			str+="']";
		}
		
		str+="]";

		out.print(str);
		out.flush();
		out.close();
		
		return SUCCESS;
	}
}