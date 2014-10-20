package org.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.spring.dbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;

public class SSPHB extends ActionSupport {

	/**
	 * @return
	 */
	private String ksyear;
	private String ksmouth;
	private String jzyear;
	private String jzmouth;
	private String BM;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc ye=(dbc)ac.getBean("jzye");
		ye.setDrivermanager(ye.getDrivermanager()+session.getAttribute("custom"));

		String where="";
		int k=Integer.valueOf(ksyear);
		int z=Integer.valueOf(jzyear);
		
		int Cmouth=Integer.valueOf(ksmouth)-1;
		int Cyear=Integer.valueOf(ksyear);
		if(Cmouth<=0){
			Cmouth=12;
			Cyear=Integer.valueOf(ksyear)-1;
		}
				
		if(k==z){
			where+="(([year]="+ksyear+" and [mouth]>="+ksmouth+")" +
					" and ([year]="+jzyear+" and [mouth]<="+jzmouth+"))";
		}else if(z-k>1){
			where+="(";
			where+="(([year]="+ksyear+" and [mouth]>="+ksmouth+") " +
					"or ([year]="+jzyear+" and [mouth]<="+jzmouth+"))";
			where+=" or ([year]>="+String.valueOf(k+1)+" and [year]<="+String.valueOf(z-1)+"))";
			where+=")";
		}else{
			where+="(([year]="+ksyear+" and [mouth]>="+ksmouth+")" +
					" or ([year]="+jzyear+" and [mouth]<="+jzmouth+"))";
		}
		
		String tab="";
		
		tab="(select km.BM,km.KMMC,YE as a,ba,bb,YEFX " +
				"from km " +
				"left join " +
				"(select pz.BM,sum(JE) ba " +
				"from pz,km " +
				"where km.BM=pz.BM " +
				"and "+where+" " +
				"and JD=1 " +
				"group by pz.BM) pza " +
				"on pza.BM=km.BM " +
				"left join " +
				"(select pz.BM,sum(JE) bb " +
				"from pz,km " +
				"where km.BM=pz.BM " +
				"and "+where+" " +
				"and JD=-1 " +
				"group by pz.BM) pzb " +
				"on pzb.BM=km.BM " +
				"left join " +
				"(select * from kmye " +
				"where [year]="+String.valueOf(Cyear)+" and [mouth]="+String.valueOf(Cmouth)+") kmye " +
				"on kmye.BM=km.BM) mxz";
		
		ye.setSql(String.format(ye.getSql()
				, "*",tab
				,"where a IS NOT NULL or ba IS NOT NULL or bb IS NOT NULL"));
		
		String str=null;
		try {
			str=ye.SSPHB();
			
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
	public String getJzyear() {
		return jzyear;
	}
	public void setJzyear(String jzyear) {
		this.jzyear = jzyear;
	}
	public String getJzmouth() {
		return jzmouth;
	}
	public void setJzmouth(String jzmouth) {
		this.jzmouth = jzmouth;
	}
	public String getBM() {
		return BM;
	}
	public void setBM(String bM) {
		BM = bM;
	}
}