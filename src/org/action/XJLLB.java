package org.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class XJLLB extends ActionSupport {

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
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("jzye");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		
		int a,b,c;
		a=Integer.valueOf(ksyear);
		b=Integer.valueOf(ksmouth);
		c=a-1;
		if(b-1<1){
			b=12;
			a--;
		}else{
			b--;
		}
		
		String YEsql="select ISNULL(AYE,0) AYE,ISNULL(BYE,0) BYE " +
				"from (select 1 stmp,sum(YE) AYE " +
				"from km,kmye " +
				"where kmye.BM=km.BM " +
				"and (fistKm=1001 or fistKm=1002) " +
				"and [year]="+String.valueOf(c)+" and [mouth]=12) a " +
				"full join (select 1 stmp,sum(YE) BYE " +
				"from km,kmye " +
				"where kmye.BM=km.BM " +
				"and (fistKm=1001 or fistKm=1002) " +
				"and [year]="+String.valueOf(a)+" and [mouth]="+String.valueOf(b)+") b " +
				"on a.stmp=b.stmp";
		
		dbc.setSql(YEsql);
		String str="";
		try {
			str=dbc.XJYE();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		String sql="select BH,MC,ISNULL(BYE,0) BYE,ISNULL(AYE,0) AYE " +
				"from xjll " +
				"FULL JOIN " +
				"(select ISNULL(a.XJLLH,b.XJLLH) BM,b.YE BYE,a.YE AYE " +
				"from " +
				"(select sum(JE*JD) YE,XJLLH" +
				" from pz,xjll where XJLLH=xjll.BH " +
				"and [year]="+ksyear+" " +
				"and [mouth]="+ksmouth+" " +
				"group by XJLLH) a " +
				"FULL JOIN " +
				"(select sum(JE*JD) YE,XJLLH from pz,xjll " +
				"where XJLLH=xjll.BH " +
				"and [year]="+ksyear+" " +
				"and [mouth]<="+ksmouth+" " +
				"group by XJLLH) b " +
				"on a.XJLLH=b.XJLLH) a " +
				"on a.BM=xjll.BH";
		
		dbc.setSql(sql);
		
		try {
			str=dbc.XJLLB(str);
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