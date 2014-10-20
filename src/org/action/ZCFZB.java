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

public class ZCFZB extends ActionSupport {

	/**
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private String ksyear;
	private String ksmouth;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("jzye");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		
		int a,b,c;
		a=Integer.valueOf(ksyear);
		b=Integer.valueOf(ksmouth);
		c=a-1;
		
		String sql="select XH,MC,ISNULL(AYE,0) AYE,ISNULL(BYE,0) BYE,ISNULL(hj,0) hj " +
				"from biao " +
				"FULL JOIN " +
				"(select XH XXH,sum(AYE) AYE,sum(BYE) BYE,sum(hj) hj " +
				"from km,biao," +
				"(select ISNULL(a.YE,0) AYE,ISNULL(a.BM,b.BM) BM,ISNULL(b.YE,0) BYE,ISNULL(a.YE,0)+ISNULL(b.YE,0) hj " +
				"from " +
				"(select YE,BM " +
				"from kmye " +
				"where [year]="+String.valueOf(c)+" and [mouth]=12) a " +
				"FULL JOIN " +
				"(select sum(JE*YEFX*JD) YE,km.BM " +
				"from pz,km " +
				"where pz.BM=km.BM " +
				"and [year]="+String.valueOf(a)+" " +
				"and [mouth]<="+String.valueOf(b)+" " +
				"group by km.BM) b " +
				"on a.BM=b.BM) a where XH=biao " +
				"and a.BM=km.BM " +
				"and FL!='ËðÒæÀà' " +
				"and BLX=0 group by XH) a " +
				"on a.XXH=biao.XH " +
				"where BLX=0";
		
		dbc.setSql(sql);
		
		String str="";
		try {
			str=dbc.ZCFZB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println(str);
//		String str=dbc.StableQuery();

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