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

public class JZ_PZ extends ActionSupport {

	/**
	 * @return
	 */
	private String xz;
	private String date;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("d");
		dbc Sdbc=(dbc)ac.getBean("pz");
		Sdbc.setDrivermanager(Sdbc.getDrivermanager()+session.getAttribute("custom"));
		
		date=(String) session.getAttribute("time");
		String year=date.substring(0, 4);
		String mouth=date.substring(4);
		
		Sdbc.setSql("select count(PZH) " +
				"from pz " +
				"where [year]="+year+" " +
				"and [mouth]="+mouth+" " +
				"and ISNULL(SH,'false')!='True'");
		
		int x=0;
		try {
			x=Integer.valueOf(Sdbc.conutQuery());
			if(x!=0){
				out.print("{success:false,errorMessage:'失败，有未审核凭证'}");
				return SUCCESS;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		dbc.setSql(dbc.getSql()+" where BH='"+session.getAttribute("custom")+"' and XZQJ='"+date+"'");
		
		try {
			if(!dbc.sqlQ()){
				out.print("{success:false,errorMessage:'失败，请重新登录结转'}");
				return SUCCESS;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.print("{success:false,errorMessage:'失败，原因："+e.getMessage().replaceAll("'", "")+"'}");
			e.printStackTrace();
			return SUCCESS;
		}
		
		String updataSql=null;
		
		if(xz.equals("1")){
			int Cmouth=Integer.valueOf(mouth)+1;
			if(Cmouth>=13){
				Cmouth=1;
				year=String.valueOf(Integer.valueOf(year)+1);
			}
			if(Cmouth<10){
				mouth="0"+String.valueOf(Cmouth);
			}else{
				mouth=String.valueOf(Cmouth);
			}
		}else{
			int Cmouth=Integer.valueOf(mouth)-1;
			if(Cmouth<=0){
				Cmouth=12;
				year=String.valueOf(Integer.valueOf(year)-1);
			}
			if(Cmouth<10){
				mouth="0"+String.valueOf(Cmouth);
			}else{
				mouth=String.valueOf(Cmouth);
			}
		}
		
		String KSQJ=(String) session.getAttribute("KSQJ");
		int ksqj=Integer.valueOf(KSQJ);
		int xzqj=Integer.valueOf(year+mouth);
		if(xzqj<ksqj){
			out.print("{success:false,errorMessage:'失败，帐套初始期间为："+KSQJ+"，不能小于帐套初始期间'}");
			return SUCCESS;
		}
		
		updataSql=String.format(dbc.getUpdatesql(), "XZQJ='"+year+mouth
				+"' where BH='"+session.getAttribute("custom")+"'");
		
		List<String> sqlarray=new ArrayList<String>();
		sqlarray.add(updataSql);
		
		try {
			jzye(session);
			dbc.sql_add_del_update(sqlarray);
			out.print("{success:true,successMessage:'成功,已结转-"+year+mouth+"'}");
			session.setAttribute("time", year+mouth);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.print("{success:false,errorMessage:'失败，原因："+e.getMessage().replaceAll("'", "")+"'}");
			e.printStackTrace();
			return SUCCESS;
		} 
		return SUCCESS;
			
	}
	
	private void jzye(HttpSession session) throws ClassNotFoundException, SQLException {
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc jzye=(dbc)ac.getBean("jzye");
		jzye.setDrivermanager(jzye.getDrivermanager()+session.getAttribute("custom"));

		String year=date.substring(0, 4);
		String mouth=date.substring(4);
		String y=date.substring(0, 4);
		String m=date.substring(4);
		
		int Cmouth=Integer.valueOf(mouth)-1;
		if(Cmouth<=0){
			Cmouth=12;
			y=String.valueOf(Integer.valueOf(year)-1);
		}
		m=String.valueOf(Cmouth);
		
		String updataSql=null;
		
		if(xz.equals("1")){
			String Qsql="select "+year+","+mouth+",ISNULL(YE,0)+ISNULL(S,0),ISNULL(a.BM,b.BM) " +
					"from " +
					"(select sum(JE*YEFX*JD) S,pz.BM " +
					"from pz,km " +
					"where pz.BM=km.BM " +
					"and [year]='"+year+"' " +
					"and [mouth]='"+mouth+"' " +
					"group by pz.BM) a " +
					"FULL JOIN " +
					"(select * from kmye " +
					"where [year]='"+y+"' " +
					"and [mouth]='"+m+"') b " +
					"on a.BM=b.BM";
			updataSql=String.format(jzye.getAddsql(), "kmye", Qsql);
		}else{
			Cmouth=Integer.valueOf(mouth)-1;
			if(Cmouth<=0){
				Cmouth=12;
				year=String.valueOf(Integer.valueOf(year)-1);
			}
			updataSql=String.format(jzye.getDelsql(),"kmye"
					,"where [year]='"+year+"' and [mouth]='"+String.valueOf(Cmouth)+"'");
		}
			
		System.out.println(updataSql);
		
		List<String> sqlarray=new ArrayList<String>();
		sqlarray.add(updataSql);
		
		jzye.sql_add_del_update(sqlarray);
	}
	
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}