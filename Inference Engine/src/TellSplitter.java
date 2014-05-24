/**
 * TellSplitter.java
 * Created on 19/5/2014
 * Author: Kaishley Lingachetti (4303350)
 */

import java.util.ArrayList;

public class TellSplitter {
	
	private static ArrayList<String> rules;
	private static ArrayList<String> nonRules;
	private static ArrayList<Integer> count;

	public TellSplitter(String tell) 
	{
		rules = new ArrayList<String>();
		nonRules = new ArrayList<String>();
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
			if (!clauses[i].contains("=>")) 
			{
				// add rules to be processed
				nonRules.add(clauses[i]);
			}
			else
			{
				// add values(non rules)
				rules.add(clauses[i]);
				count.add(clauses[i].split("&").length);
			}
		}
	}
	
	public ArrayList<String> getRules() 
	{
		return rules;
	}
	
	public ArrayList<String> getNonRules() 
	{
		return nonRules;
	}
	
	public ArrayList<Integer> getCount() 
	{
		return count;
	}
}
