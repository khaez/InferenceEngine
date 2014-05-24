import java.util.ArrayList;

/**
 * BackwardChaining.java
 * Created on 22/5/2014
 * Author: Kaishley Lingachetti (4303350)
 */

 
// to run simply do a: new BC(ask,tell) and then bc.execute()
// ask is a propositional symbol
// and tell is a knowledge base
// ask : p
// tell : p=>q;q=>r;r;
 
public class BackwardChaining{
// create variables
	private static String ask;
	private static ArrayList<String> rules;
	private static ArrayList<String> facts;
	private static ArrayList<String> workingList;
	private static ArrayList<String> queued;
	
	public BackwardChaining(String tell, String ask)
	{
		TellSplitter ts = new TellSplitter(tell);
		this.ask = ask;
		rules = ts.getRules();
		facts = ts.getNonRules();
		queued = new ArrayList<String>();
		
		workingList.add(ask);
	}
	
	public String printResult()
	{
		String result = "";
		
		if(bcPresent())
		{
			result = "YES: ";
			for (int i = queued.size()-1; i >= 0; i--)
			{
				if(i == 0)
					result += queued.get(i);
				else
					result += queued.get(i) + ", ";
			}
		}
		else
			result = "NO";
		
		return result;
	}
	
	private boolean bcPresent() 
	{
		while(!workingList.isEmpty())
		{
			String workingValue = workingList.remove(workingList.size()-1);
			
			queued.add(workingValue);
			
			if(!facts.contains(workingValue))
			{
				ArrayList<String> premises = new ArrayList<String>();
				
				for(String rule : rules)
				{
					if(ruleEqualsTo(rule, workingValue))
					{
						String premise = rule.split("=>")[0];
						String[] variables = premise.split("&");
						
						for(String variable : variables)
						{
							if(!workingList.contains(variable))
								premises.add(variable);
						}
					}
				}
				
				if(premises.size() == 0)
					return false;
				else
				{
					for(String premise : premises)
					{
						if(!queued.contains(premise))
							workingList.add(premise);
					}
				}
			}
		}
		return true;
	}
	
	private boolean ruleEqualsTo(String rule, String toCheck)
	{
		String splittedRule = rule.split("=>")[1];
		if(splittedRule.equals(toCheck))
			return true;
		else
			return false;
	}
}