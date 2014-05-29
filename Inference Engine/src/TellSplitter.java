/**
 * TellSplitter.java
 * Created on 19/5/2014
 * Author: Kaishley Lingachetti (4303350)
 */

import java.util.ArrayList;

public class TellSplitter {
	
	private ArrayList<String> KB;
	private ArrayList<String> rules;
	private ArrayList<String> facts;
	private ArrayList<Integer> count;

	public TellSplitter(String tell) 
	{
		KB = new ArrayList<String>();
		rules = new ArrayList<String>();
		facts = new ArrayList<String>();
		count = new ArrayList<Integer>();
		
		//removing any spaces
		String tempTell = "";
		for(int i = 0 ; i < tell.length() ; i++) {
			if(tell.charAt(i) != ' ')
				tempTell += tell.charAt(i);
		}
		
		//split the TELL into rules and non rules and store count of rules
		String[] clauses = tempTell.split(";");
		for (int i = 0 ; i < clauses.length; i++)
		{
			KB.add(clauses[i]);
			if (!clauses[i].contains("=>")) 
			{
				// add rules to be processed
				facts.add(clauses[i]);
			}
			else
			{
				// add values(non rules)
				rules.add(clauses[i]);
				count.add(clauses[i].split("&").length);
			}
		}
	}
	
	public ArrayList<String> getKB() 
	{
		return KB;
	}
	
	public ArrayList<String> getRules() 
	{
		return rules;
	}
	
	public ArrayList<String> getFacts() 
	{
		return facts;
	}
	
	public ArrayList<Integer> getCount() 
	{
		return count;
	}
}
