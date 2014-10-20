package org.JSON;
import java.util.List;



public class Menu {    
		private String BM;   
		private String KMMC;   
		private String FL;
		private String YEFX;
		private boolean leaf;   
		private String lever;   
		private String biao;
		private List<Menu> children;
		
		
		public String getBM() {
			return BM;
		}
		public void setBM(String bM) {
			BM = bM;
		}
		public String getKMMC() {
			return KMMC;
		}
		public void setKMMC(String kMMC) {
			KMMC = kMMC;
		}
		public String getYEFX() {
			return YEFX;
		}
		public void setYEFX(String yEFX) {
			YEFX = yEFX;
		}
		public String getFL() {
			return FL;
		}
		public void setFL(String fL) {
			FL = fL;
		}
		public boolean isLeaf() {
			return leaf;
		}
		public void setLeaf(boolean leaf) {
			this.leaf = leaf;
		}
		public String getLever() {
			return lever;
		}
		public void setLever(String lever) {
			this.lever = lever;
		}
		public List<Menu> getChildren() {
			return children;
		}
		public void setChildren(List<Menu> children) {
			this.children = children;
		}
		public String getBiao() {
			return biao;
		}
		public void setBiao(String biao) {
			this.biao = biao;
		}
		
	}
