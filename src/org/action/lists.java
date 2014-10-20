package org.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class lists extends ActionSupport {

	/**
	 * @return
	 * @throws IOException 
	 */
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
//		System.out.println("ok1");ActionContext.getContext()
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");//setContentType("text/html:charset=UTF-8");
//		System.out.println("ok2");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
//		System.out.println("ok3");
		
//		HttpServletResponse response=(HttpServletResponse)ActionContext.getContext().get(
//				org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
//		response.setContentType("text/html:charset=UTF-8");
//		PrintWriter out=response.getWriter();
		try {
			out.print(dbc.cus());
			out.flush();
			out.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "success";
	}
	
}