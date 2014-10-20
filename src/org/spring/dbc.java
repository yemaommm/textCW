package org.spring;

import org.JSON.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.action.UpdataUser;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class dbc {
	private String drivermanager;
	private String use;
	private String password;
	private String sql;
	private String updatesql;
	private String delsql;
	private String addsql;
	public String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	
	
	public String XJYE() throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		System.out.println(sql);
		PreparedStatement pstat=conn.prepareStatement(sql);
		
		ResultSet rs=pstat.executeQuery();
		rs.next();
		
		String str=rs.getString("AYE")+";"+rs.getString("BYE");
		
		return str;
	}
	
	public String XJLLB(String money) throws SQLException, ClassNotFoundException{
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		System.out.println(sql);
		PreparedStatement pstat=conn.prepareStatement(sql);
		
		ResultSet rs=pstat.executeQuery();
		
		String data=null;
		Double a=0.0;
		Double b=0.0;
		Double hj=0.0;
		Double bq=0.0;
		String str="";
		List<XJLL> acc=new ArrayList<XJLL>();
		
		str+="<div><center><b><font size='+3'>现金流量表</font></b><table border='1'>";
		str+="<tr>" +
				"<td width='310'>项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目</td>" +
				"<td width='40'><center>行次</center></td>" +
				"<td width='190'><center>本年累计金额</center></td>" +
				"<td width='190'><center>本月金额</center></td>" +
				"</tr>";
		
		str+="<tr>" +
				"<td width='310'>一、经营活动产生的现金流量：</td>" +
				"<td width='40'><center>-</center></td>" +
				"<td width='190'><center>-</center></td>" +
				"<td width='190'><center>-</center></td>" +
				"</tr>";
		
		while(rs.next()){
			XJLL ts=new XJLL();
			String BH=rs.getString("BH").trim();
			String MC=rs.getString("MC").trim();
			Double LJ=rs.getDouble("AYE");
			Double BQ=rs.getDouble("BYE");
			
			int stmp=Integer.valueOf(BH);
			if(stmp==3||stmp==4||stmp==5||stmp==6||stmp==10||stmp==11||stmp==14||stmp==15||stmp==16){
				LJ*=-1;
				BQ*=-1;
			}

			int x=Integer.valueOf(BH);
			ts.setBH(x);
			ts.setMC(MC);
			ts.setAYE(BQ);
			ts.setBYE(LJ);
			acc.add(ts);
		}
		for(int i=1;i<=6;i++){
			str+="<tr>" +
					"<td width='310'>"+acc.get(i-1).getMC()+"</td>" +
					"<td width='40'><center>"+String.valueOf(i-1)+"</center></td>" +
					"<td width='190'><center>"+acc.get(i-1).getAYE()+"</center></td>" +
					"<td width='190'><center>"+acc.get(i-1).getBYE()+"</center></td>" +
					"</tr>";
		}
		hj=a=acc.get(0).getAYE()+acc.get(1).getAYE()-acc.get(2).getAYE()
				-acc.get(3).getAYE()-acc.get(4).getAYE()-acc.get(5).getAYE();
		data = new DecimalFormat("#.##").format(a);
		a=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(hj);
		hj=Double.valueOf(data);
		bq=b=acc.get(0).getBYE()+acc.get(1).getBYE()-acc.get(2).getBYE()
				-acc.get(3).getBYE()-acc.get(4).getBYE()-acc.get(5).getBYE();
		data = new DecimalFormat("#.##").format(b);
		b=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(bq);
		bq=Double.valueOf(data);
		str+="<tr>" +
				"<td width='310'>经营活动产生的现金流量净额</td>" +
				"<td width='40'><center>7</center></td>" +
				"<td width='190'><center>"+a+"</center></td>" +
				"<td width='190'><center>"+b+"</center></td>" +
				"</tr>";
		for(int i=7;i<=11;i++){
			str+="<tr>" +
					"<td width='310'>"+acc.get(i-1).getMC()+"</td>" +
					"<td width='40'><center>"+String.valueOf(i+1)+"</center></td>" +
					"<td width='190'><center>"+acc.get(i-1).getAYE()+"</center></td>" +
					"<td width='190'><center>"+acc.get(i-1).getBYE()+"</center></td>" +
					"</tr>";
		}
		hj+=a=acc.get(6).getAYE()+acc.get(7).getAYE()+acc.get(8).getAYE()
				-acc.get(9).getAYE()-acc.get(10).getAYE();
		data = new DecimalFormat("#.##").format(a);
		a=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(hj);
		hj=Double.valueOf(data);
		bq+=b=acc.get(6).getBYE()+acc.get(7).getBYE()+acc.get(8).getBYE()
				-acc.get(9).getBYE()-acc.get(10).getBYE();
		data = new DecimalFormat("#.##").format(b);
		b=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(bq);
		bq=Double.valueOf(data);
		str+="<tr>" +
				"<td width='310'>投资活动产生的现金流量净额</td>" +
				"<td width='40'><center>13</center></td>" +
				"<td width='190'><center>"+a+"</center></td>" +
				"<td width='190'><center>"+b+"</center></td>" +
				"</tr>";
		for(int i=12;i<=16;i++){
			str+="<tr>" +
					"<td width='310'>"+acc.get(i-1).getMC()+"</td>" +
					"<td width='40'><center>"+String.valueOf(i+2)+"</center></td>" +
					"<td width='190'><center>"+acc.get(i-1).getAYE()+"</center></td>" +
					"<td width='190'><center>"+acc.get(i-1).getBYE()+"</center></td>" +
					"</tr>";
		}
		hj+=a=acc.get(11).getAYE()+acc.get(12).getAYE()-acc.get(13).getAYE()
				-acc.get(14).getAYE()-acc.get(15).getAYE();
		data = new DecimalFormat("#.##").format(a);
		a=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(hj);
		hj=Double.valueOf(data);
		bq+=b=acc.get(11).getBYE()+acc.get(12).getBYE()-acc.get(13).getBYE()
				-acc.get(14).getBYE()-acc.get(15).getBYE();
		data = new DecimalFormat("#.##").format(b);
		b=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(bq);
		bq=Double.valueOf(data);
		str+="<tr>" +
				"<td width='310'>筹资活动产生的现金流量净额</td>" +
				"<td width='40'><center>19</center></td>" +
				"<td width='190'><center>"+a+"</center></td>" +
				"<td width='190'><center>"+b+"</center></td>" +
				"</tr>";
		str+="<tr>" +
				"<td width='310'>四、现金净增加额</td>" +
				"<td width='40'><center>20</center></td>" +
				"<td width='190'><center>"+hj+"</center></td>" +
				"<td width='190'><center>"+bq+"</center></td>" +
				"</tr>";
		String[] stmp=money.split(";");
		Double qa=Double.valueOf(stmp[0]);
		Double qb=Double.valueOf(stmp[1]);
		str+="<tr>" +
				"<td width='310'>加：期初现金余额</td>" +
				"<td width='40'><center>21</center></td>" +
				"<td width='190'><center>"+qa+"</center></td>" +
				"<td width='190'><center>"+qb+"</center></td>" +
				"</tr>";
		hj+=qa;
		bq+=qb;
		data = new DecimalFormat("#.##").format(hj);
		hj=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(bq);
		bq=Double.valueOf(data);
		str+="<tr>" +
				"<td width='310'>五、期末现金余额</td>" +
				"<td width='40'><center>22</center></td>" +
				"<td width='190'><center>"+hj+"</center></td>" +
				"<td width='190'><center>"+bq+"</center></td>" +
				"</tr>";
		
		str+="</table></center></div>";
		
		return str;
	}
	
	public void PZPX(String year,String mouth) throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		
		sql="select PZH from pz where [year]="+year+" and [mouth]="+mouth
				+" group by PZH order by PZH";
		
		System.out.println(sql);
		PreparedStatement pstat=conn.prepareStatement(sql);
		
		ResultSet rs=pstat.executeQuery();
		List<String> sqlarray=new ArrayList<String>();
		String str="";
		int count=1;
		
		while(rs.next()){
			int c=rs.getInt("PZH");
			if(c!=count){
				str="UPDATE pz SET PZH="+String.valueOf(count)
						+"where [year]="+year
						+" and [mouth]="+mouth
						+" and PZH="+rs.getString("PZH");
				sqlarray.add(str);
			}
			count++;
		}
		
		sql_add_del_update(sqlarray);
		
	}
	
	public String SYB() throws SQLException, ClassNotFoundException{
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		System.out.println(sql);
		PreparedStatement pstat=conn.prepareStatement(sql);
		
		ResultSet rs=pstat.executeQuery();
		
		String data=null;
		double a=0;
		double b=0;
		double hj=0;
		double bq=0;
		String str="";
		List<SY> acc=new ArrayList<SY>();
		
		str+="<div><center><b><font size='+3'>损益表</font></b><table border='1'>";
		str+="<tr>" +
				"<td width='310'>项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目</td>" +
				"<td width='40'><center>行次</center></td>" +
				"<td width='190'><center>本年累计金额</center></td>" +
				"<td width='190'><center>本月金额</center></td>" +
				"</tr>";
		
		while(rs.next()){
			SY ts=new SY();
			String BH=rs.getString("XH").trim();
			String MC=rs.getString("MC").trim();
			String BQ=rs.getString("BQ").trim();
			String LJ=rs.getString("LJ").trim();

			int x=Integer.valueOf(BH);
			ts.setBH(x);
			ts.setMC(MC);
			ts.setBQ(Double.valueOf(BQ));
			ts.setLJ(Double.valueOf(LJ));
			acc.add(ts);
		}
		
		str+="<tr>" +
				"<td>一、营业收入</td>" +
				"<td><center>1</center></td>" +
				"<td>"+String.valueOf(acc.get(0).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(0).getBQ())+"</td>"+
				"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp减：营业成本</td>" +
				"<td><center>2</center></td>" +
				"<td>"+String.valueOf(acc.get(1).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(1).getBQ())+"</td>"+
						"</tr>";
		
		a=0;b=0;
		for(int i=2;i<=9;i++){
			a+=acc.get(i).getLJ();
			b+=acc.get(i).getBQ();
		}
		data = new DecimalFormat("#.##").format(a);
		a=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(b);
		b=Double.valueOf(data);
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp营业税附加</td>" +
				"<td><center>3</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp其中：消费税</td>" +
				"<td><center>4</center></td>" +
				"<td>"+String.valueOf(acc.get(3).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(3).getBQ())+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp&nbsp;&nbsp营业税</td>" +
				"<td><center>5</center></td>" +
				"<td>"+String.valueOf(acc.get(4).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(4).getBQ())+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp&nbsp;&nbsp城市维护建设税</td>" +
				"<td><center>6</center></td>" +
				"<td>"+String.valueOf(acc.get(5).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(5).getBQ())+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp&nbsp;&nbsp资源税</td>" +
				"<td><center>7</center></td>" +
				"<td>"+String.valueOf(acc.get(6).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(6).getBQ())+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp&nbsp;&nbsp土地增值税</td>" +
				"<td><center>8</center></td>" +
				"<td>"+String.valueOf(acc.get(7).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(7).getBQ())+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp&nbsp;&nbsp城镇土地使用税、房产税、车船税、印花税</td>" +
				"<td><center>9</center></td>" +
				"<td>"+String.valueOf(acc.get(8).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(8).getBQ())+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp&nbsp;&nbsp教育费附加、矿产资源补偿费、排污费</td>" +
				"<td><center>10</center></td>" +
				"<td>"+String.valueOf(acc.get(9).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(9).getBQ())+"</td>"+
						"</tr>";
		
		a=0;b=0;
		for(int i=10;i<=12;i++){
			a+=acc.get(i).getLJ();
			b+=acc.get(i).getBQ();
		}
		data = new DecimalFormat("#.##").format(a);
		a=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(b);
		b=Double.valueOf(data);
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp销售费用</td>" +
				"<td><center>11</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp其中：商品维修给</td>" +
				"<td><center>12</center></td>" +
				"<td>"+String.valueOf(acc.get(11).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(11).getBQ())+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp&nbsp;&nbsp广告费和业务宣传费</td>" +
				"<td><center>13</center></td>" +
				"<td>"+String.valueOf(acc.get(12).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(12).getBQ())+"</td>"+
						"</tr>";
		a=0;b=0;
		for(int i=13;i<=16;i++){
			a+=acc.get(i).getLJ();
			b+=acc.get(i).getBQ();
		}
		data = new DecimalFormat("#.##").format(a);
		a=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(b);
		b=Double.valueOf(data);
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp管理费用</td>" +
				"<td><center>14</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp其中：开办费</td>" +
				"<td><center>15</center></td>" +
				"<td>"+String.valueOf(acc.get(14).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(14).getBQ())+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp&nbsp;&nbsp业务招待费</td>" +
				"<td><center>16</center></td>" +
				"<td>"+String.valueOf(acc.get(15).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(15).getBQ())+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp&nbsp;&nbsp研究费用</td>" +
				"<td><center>17</center></td>" +
				"<td>"+String.valueOf(acc.get(16).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(16).getBQ())+"</td>"+
						"</tr>";
		
		a=0;b=0;
		for(int i=17;i<=18;i++){
			a+=acc.get(i).getLJ();
			b+=acc.get(i).getBQ();
		}
		data = new DecimalFormat("#.##").format(a);
		a=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(b);
		b=Double.valueOf(data);
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp财务费用</td>" +
				"<td><center>18</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp其中：利息费用（收入以“-”号填列）</td>" +
				"<td><center>18</center></td>" +
				"<td>"+String.valueOf(acc.get(18).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(18).getBQ())+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp加：投资收益（损失以“-”号填列）</td>" +
				"<td><center>20</center></td>" +
				"<td>"+String.valueOf(acc.get(19).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(19).getBQ())+"</td>"+
						"</tr>";
		
		a=acc.get(0).getLJ();b=acc.get(0).getBQ();
		for(int i=1;i<=18;i++){
			a-=acc.get(i).getLJ();
			b-=acc.get(i).getBQ();
		}
		a+=acc.get(19).getLJ();
		b+=acc.get(19).getBQ();
		hj=a;bq=b;
		data = new DecimalFormat("#.##").format(a);
		a=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(b);
		b=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(hj);
		hj=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(bq);
		bq=Double.valueOf(data);
		str+="<tr>" +
				"<td>二、营业利润</td>" +
				"<td><center>21</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>"+
						"</tr>";
		a=0;b=0;
		for(int i=20;i<=21;i++){
			a+=acc.get(i).getLJ();
			b+=acc.get(i).getBQ();
		}
		data = new DecimalFormat("#.##").format(a);
		a=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(b);
		b=Double.valueOf(data);
		str+="<tr>" +
				"<td>&nbsp;&nbsp加：营业外收入</td>" +
				"<td><center>22</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp其中：政府补贴</td>" +
				"<td><center>23</center></td>" +
				"<td>"+String.valueOf(acc.get(21).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(21).getBQ())+"</td>"+
						"</tr>";
		a=0;b=0;
		for(int i=22;i<=27;i++){
			a+=acc.get(i).getLJ();
			b+=acc.get(i).getBQ();
		}
		data = new DecimalFormat("#.##").format(a);
		a=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(b);
		b=Double.valueOf(data);
		str+="<tr>" +
				"<td>&nbsp;&nbsp加：营业外支出</td>" +
				"<td><center>24</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp其中：坏账损失</td>" +
				"<td><center>25</center></td>" +
				"<td>"+String.valueOf(acc.get(23).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(23).getBQ())+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp无法收回的长期债券投资损失</td>" +
				"<td><center>26</center></td>" +
				"<td>"+String.valueOf(acc.get(24).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(24).getBQ())+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp无法收回的长期股权投资损失</td>" +
				"<td><center>27</center></td>" +
				"<td>"+String.valueOf(acc.get(25).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(25).getBQ())+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp自然灾害等不可抗力因素造成的损失</td>" +
				"<td><center>28</center></td>" +
				"<td>"+String.valueOf(acc.get(26).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(26).getBQ())+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp税收滞纳金</td>" +
				"<td><center>29</center></td>" +
				"<td>"+String.valueOf(acc.get(27).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(27).getBQ())+"</td>"+
						"</tr>";
		
		a=0;b=0;
		for(int i=20;i<=21;i++){
			a+=acc.get(i).getLJ();
			b+=acc.get(i).getBQ();
		}
		for(int i=22;i<=27;i++){
			a-=acc.get(i).getLJ();
			b-=acc.get(i).getBQ();
		}
		hj+=a;bq+=b;
		data = new DecimalFormat("#.##").format(a);
		a=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(b);
		b=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(hj);
		hj=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(bq);
		bq=Double.valueOf(data);
		str+="<tr>" +
				"<td>三、利润总额（亏损总额以“-”号填列）</td>" +
				"<td><center>30</center></td>" +
				"<td>"+String.valueOf(hj)+"</td>" +
				"<td>"+String.valueOf(bq)+"</td>"+
						"</tr>";
		
		str+="<tr>" +
				"<td>&nbsp;&nbsp减：所得税费用</td>" +
				"<td><center>31</center></td>" +
				"<td>"+String.valueOf(acc.get(28).getLJ())+"</td>" +
				"<td>"+String.valueOf(acc.get(28).getBQ())+"</td>"+
						"</tr>";
		hj-=acc.get(28).getLJ();
		bq-=acc.get(28).getBQ();
		data = new DecimalFormat("#.##").format(hj);
		hj=Double.valueOf(data);
		data = new DecimalFormat("#.##").format(bq);
		bq=Double.valueOf(data);
		str+="<tr>" +
				"<td>四、净利润（净亏损以“-”号填列）</td>" +
				"<td><center>32</center></td>" +
				"<td>"+String.valueOf(hj)+"</td>" +
				"<td>"+String.valueOf(bq)+"</td>"+
						"</tr>";
		
		
		
		str+="</table></center></div>";
		
		return str;
	}
	
	public String ZCFZB() throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		System.out.println(sql);
		PreparedStatement pstat=conn.prepareStatement(sql);
		
		ResultSet rs=pstat.executeQuery();
		
		double a=0;
		double b=0;
		String str="";
		List<TS> acc=new ArrayList<TS>();
		
		str+="<div><center><b><font size='+3'>资产负债表</font></b><table border='1'>";
		str+="<tr>" +
				"<td width='220'><center>资产</center></td>" +
				"<td width='40'><center>行次</center></td>" +
				"<td width='190'><center>年初余额</center></td>" +
				"<td width='190'><center>期末余额</center></td>" +
				"<td width='220'><center>负债和所有者权益</center></td>" +
				"<td width='40'><center>行次</center></td>" +
				"<td width='190'><center>年初余额</center></td>" +
				"<td width='190'><center>期末余额</center></td>" +
				"</tr>";
		str+="<tr>" +
				"<td>流动资产：</td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"<td>流动负债</td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"</tr>";
		
		while(rs.next()){
			TS ts=new TS();
			String BH=rs.getString("XH").trim();
			String MC=rs.getString("MC").trim();
			String AYE=rs.getString("AYE").trim();
			String BYE=rs.getString("BYE").trim();
			String hj=rs.getString("hj").trim();
			int x=Integer.valueOf(BH);
			ts.setBH(x);
			ts.setMC(MC);
			ts.setAYE(Double.valueOf(AYE));
			ts.setBYE(Double.valueOf(BYE));
			ts.setHj(Double.valueOf(hj));
			acc.add(ts);
		}
		System.out.println(acc.size());
		int count=27;
		str+="<tr>" +
				"<td>货币资金</td>" +
				"<td><center>1</center></td>" +
				"<td>"+String.valueOf(acc.get(1-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(1-1).getHj())+"</td>" +
				"<td>短期借款</td>" +
				"<td><center>31</center></td>" +
				"<td>"+String.valueOf(acc.get(27-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(27-1).getHj())+"</td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td>短期投资</td>" +
				"<td><center>2</center></td>" +
				"<td>"+String.valueOf(acc.get(2-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(2-1).getHj())+"</td>" +
				"<td>应付票据</td>" +
				"<td><center>32</center></td>" +
				"<td>"+String.valueOf(acc.get(28-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(28-1).getHj())+"</td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td>应收票据</td>" +
				"<td><center>3</center></td>" +
				"<td>"+String.valueOf(acc.get(3-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(3-1).getHj())+"</td>" +
				"<td>应付账款</td>" +
				"<td><center>33</center></td>" +
				"<td>"+String.valueOf(acc.get(29-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(29-1).getHj())+"</td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td>应收账款</td>" +
				"<td><center>4</center></td>" +
				"<td>"+String.valueOf(acc.get(4-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(4-1).getHj())+"</td>" +
				"<td>预售账款</td>" +
				"<td><center>34</center></td>" +
				"<td>"+String.valueOf(acc.get(30-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(30-1).getHj())+"</td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td>预付账款</td>" +
				"<td><center>5</center></td>" +
				"<td>"+String.valueOf(acc.get(5-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(5-1).getHj())+"</td>" +
				"<td>应付职工薪酬</td>" +
				"<td><center>35</center></td>" +
				"<td>"+String.valueOf(acc.get(31-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(31-1).getHj())+"</td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td>应收股利</td>" +
				"<td><center>6</center></td>" +
				"<td>"+String.valueOf(acc.get(6-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(6-1).getHj())+"</td>" +
				"<td>应交税费</td>" +
				"<td><center>36</center></td>" +
				"<td>"+String.valueOf(acc.get(32-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(32-1).getHj())+"</td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td>应收利息</td>" +
				"<td><center>7</center></td>" +
				"<td>"+String.valueOf(acc.get(7-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(7-1).getHj())+"</td>" +
				"<td>应付利息</td>" +
				"<td><center>37</center></td>" +
				"<td>"+String.valueOf(acc.get(33-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(33-1).getHj())+"</td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td>其他应收款</td>" +
				"<td><center>8</center></td>" +
				"<td>"+String.valueOf(acc.get(8-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(8-1).getHj())+"</td>" +
				"<td>应付利润</td>" +
				"<td><center>38</center></td>" +
				"<td>"+String.valueOf(acc.get(34-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(34-1).getHj())+"</td>" +
				"</tr>";
				
		a=acc.get(9-1).getAYE()+acc.get(10-1).getAYE()+acc.get(11-1).getAYE()+acc.get(12-1).getAYE()+acc.get(13-1).getAYE();
		b=acc.get(9-1).getHj()+acc.get(10-1).getHj()+acc.get(11-1).getHj()+acc.get(12-1).getHj()+acc.get(13-1).getHj();
		count++;
		str+="<tr>" +
				"<td>存货</td>" +
				"<td><center>9</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>" +
				"<td>其他应付款</td>" +
				"<td><center>39</center></td>" +
				"<td>"+String.valueOf(acc.get(35-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(35-1).getHj())+"</td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td>其中：原材料</td>" +
				"<td><center>10</center></td>" +
				"<td>"+String.valueOf(acc.get(10-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(10-1).getHj())+"</td>" +
				"<td>其他流动负债</td>" +
				"<td><center>40</center></td>" +
				"<td>"+String.valueOf(acc.get(36-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(36-1).getHj())+"</td>" +
				"</tr>";
		
		a=0;b=0;
		for(int i=26;i<=35;i++){
			a+=acc.get(i).getAYE();
			b+=acc.get(i).getHj();
		}
		
		str+="<tr>" +
				"<td>在产品</td>" +
				"<td><center>11</center></td>" +
				"<td>"+String.valueOf(acc.get(11-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(11-1).getHj())+"</td>" +
				"<td>流动负债合计</td>" +
				"<td><center>41</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>" +
				"</tr>";

		str+="<tr>" +
				"<td>库存商品</td>" +
				"<td><center>12</center></td>" +
				"<td>"+String.valueOf(acc.get(12-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(12-1).getHj())+"</td>" +
				"<td><center>非流动负债</center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td>周转材料</td>" +
				"<td><center>13</center></td>" +
				"<td>"+String.valueOf(acc.get(13-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(13-1).getHj())+"</td>" +
				"<td>长期借款</td>" +
				"<td><center>42</center></td>" +
				"<td>"+String.valueOf(acc.get(37-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(37-1).getHj())+"</td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td>其他流动资产</td>" +
				"<td><center>14</center></td>" +
				"<td>"+String.valueOf(acc.get(14-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(14-1).getHj())+"</td>" +
				"<td>长期应付款</td>" +
				"<td><center>43</center></td>" +
				"<td>"+String.valueOf(acc.get(38-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(38-1).getHj())+"</td>" +
				"</tr>";
		a=0;b=0;
		for(int i=0;i<=13;i++){
			a+=acc.get(i).getAYE();
			b+=acc.get(i).getHj();
		}
		count++;
		str+="<tr>" +
				"<td><center>流动资产合计</center></td>" +
				"<td><center>15</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>" +
				"<td>递延收益</td>" +
				"<td><center>44</center></td>" +
				"<td>"+String.valueOf(acc.get(39-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(39-1).getHj())+"</td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td><center>非流动资产：</center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"<td>其他非流动负债</td>" +
				"<td><center>45</center></td>" +
				"<td>"+String.valueOf(acc.get(40-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(40-1).getHj())+"</td>" +
				"</tr>";
		a=0;b=0;
		for(int i=36;i<=39;i++){
			a+=acc.get(i).getAYE();
			b+=acc.get(i).getHj();
		}
		str+="<tr>" +
				"<td>长期债券投资</td>" +
				"<td><center>16</center></td>" +
				"<td>"+String.valueOf(acc.get(15-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(15-1).getHj())+"</td>" +
				"<td><center>非流动负债合计</center></td>" +
				"<td><center>46</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>" +
				"</tr>";
		a=0;b=0;
		for(int i=26;i<=39;i++){
			a+=acc.get(i).getAYE();
			b+=acc.get(i).getHj();
		}
		str+="<tr>" +
				"<td>长期股权投资</td>" +
				"<td><center>17</center></td>" +
				"<td>"+String.valueOf(acc.get(16-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(16-1).getHj())+"</td>" +
				"<td><center>负债合计</center></td>" +
				"<td><center>47</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>" +
				"</tr>";
		
		str+="<tr>" +
				"<td>固定资产原价</td>" +
				"<td><center>18</center></td>" +
				"<td>"+String.valueOf(acc.get(17-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(17-1).getHj())+"</td>" +
				"<td><center></center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"</tr>";
		
		str+="<tr>" +
				"<td>减：累计折旧</td>" +
				"<td><center>19</center></td>" +
				"<td>"+String.valueOf(acc.get(18-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(18-1).getHj())+"</td>" +
				"<td><center></center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"</tr>";
		a=acc.get(17-1).getAYE()-acc.get(18-1).getAYE();
		b=acc.get(17-1).getHj()-acc.get(18-1).getHj();
		str+="<tr>" +
				"<td>固定资产账面价值</td>" +
				"<td><center>20</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>" +
				"<td><center></center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"</tr>";
		
		str+="<tr>" +
				"<td>在建工程</td>" +
				"<td><center>21</center></td>" +
				"<td>"+String.valueOf(acc.get(19-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(19-1).getHj())+"</td>" +
				"<td><center></center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"</tr>";
		
		str+="<tr>" +
				"<td>工程物资</td>" +
				"<td><center>22</center></td>" +
				"<td>"+String.valueOf(acc.get(20-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(20-1).getHj())+"</td>" +
				"<td><center></center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"</tr>";
		
		str+="<tr>" +
				"<td>固定资产清理</td>" +
				"<td><center>23</center></td>" +
				"<td>"+String.valueOf(acc.get(21-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(21-1).getHj())+"</td>" +
				"<td><center></center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"</tr>";
		
		str+="<tr>" +
				"<td>生产性生物资产</td>" +
				"<td><center>24</center></td>" +
				"<td>"+String.valueOf(acc.get(22-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(22-1).getHj())+"</td>" +
				"<td>所有者权益（或股东权益）：</td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"<td><center>-</center></td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td>无形资产</td>" +
				"<td><center>25</center></td>" +
				"<td>"+String.valueOf(acc.get(23-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(23-1).getHj())+"</td>" +
				"<td>实收资本（或股本）</td>" +
				"<td><center>48</center></td>" +
				"<td>"+String.valueOf(acc.get(41-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(41-1).getHj())+"</td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td>开发支出</td>" +
				"<td><center>26</center></td>" +
				"<td>"+String.valueOf(acc.get(24-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(24-1).getHj())+"</td>" +
				"<td>资本公积</td>" +
				"<td><center>49</center></td>" +
				"<td>"+String.valueOf(acc.get(42-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(42-1).getHj())+"</td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td>长期待摊费用</td>" +
				"<td><center>27</center></td>" +
				"<td>"+String.valueOf(acc.get(25-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(25-1).getHj())+"</td>" +
				"<td>盈余公积</td>" +
				"<td><center>50</center></td>" +
				"<td>"+String.valueOf(acc.get(43-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(43-1).getHj())+"</td>" +
				"</tr>";
		count++;
		str+="<tr>" +
				"<td>其他非流动资产</td>" +
				"<td><center>28</center></td>" +
				"<td>"+String.valueOf(acc.get(26-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(26-1).getHj())+"</td>" +
				"<td>未分配利润</td>" +
				"<td><center>51</center></td>" +
				"<td>"+String.valueOf(acc.get(44-1).getAYE())+"</td>" +
				"<td>"+String.valueOf(acc.get(44-1).getHj())+"</td>" +
				"</tr>";
		double c=0,d=0;
		for(int i=14;i<=25;i++){
			a+=acc.get(i).getAYE();
			b+=acc.get(i).getHj();
		}
		for(int i=40;i<=43;i++){
			c+=acc.get(i).getAYE();
			d+=acc.get(i).getHj();
		}
		str+="<tr>" +
				"<td><center>非流动资产</center></td>" +
				"<td><center>29</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>" +
				"<td><center>所有者权益（或股东权益）合计</center></td>" +
				"<td><center>52</center></td>" +
				"<td>"+String.valueOf(c)+"</td>" +
				"<td>"+String.valueOf(d)+"</td>" +
				"</tr>";
		c=0;d=0;
		for(int i=0;i<=25;i++){
			
			a+=acc.get(i).getAYE();
			b+=acc.get(i).getHj();
		}
		for(int i=26;i<=43;i++){
			c+=acc.get(i).getAYE();
			d+=acc.get(i).getHj();
		}
		str+="<tr>" +
				"<td><center>资产总计</center></td>" +
				"<td><center>30</center></td>" +
				"<td>"+String.valueOf(a)+"</td>" +
				"<td>"+String.valueOf(b)+"</td>" +
				"<td><center>负债和所有者权益（或股东权益）总计</center></td>" +
				"<td><center>52</center></td>" +
				"<td>"+String.valueOf(c)+"</td>" +
				"<td>"+String.valueOf(d)+"</td>" +
				"</tr>";
		
		str+="</table></center></div>";
		
		return str;
	}

	public int SYJZ(String username,String year,String mouth,String day) throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		System.out.println(sql);
		PreparedStatement pstat=conn.prepareStatement(sql);
		
		ResultSet rs=pstat.executeQuery();
		
		sql="select count(*) from "+
				"(select PZH from pz where [year]="+year+" and [mouth]="+mouth
				+" group by PZH) as kk";

		int PZH=0;
		PZH=Integer.valueOf(conutQuery());
		PZH++;
		int x=0;
		double sum=0;
		String syjz="INSERT INTO %s %s";
		String BM="";
		String JE="";
		String KMMC="";
		List<String> sqlArray=new ArrayList<String>();
		while(rs.next()){
			BM=rs.getString("BM").trim();
			JE=rs.getString("JE").trim();
			KMMC=rs.getString("KMMC").trim();
			if(Double.valueOf(JE)!=0){
				String y;
				if(KMMC.indexOf("收入")>=0){
					y=String.format("VALUES (%s,%s,%s,%s,%s,'%s',%s,%s,getdate(),'%s','%s')",year,mouth,day,
							PZH,x+1,BM,1,Double.valueOf(JE)*-1,
							"结转损益",username);
					sum+=Double.valueOf(JE);
				}else{
					y=String.format("VALUES (%s,%s,%s,%s,%s,'%s',%s,%s,getdate(),'%s','%s')",year,mouth,day,
							PZH,x+1,BM,-1,JE,
							"结转损益",username);
					sum+=Double.valueOf(JE);
				}
				String str=String.format(syjz, 
						"pz ([year],[mouth],[day],[PZH],[XH],[BM],[JD],[JE],[CZRQ],[ZY],[users])",
						y);
				sqlArray.add(str);
				x++;
			}
		}
		if(x==0){
			return 0;
		}
		String y;
		y=String.format("VALUES (%s,%s,%s,%s,%s,'%s',%s,%s,getdate(),'%s','%s')",year,mouth,day,
				PZH,x+1,4103,-1,sum*-1,
				"结转损益",username);
		
		String str=String.format(syjz, 
				"pz ([year],[mouth],[day],[PZH],[XH],[BM],[JD],[JE],[CZRQ],[ZY],[users])",
				y);
		sqlArray.add(str);
		int i=sql_add_del_update(sqlArray);
		System.out.println(PZH);
		return PZH;
	}
	
	public String PZ() throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		System.out.println(sql);
		PreparedStatement pstat=conn.prepareStatement(sql);
		
		ResultSet rs=pstat.executeQuery();
		
		List<PZCX> pzcx=new ArrayList<PZCX>();
		String xx="";
		String str="";
		int i=0;
		
		while(rs.next()){
			if(i==0){
				xx+=rs.getString("PZH")+":";
				xx+=rs.getString("year")+":";
				xx+=rs.getString("mouth")+":";
				xx+=rs.getString("day")+":";
				xx+=rs.getString("XJLLH")+":";
				xx+=rs.getString("SH")+":";
				xx+=rs.getString("ZF")+":";
				xx+=rs.getString("KHBH");
			}
			PZCX stmp=new PZCX();
			stmp.setZY(rs.getString("ZY").trim());
			stmp.setKM(rs.getString("BM").trim()+"："+rs.getString("KMMC").trim());
			if(rs.getString("JD").equals("1")){
				stmp.setJF(rs.getString("JE"));
				stmp.setDF("0");
			}else{
				stmp.setJF("0");
				stmp.setDF(rs.getString("JE"));
			}
			stmp.setXJLLH(rs.getString("XJLLH"));
			pzcx.add(stmp);
			i++;
		}
		
		rs.close();
		pstat.close();
		conn.close();
		
		JSONArray jsonObject = JSONArray.fromObject(pzcx);   
		   
		str = jsonObject.toString();
		System.out.println(str);
		System.out.println(xx);
		return str+"//<>//"+xx;
		
	}
	
	public String SSPHB() throws SQLException, ClassNotFoundException{
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		System.out.println(sql);
		PreparedStatement pstat=conn.prepareStatement(sql);
		
		ResultSet rs=pstat.executeQuery();
		ResultSetMetaData md=rs.getMetaData();
		
		List<SSPHB> SSPHB=new ArrayList<SSPHB>();
		String str="";
		
		
		while(rs.next()){
			SSPHB stmp=new SSPHB();
			stmp.setBM(rs.getString("BM").trim());
			stmp.setKMMC(rs.getString("KMMC").trim());
			String s=rs.getString("YEFX").trim();
			double a=0;
			double ba=0;
			double bb=0;
			if(rs.getString("a")!=null)
				a=Double.valueOf(rs.getString("a").trim());
			if(rs.getString("ba")!=null)
				ba=Double.valueOf(rs.getString("ba").trim());
			if(rs.getString("bb")!=null)
				bb=Double.valueOf(rs.getString("bb").trim());
			if(s.equals("1")){
				stmp.setQCJF(String.valueOf(a));
				stmp.setQCDF("0.00");
				stmp.setBQJF(String.valueOf(ba));
				stmp.setBQDF(String.valueOf(bb));
				stmp.setQMJF(String.valueOf(a+ba-bb));
				stmp.setQMDF("0.00");
			}else{
				stmp.setQCJF("0.00");
				stmp.setQCDF(String.valueOf(a));
				stmp.setBQJF(String.valueOf(ba));
				stmp.setBQDF(String.valueOf(bb));
				stmp.setQMJF("0.00");
				stmp.setQMDF(String.valueOf(a+bb-ba));
			}
			SSPHB.add(stmp);
		}
		
		rs.close();
		pstat.close();
		conn.close();
		
		JSONArray jsonObject = JSONArray.fromObject(SSPHB);   
		   
		str = jsonObject.toString();
		
		return str;
	}
	
	public String StableQuery() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		PreparedStatement pstat=conn.prepareStatement(sql);
		
		ResultSet rs=pstat.executeQuery();
		ResultSetMetaData md=rs.getMetaData();
		
		String str="[";
		
		while(rs.next()){
			if(str.charAt(str.length()-1)==']')
				str+=',';
			if(str.charAt(str.length()-1)=='}')
				str+=',';
			str+="{";
			for(int i=1;i<=md.getColumnCount();i++){
				if(str.charAt(str.length()-1)=='\'')
					str+=',';
				str+="'"+md.getColumnName(i)+"':'";
				str+=rs.getString(md.getColumnName(i)).trim()+"'";
			}
			str+="}";
			
		}
		str+="]";
		System.out.println(str);
		
		rs.close();
		pstat.close();
		conn.close();
		
		return str;
		
		
	}
	
	
	
	public String Query_Account(double num) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		PreparedStatement pstat=conn.prepareStatement(sql);
		System.out.println(sql);
		ResultSet rs=pstat.executeQuery();
		ResultSetMetaData md=rs.getMetaData();
		
		String str="[";
		double sum=num;
		double JF=0;
		double DF=0;
		
		while(rs.next()){
			boolean b=false;
			boolean JD=true;
			boolean YEFX=true;
			if(str.charAt(str.length()-1)==']')
				str+=',';
			if(str.charAt(str.length()-1)=='}')
				str+=',';
			str+="{";
			for(int i=1;i<=md.getColumnCount();i++){
				if(str.charAt(str.length()-1)=='\'')
					str+=',';
				if(md.getColumnName(i).equals("YEFX")){
					String stmp=rs.getString(md.getColumnName(i)).trim();
					if(stmp.equals("1")){
						YEFX=true;
					}else{
						YEFX=false;
					}
				}
				if(md.getColumnName(i).equals("JD")){
					String stmp=rs.getString(md.getColumnName(i)).trim();
					if(stmp.equals("1")){
						stmp="借";
						b=true;
						JD=true;
					}else{
						stmp="贷";
						b=false;
						JD=false;
					}
					str+="'"+md.getColumnName(i)+"':'";
					str+=stmp+"'";
				}else if(md.getColumnName(i).equals("JE")){
					double ix=Double.valueOf(rs.getString(md.getColumnName(i)).trim());
					if(b){
						str+="'JF':'";
						str+=new DecimalFormat("#.##").format(rs.getDouble(md.getColumnName(i)))+"',";
						str+="'DF':'',";
					}else{
						str+="'JF':'',";
						str+="'DF':'";
						str+=new DecimalFormat("#.##").format(rs.getDouble(md.getColumnName(i)))+"',";
					}
					if(JD){
						JF+=ix;
					}else{
						DF+=ix;
					}
					if(YEFX)
						sum=num+JF-DF;
					else
						sum=num+DF-JF;
					str+="'HJ':'";
					str+=String.valueOf(sum)+"'";
				}else{
					str+="'"+md.getColumnName(i)+"':'";
					str+=rs.getString(md.getColumnName(i)).trim()+"'";
					System.out.println(i);
				}
			}
			str+="},";
			
		}
		str+="{";
		for(int i=1;i<=md.getColumnCount();i++){
			if(str.charAt(str.length()-1)=='\'')
				str+=',';
			if(md.getColumnName(i).equals("JE")){
				str+="'JF':'"+JF+"','DF':'"+DF+"',";
				str+="'HJ':'";
				str+=String.valueOf(sum)+"'";
			}else if(md.getColumnName(i).equals("ZY")){
				str+="'"+md.getColumnName(i)+"':'合计：'";
			}else{
				str+="'"+md.getColumnName(i)+"':''";
			}
		}
		str+="}";
		str+="]";
		System.out.println(sql);
		
		rs.close();
		pstat.close();
		conn.close();
		
		return str;
	}
	
	public String kmTreeQuery() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		PreparedStatement pstat=conn.prepareStatement(sql);
		
		ResultSet rs=pstat.executeQuery();
		//ResultSetMetaData md=rs.getMetaData();
		
		List<Menu> menus=new ArrayList<Menu>();
		String menuString="";
		
		while(rs.next()){
			Menu stmp=new Menu();
			stmp.setBM(rs.getString("BM").trim());
			stmp.setFL(rs.getString("FL").trim());
			stmp.setKMMC(rs.getString("KMMC").trim());
			stmp.setLeaf(rs.getBoolean("isleaf"));
			stmp.setLever(rs.getString("lever").trim());
			if(rs.getString("FL").trim().equals("损益类")){
				stmp.setBiao(rs.getString("BMC").trim());
			}else{
				stmp.setBiao(rs.getString("AMC").trim());
			}
			String str="贷";
			if(rs.getString("YEFX").trim().equals("1"))
				str="借";
			stmp.setYEFX(str);
			menus.add(stmp);
		}
		
		int i1=0;
		int i2=0;
		List<Menu> removeM=new ArrayList<Menu>();
		List<Menu> lm2=new ArrayList<Menu>();
		List<Menu> lm3=new ArrayList<Menu>();
		boolean t=false;
		
		for(int i=0;i<menus.size();i++){
			if(menus.get(i).isLeaf() && menus.get(i).getLever().equals("1")){
				t=false;
				i1=0;
				i2=0;
				lm2=new ArrayList<Menu>();
				lm3=new ArrayList<Menu>();
			}else{
				if(!menus.get(i).isLeaf() && menus.get(i).getLever().equals("1")){
					t=false;
					lm2=new ArrayList<Menu>();
					lm3=new ArrayList<Menu>();
					i1=i;
					
				}else if(!menus.get(i).isLeaf() && menus.get(i).getLever().equals("2")){
					t=false;
					lm3=new ArrayList<Menu>();
					i2=i;
					removeM.add(menus.get(i));
				}else if(menus.get(i).getLever().equals("2")){
					lm3=new ArrayList<Menu>();
					lm2.add(menus.get(i));
					menus.get(i1).setChildren(lm2);
					removeM.add(menus.get(i));
				}else if(menus.get(i).getLever().equals("3")){
					
					lm3.add(menus.get(i));
					if(t){
						
					}else{
						lm2.add(menus.get(i2));
						t=true;
					}
					lm2.get(lm2.size()-1).setChildren(lm3);
					menus.get(i1).setChildren(lm2);
					removeM.add(menus.get(i));
				}
				System.out.println(lm2.size());
			}
		}
		
		menus.removeAll(removeM);
		
		rs.close();
		pstat.close();
		conn.close();
		
		   
		JSONArray jsonObject = JSONArray.fromObject(menus);   
		   
		menuString = jsonObject.toString();
		   
		System.out.println(menuString);
		return menuString;
	}
	
	private String customQ() throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		PreparedStatement pstat=conn.prepareStatement(sql);
		
		ResultSet rs=pstat.executeQuery();
		
		String str="[";
		
		while(rs.next()){
			if(str.charAt(str.length()-1)==']')
				str+=',';
			str+="['"+rs.getString("BH").trim()+"','"+rs.getString("KH").trim()+"']";
		}
		str+="]";
		System.out.print(str);
		return str;
		
	}
	
	public int sql_add_del_update(List<String> add_Items_List) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		Statement pstat=conn.createStatement();
		
		for(int x=0;x<add_Items_List.size();x++){
			pstat.addBatch(add_Items_List.get(x));
			System.out.println(add_Items_List.get(x));
			if(x==1000){//可以设置不同的大小；如50，100，500，1000等等
				pstat.executeBatch();
				conn.commit();
				pstat.clearBatch();
			}
		}
		
		int[] i=pstat.executeBatch();
		conn.commit();
		pstat.clearBatch();

		int count=0;
		for(int x=0;x<i.length;x++)
			count+=i[x];
		
		pstat.close();
		conn.close();
		
		return count;
	}
	
//	public int del() throws ClassNotFoundException, SQLException {
//		Class.forName(driver);
//		Connection conn=DriverManager.getConnection(drivermanager, use, password);
//		PreparedStatement pstat=conn.prepareStatement(delsql);
//		
//		int i=pstat.executeUpdate();
//		
//		pstat.close();
//		conn.close();
//		
//		return i;
//	}
//	
//	public int updat() throws ClassNotFoundException, SQLException {
//		Class.forName(driver);
//		Connection conn=DriverManager.getConnection(drivermanager, use, password);
//		PreparedStatement pstat=conn.prepareStatement(updatesql);
//		
//		int i=pstat.executeUpdate();
//		
//		pstat.close();
//		conn.close();
//		
//		return i;
//	}
	
	public static String cus() throws ClassNotFoundException, SQLException{
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		dbc dbc=(dbc)ac.getBean("d");
		
		String str=dbc.customQ();
		
		return str;
		
	}
	
 	public boolean sqlQ() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		PreparedStatement pstat=conn.prepareStatement(sql);
		
		System.out.println(sql);
		ResultSet rs=pstat.executeQuery();
		
		int i=0;
		while(rs.next()){
			i++;
		}
		rs.close();
		pstat.close();
		conn.close();
		if(i>0){
			return true;
		}		
		
		return false;

	}
 	
 	public String conutQuery() throws ClassNotFoundException, SQLException{
 		Class.forName(driver);
		Connection conn=DriverManager.getConnection(drivermanager, use, password);
		PreparedStatement pstat=conn.prepareStatement(sql);
		
		ResultSet rs=pstat.executeQuery();
		
		rs.next();
		String count=rs.getString(1).trim();
 		
		rs.close();
		pstat.close();
		conn.close();
		
		return count;
 	}
	
	public String getUpdatesql() {
		return updatesql;
	}
	public void setUpdatesql(String updatesql) {
		this.updatesql = updatesql;
	}
	public String getDelsql() {
		return delsql;
	}
	public void setDelsql(String delsql) {
		this.delsql = delsql;
	}
	public String getAddsql() {
		return addsql;
	}
	public void setAddsql(String addsql) {
		this.addsql = addsql;
	}
	public String getDrivermanager() {
		return drivermanager;
	}
	public void setDrivermanager(String drivermanager) {
		this.drivermanager = drivermanager;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}

}
