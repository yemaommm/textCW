package org.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class SYB extends ActionSupport {

	/**
	 * @return
	 */
	private String ksyear;
	private String ksmouth;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		System.out.println(111);
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("jzye");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		
		int a,b,c;
		a=Integer.valueOf(ksyear);
		b=Integer.valueOf(ksmouth);
		c=a-1;
		
		String sql="select XH,MC,ISNULL(BYE,0) LJ,ISNULL(AYE,0) BQ " +
				"from biao " +
				"FULL JOIN " +
				"(select ISNULL(a.biao,b.biao) BM,b.YE BYE,a.YE AYE " +
				"from " +
				"(select sum(JE*YEFX*JD) YE,biao " +
				"from pz,km " +
				"where pz.BM=km.BM " +
				"and [year]="+ksyear+" " +
				"and [mouth]="+ksmouth+" " +
				"and FL='损益类' " +
				"and JD=-1 " +
				"group by biao) a " +
				"FULL JOIN " +
				"(select sum(JE*YEFX*JD) YE,biao " +
				"from pz,km " +
				"where pz.BM=km.BM " +
				"and [year]="+ksyear+" " +
				"and [mouth]<="+ksmouth+" " +
				"and FL='损益类' " +
				"and JD=-1 " +
				"group by biao) b " +
				"on a.biao=b.biao) a " +
				"on a.BM=XH " +
				"where BLX=1";
		
		System.out.println(sql);
		
		dbc.setSql(sql);
		
//		String str=dbc.StableQuery();
		
		String str = null;
		try {
			str = dbc.SYB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		System.out.println(str);

		out.print(str);
		out.flush();
		out.close();
		
		return SUCCESS;
	}
	public String getKsyear() {
		return ksyear;
	}
	public void setKsyear(String ksyear) {
		this.ksyear = ksyear;
	}
	public String getKsmouth() {
		return ksmouth;
	}
	public void setKsmouth(String ksmouth) {
		this.ksmouth = ksmouth;
	}
}