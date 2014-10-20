package org.action.Build;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class Building extends ActionSupport {

	/**
	 * @return
	 */
	private String username;
	private String password;
	private String errormassage;
	public String execute() {
		// TODO Auto-generated method stub
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		boolean b=(username.equals("NeGoCat")&&password.equals("565535963"));
		
		
		if(!b){
			errormassage="用户名或密码错误";
			return "error";
		}
		session.setAttribute("username", username);
		session.setAttribute("password", password);
			
		return "ok";
	}
	public String getErrormassage() {
		return errormassage;
	}
	public void setErrormassage(String errormassage) {
		this.errormassage = errormassage;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}