package com.reif.mathematics;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	//set comments to true to see log.
	static boolean comments = false;
	static ArrayList<Lineage> masterList = new ArrayList<>();
	static boolean fail = false;
	static ArrayList<String> dummyList = new ArrayList<>();
	static ArrayList<String[]> allPairs = new ArrayList<>();
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		while(s.hasNextLine()){
			String input = s.nextLine();
			if(input.equals("")){ break; }
			String[] parts = input.split(": |:");
			allPairs.add(parts);
			for(int i = 0; i<parts.length; i++){
				parts[i] = parts[i].replaceAll("\\s","");
				//clean up input (remove spaces)
			}
			int size = parts.length;
			if(comments){
				if(parts.length > 1) {
					System.out.println("\n<NEW INPUT dep/root = " + parts[0] + "," + parts[1] + ">");
				} else {
					System.out.println("\n<NEW INPUT root = " + parts[0]+">");
				}
			}
			if(size == 1){
					if(!Main.isInputInALineage(parts[0], null)){
						Lineage l = new Lineage(parts[0]);
						if(comments)System.out.println("LINEAGE CREATION - as Root: " + parts[0]);
						if(comments)l.print(true);
						masterList.add(l);
						Main.updateTemporaryDummyList();
					}	
			} else {
				if(isInputaRoot(parts[0], parts[1])) { 
					int index = returnRelevantIndex(parts[0], "root");
					if(!masterList.get(index).isLoop(parts[0], parts[1], "root")){
						if(!Main.isInputInALineage(parts[0], parts[1])){
							masterList.get(index).order.add(0,parts[1]);
							if(comments) masterList.get(index).print(true);
							Main.updateTemporaryDummyList();
						}
					} else { fail = true; }
				}
				else if(isInputaDependent(parts[0], parts[1])){
					int index = returnRelevantIndex(parts[1], "dependent");
					if(!masterList.get(index).isLoop(parts[0], parts[1], "dependent") && !Main.isInputInALineage(parts[0], parts[1])){
						masterList.get(index).order.add(parts[0]);
						if(comments) masterList.get(index).print(true);
						Main.updateTemporaryDummyList();
					} else { fail = true; }					
				} else {
					Lineage l = new Lineage(parts[0], parts[1]);
					if(comments)System.out.println("LINEAGE CREATION - creating a new lineage because of failed prev tests " + parts[1] + "," + parts[0]);
					masterList.add(l);
					if(comments) l.print(true);
					Main.updateTemporaryDummyList();
				}
			}
			if(comments) { 
				System.out.print("\nCurrent results -> "); 
				printer(); 
				System.out.println("<<Missing output? Press Enter>>");
			}
		}
		Main.combineDependentLineages();
		Main.printer();
		
	}
	
	public static boolean isInputaRoot(String dependent, String root){
		for(int i = 0; i< masterList.size(); i++){
			if(masterList.get(i).getRoot().equals(dependent)) {
				if(comments)System.out.println("ROOT TEST ***PASSED - " + root + "  is a root, lineage gets a new root, root is: " + root);
				return true; 
			} else {
				if(comments)System.out.println("ROOT TEST FAILED - INPUT DEPENDENT MUST EQUAL ROOT OF LINEAGE " + dependent+"/"+masterList.get(i).getRoot());
			}
			
		}
		return false;
	}
	
	public static boolean isInputaDependent(String dependent, String root){
		for(int i = 0; i<masterList.size(); i++){
			if(masterList.get(i).getDependent().equals(root)){ 
				if(comments)System.out.println("DEPENDENT TEST ***PASSED - Lineage gets a new dependent, dependent is: " +dependent);
				return true;
			} else {
				if(comments)System.out.println("DEPENDENT TEST FAILED - INPUT ROOT MUST EQUAL DEPENDENT OF LINEAGE " + root+"/"+masterList.get(i).getDependent());
			}
		}
		return false;
	}
	
	public static int returnRelevantIndex(String s, String location){
		int x = -1;
		for(int i = 0; i<masterList.size(); i++){
			if(location.equals("root")){
				if(masterList.get(i).getRoot().equals(s)){ return i; }
			} else {
				if(masterList.get(i).getDependent().equals(s)){ return i; }
			}
		}
		return x;
	}
	
	public static void updateTemporaryDummyList(){
		Main.dummyList.clear();
		for(int i = 0; i<masterList.size(); i++){
			for(String s: masterList.get(i).order){
				Main.dummyList.add(s);
			}
		}
	}
	
	public static boolean isInputInALineage(String dependent, String root){
				if(root == null && Main.dummyList.contains(dependent)) { 
					if(comments)System.out.println("ALREADY IN A LINEAGE TEST - returns true (for single Root -> "+ "'"+dependent+"' ). [new Lineage not created]");
					return true; }
				if(Main.dummyList.contains(dependent) && Main.dummyList.contains(root)){ 
					if(comments)System.out.println("ALREADY IN A LINEAGE TEST - returns true. [new Lineage not created]");
					return true;
				} else {
					if(comments)System.out.println("ALREADY IN A LINEAGE TEST FAILED - dep/root = " +dependent+"/"+root);
				}
		return false;
	}
	
	public static void combineDependentLineages(){
		if(comments)System.out.println("We are done building lineages, now we must check if these lineages depend on each other.");
		for(String[] st : Main.allPairs){
			if(Main.fail && Main.comments) { System.out.println("Combining lineages not needed, system fail."); break ;}
			if(st.length >1){
				for(int i = 0; i< Main.masterList.size(); i++){
					for(int j = 1; j< Main.masterList.size(); j++){
						if(Main.masterList.get(i).getRoot().equals(st[0]) && Main.masterList.get(j).getDependent().equals(st[1])) {
							if(comments)System.out.print("\n***Combined two Lineages, first lineage was (");
							if(comments)Main.masterList.get(i).print(false);
							if(comments)System.out.print(") second lineage was (");
							if(comments)Main.masterList.get(j).print(false);
							Main.masterList.get(j).order.addAll(Main.masterList.get(i).order);
							if(comments)System.out.print(") New combined lineage is (");
							if(comments)Main.masterList.get(j).print(false);
							if(comments)System.out.print(")");
							Main.masterList.remove(i);
						}
						if(Main.masterList.get(i).getDependent().equals(st[1]) && Main.masterList.get(j).getRoot().equals(st[0])){
							if(comments)System.out.print("\n***Combined two Lineages, first lineage was (");
							if(comments)Main.masterList.get(i).print(false);
							if(comments)System.out.print(") second lineage was (");
							if(comments)Main.masterList.get(j).print(false);
							Main.masterList.get(i).order.addAll(Main.masterList.get(j).order);
							if(comments)System.out.print(") New combined lineage is (");
							if(comments)Main.masterList.get(i).print(false);
							if(comments)System.out.print(")\n");
							Main.masterList.remove(j);
						}
					}
				}
			}
		}
		if(comments)System.out.println("\nLineage dependency check complete, printing final results");
	}
	
	
	public static void printer(){
		System.out.println("\n");
		if(!fail) {
			int counter = 0;
			for(int i = 0; i<masterList.size(); i++){
				for(int j = 0; j<masterList.get(i).order.size(); j++){
					if(counter == 0) { System.out.print(masterList.get(i).order.get(j)); 
					} else {
						System.out.print(", " + masterList.get(i).order.get(j));
					}
				counter++;
				}
			}
			System.out.println("");
		} else {
			System.out.println("System quit due to cyclical dependencies.");
		}
	}

}
