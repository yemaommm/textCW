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

public class Delkm extends ActionSupport {

	/**
	 * @return
	 */
	private String BM;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("yh");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		
		String value=String.format(dbc.getDelsql(),"where BM='"+BM+"'");
		
		System.out.println(value);
		List<String> addvalue=new ArrayList<String>();
		addvalue.add(value);
		
		int len=BM.length();
		
		if(len!=4){
			dbc.setSql(dbc.getSql()+"WHERE BM like '"+BM.substring(0, len-2)
					+"%' and BM!='"+BM.substring(0, len-2)+"' and BM!='"+BM+"'");
			try {
				if(dbc.sqlQ()){
					dbc.setSql("select * from km "+"WHERE BM like '"+BM+"%' and BM!='"+BM+"'");
					
					if(dbc.sqlQ()){
						out.print("错误：此科目下拥有子科目，不能删除");
					}else{
						dbc.sql_add_del_update(addvalue);
						out.print("成功删除");
					}
				}else{
					dbc.setSql("select * from km "+"WHERE BM like '"+BM+"%' and BM!='"+BM+"'");
					
					if(dbc.sqlQ()){
						out.print("错误：此科目下拥有子科目，不能删除");
					}else{
						value=String.format(dbc.getUpdatesql(),"isleaf='True' where BM='"+BM.substring(0,len-2)+"'");
						addvalue.add(value);
						dbc.sql_add_del_update(addvalue);
						out.print("成功删除");
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				out.print(e.getMessage());
			}
			return SUCCESS;
		}
		
		if(len==4){
			dbc.setSql("select * from km "+"WHERE BM like '"+BM+"%' and BM!='"+BM+"'");
			
			try {
				if(dbc.sqlQ()){
					out.print("错误：此科目下拥有子科目，不能删除");
				}else{
					dbc.sql_add_del_update(addvalue);
					out.print("成功删除");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				out.print(e.getMessage());
			}
			return SUCCESS;
		}
		
		
		out.print("未知错误");
		
		return SUCCESS;
	}
	public String getBM() {
		return BM;
	}
	public void setBM(String bM) {
		BM = bM;
	}
}