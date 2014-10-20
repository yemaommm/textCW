package org.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class SYJZ extends ActionSupport {

	/**
	 * @return
	 * @throws IOException 
	 */
	private String datePZ;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("pz");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		
		String date=(String)session.getAttribute("time");
		String year=date.substring(0, 4);
		String mouth=date.substring(4);
		System.out.print(datePZ);
		String day=datePZ.split("-")[2];
		
		dbc.setSql("select km.BM,km.KMMC,JE " +
				"from km,(" +
				"select km.BM,sum(JE*JD) JE " +
				"from pz,km " +
				"where pz.BM=km.BM " +
				"and km.FL='损益类' " +
				"and [year]="+year+" " +
				"and [mouth]="+mouth+" " +
				"group by km.BM" +
				") a where a.BM=km.BM");
		
		
		try {
			int i=dbc.SYJZ((String)session.getAttribute("username"),year,mouth,day);
			if(i!=0)
				out.print("{success:true,successMessage:'成功,已生成凭证，凭证号"+String.valueOf(i)+"'}");
			else
				out.print("{success:true,successMessage:'失败,无损益结转'}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.print("{success:false,errorMessage:'失败，原因："+e.getMessage().replaceAll("'", "")+"'}");
			e.printStackTrace();
			return ERROR;
		}
		
		
		return SUCCESS;
	}
	public String getDatePZ() {
		return datePZ;
	}
	public void setDatePZ(String datePZ) {
		this.datePZ = datePZ;
	}
}