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
			out.print("{id:'yhgl',text:'�û�����',leaf:true},");
		}
		
		out.print("{id:'zwcl',text:'������',expanded:true,children:[");
		if(CX==1){
			out.print("{id:'kmgl',text:'��Ŀ����',leaf:true},");
		}
		out.print("{id:'pzzz',text:'ƾ֤����',leaf:true},");
		if(ZZ==1){
			out.print("{id:'pzsh',text:'ƾ֤���',leaf: true},");
			out.print("{id:'syjz',text:'�����ת',leaf:true},");
			out.print("{id:'ydjz',text:'�¶Ƚ�ת',leaf:true},");
		}
		if(CX==1){
			out.print("{id:'bbsc',text:'��������',expanded:true,children:[");
			out.print("{id:'zcfzb',text:'�ʲ���ծ��',leaf:true},");
			out.print("{id:'syb',text:'�����',leaf:true},");
			out.print("{id:'xjllb',text:'�ֽ�������',leaf:true}]},");
			
			out.print("{id:'kmmxz',text:'��Ŀ��ϸ��',leaf:true},");
			out.print("{id:'pzhzb',text:'ƾ֤���ܱ�',leaf:true},");
			out.print("{id:'ssphb',text:'����ƽ���',leaf:true},");
		}
		
		out.print("]}");
		
		out.print("]");
		
		return SUCCESS;
	}
}