/**
 * ForwardChaining.java
 * Created on 22/5/2014
 * Author: Azim Khazali (1757733)
 */
import java.util.ArrayList;
import java.util.Arrays;
 
// to run simply do a: new FC(ask,tell) and then fc.execute()
// ask is a propositional symbol
// and tell is a knowledge base 
// ask : r
// tell : p=>q;q=>r;p;q;
 
class ForwardChaining{
	// create variables
	public String ask;
	public ArrayList<String> workingList;
	public ArrayList<String> rules;
	public ArrayList<Integer> count;
	public ArrayList<String> queued;
	 
	 
	public ForwardChaining(String a, String t){
		TellSplitter ts = new TellSplitter(t);
		
		// initialize variables
		workingList  = ts.getFacts();
		rules  = ts.getRules();
		queued  = new ArrayList<String>();
		count  = ts.getCount();
		ask = a;
	}
	 
	// method which calls the main fcPresent() method and returns output back to the Inference Engine
	public String printResult(){
		String result = "";
		if (fcPresent()){
				// the method returned true so it is present
				result = "YES: ";
				// for each queued symbol
				for (int i=0;i<queued.size();i++){
						result += queued.get(i)+", ";
					}
				result += ask;	
		}
		else{
				result = "NO";
		}
		return result;		
	}
	 
	// FC algorithm
	public boolean fcPresent(){
	// This will loop if there is any unprocessed workingList
	while(!workingList.isEmpty()){
			// take the first item and process it
		 	String p = workingList.remove(0);
		 	//System.out.println(p);
			// add to queued
		 	
			queued.add(p);
			// for each of the rules....
			for (int i=0;i<rules.size();i++){
				// .... that contain p in its premise
				if (premiseContains(rules.get(i),p)){
				Integer j = count.get(i);
				// reduce count : unknown elements in each premise
				count.set(i,--j);
					// all the elements in the premise are now known
					if (count.get(i)==0){
						// the conclusion has been proven so put into workingList
						String head = rules.get(i).split("=>")[1];
						// have we just proven the 'ask'?
						if (head.equals(ask))
							return true;
						workingList.add(head);					
					}
				}	
			}
		}
		// if we arrive here then ask cannot be queued
		return false;
	}
	 
	// method which checks if p appears in the premise of a given rule	
	// input : rule, p
	// output : true if p is in the premise of rule
	public static boolean premiseContains(String rule, String p)
	{
		String premise = rule.split("=>")[0];
		String[] conjuncts = premise.split("&");
		// check if p is in the premise
		if (conjuncts.length==1)
			return premise.equals(p);
		else
			return Arrays.asList(conjuncts).contains(p);
	}
}
