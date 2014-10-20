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

public class Detail_Account extends ActionSupport {

	/**
	 * @return
	 * @throws IOException 
	 */
	private String ksyear;
	private String ksmouth;
	private String jzyear;
	private String jzmouth;
	private String BM;
	private String JE;
	private String ZY;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("mxz");
		dbc.setDrivermanager(dbc.getDrivermanager()+session.getAttribute("custom"));
		dbc ye=(dbc)ac.getBean("jzye");
		ye.setDrivermanager(ye.getDrivermanager()+session.getAttribute("custom"));   System.out.println(1);

		String where="";
		int k=Integer.valueOf(ksyear);
		int z=Integer.valueOf(jzyear);
		
		int Cmouth=Integer.valueOf(ksmouth)-1;
		int Cyear=Integer.valueOf(ksyear);
		if(Cmouth<=0){
			Cmouth=12;
			Cyear=Integer.valueOf(ksyear)-1;
		}
		System.out.println(2);
		ye.setSql(String.format(ye.getSql()
				, "[year],[mouth],km.BM,YE as HJ,'ÆÚ³õ½ð¶î' as ZY,km.KMMC","kmye,km"
				,"where kmye.BM=km.BM and km.BM like '"+BM
				+"%' and [year]="+String.valueOf(Cyear)+" and [mouth]="+String.valueOf(Cmouth)+""));
		
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
		if(ZY!=null){
			if(!ZY.equals(""))
				ZY="and pz.ZY like '%"+ZY+"%' ";
		}else{
			ZY="";
		}
		if(JE!=null){
			if(!JE.equals(""))
				JE="and pz.JE="+JE+" ";
		}else{
			JE="";
		}
		
		dbc.setSql(String.format(dbc.getSql()
				, "[YEFX],[year],[mouth],[day],[PZH],[ZY],pz.BM,[KMMC],[JD],[JE]"
				,"and pz.BM like '"+BM+"%' "+ZY+JE+"and "+where));
		System.out.println(3);
		String str=null;
		String st=null;
		try {
			st = ye.Query_Account(0);
			System.out.println(4);
			String num=st.split("'HJ':'")[1].split("'")[0];
			System.out.println(5);
			try {
				str = dbc.Query_Account(Double.valueOf(num));
				System.out.println(6);
			} catch (Exception e1) {
				// TODO: handle exception
				str = dbc.Query_Account(0);
				System.out.println(7);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!BM.equals("")){
			st=st.replace("]", "");
			str=st+","+str.substring(1);
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
	public String getJE() {
		return JE;
	}
	public void setJE(String jE) {
		JE = jE;
	}
	public String getZY() {
		return ZY;
	}
	public void setZY(String zY) {
		ZY = zY;
	}
}