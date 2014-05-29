import java.util.ArrayList;

/**
 * BackwardChaining.java
 * Created on 22/5/2014
 * Author: Kaishley Lingachetti (4303350)
 */
 
public class BackwardChaining{
// create variables
	private ArrayList<String> rules;
	private ArrayList<String> facts;
	private ArrayList<String> workingList;		//agenda
	private ArrayList<String> queued;			//entailed
	
	public BackwardChaining(String ask, String tell)
	{
		TellSplitter ts = new TellSplitter(tell);
		rules = ts.getRules();						//get rules
		facts = ts.getFacts();						//get facts
		queued = new ArrayList<String>();
		workingList = new ArrayList<String>();
		workingList.add(ask);						//add ask symbol to working list(agenda)
	}
	
	public String printResult()
	{
		String result = "";
		
		if(bcPresent())					//if entailed
		{
			result = "YES: ";
			for (int i = queued.size()-1; i >= 0; i--)		//display in reverse in queued	
			{
				if(i == 0)
					result += queued.get(i);			//if last symbol, no comma
				else
					result += queued.get(i) + ", ";		//if not last symbol, comma
			}
		}
		else
			result = "NO";			//display No if not entailed
		
		return result;
	}
	
	private boolean bcPresent() 
	{
		while(!workingList.isEmpty())		//while working list of symbols not empty
		{
			String workingValue = workingList.remove(workingList.size()-1);		//get symbol to work
			
			queued.add(workingValue);			//add symbol to queued(entailed)
			
			if(!facts.contains(workingValue))	//if symbol is a fact, no need to continue
			{
				ArrayList<String> premises = new ArrayList<String>();
				
				for(String rule : rules)		//for each rule
				{
					if(ruleEqualsTo(rule, workingValue)) //if working symbol is at right side of rule
					{
						String premise = rule.split("=>")[0];		//get left side of rule
						String[] variables = premise.split("&");	//split left side of rule
						
						for(String variable : variables)		//for each symbol on left side of rule
						{
							if(!workingList.contains(variable))		//add symbol to premises if not in workingList(agenda
								premises.add(variable);
						}
					}
				}
				
				if(premises.size() == 0)
					return false;			//return false if no premises
				else
				{
					for(String premise : premises)
					{
						if(!queued.contains(premise))	//if premise not queued(entailed)
							workingList.add(premise);	//add premise to workingList(agenda)
					}
				}
			}
		}
		return true;
	}
	
	private boolean ruleEqualsTo(String rule, String toCheck)
	{
		String splittedRule = rule.split("=>")[1];		//get right side of rule
		if(splittedRule.equals(toCheck))				//check if symbol is on right side of rule
			return true;								//true if yes
		else
			return false;								//false if no
	}
}