package org.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class CW extends ActionSupport {

	/**
	 * @return
	 * @throws IOException 
	 */
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		int ZD=Integer.valueOf((String) session.getAttribute("ZD"));
		int CX=Integer.valueOf((String) session.getAttribute("CX"));
		int ZZ=Integer.valueOf((String) session.getAttribute("ZZ"));
		
		out.print("[");
		
		if(ZZ==1){
			out.print("{id:'yhgl',text:'用户管理',leaf:true},");
		}
		
		out.print("{id:'zwcl',text:'账务处理',expanded:true,children:[");
		if(CX==1){
			out.print("{id:'kmgl',text:'科目管理',leaf:true},");
		}
		out.print("{id:'pzzz',text:'凭证制作',leaf:true},");
		if(ZZ==1){
			out.print("{id:'pzsh',text:'凭证审核',leaf: true},");
			out.print("{id:'syjz',text:'损益结转',leaf:true},");
			out.print("{id:'ydjz',text:'月度结转',leaf:true},");
		}
		if(CX==1){
			out.print("{id:'bbsc',text:'报表生成',expanded:true,children:[");
			out.print("{id:'zcfzb',text:'资产负债表',leaf:true},");
			out.print("{id:'syb',text:'损益表',leaf:true},");
			out.print("{id:'xjllb',text:'现金流量表',leaf:true}]},");
			
			out.print("{id:'kmmxz',text:'科目明细账',leaf:true},");
			out.print("{id:'pzhzb',text:'凭证汇总表',leaf:true},");
			out.print("{id:'ssphb',text:'试算平衡表',leaf:true},");
		}
		
		out.print("]}");
		
		out.print("]");
		
		return SUCCESS;
	}
}