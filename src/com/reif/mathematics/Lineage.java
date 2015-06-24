package com.reif.mathematics;

import java.util.ArrayList;

public class Lineage {
	ArrayList<String> order = new ArrayList<String>();
	
	Lineage(String s){
		order.add(s);
	}
	
	Lineage(String dependent, String root){
		order.add(root);
		order.add(dependent);
	}
	
	public String getRoot(){
		return this.order.get(0);
	}
	
	public String getDependent(){
		return this.order.get(order.size()-1);
	}
	
	public void print(boolean showComment){
		if(showComment)System.out.print("Lineage Updated -> ");
		for(String s: order){
			System.out.print(s + ",");
		}
	}
	
	public boolean isLoop(String dependent, String root, String rootORdep){
		if(rootORdep.equals("root")){
			if(this.order.contains(root)) {
				return true;
			}
		} else {
			if(this.order.contains(dependent)){
				return true;
			}
		}
		return false;
	}
}


