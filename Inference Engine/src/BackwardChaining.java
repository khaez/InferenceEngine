import java.util.ArrayList;

/*
 * @BackwardChaining.java
 * @Created on 22/5/2014
 * @author Kaishley Lingachetti (4303350)
 */
 
public class BackwardChaining{
// create variables
	private ArrayList<String> rules;
	private ArrayList<String> facts;
	private ArrayList<String> workingList;
	private ArrayList<String> queued;
	
	public BackwardChaining(String ask, String tell)
	{
		TellSplitter ts = new TellSplitter(tell);
		rules = ts.getRules();
		facts = ts.getFacts();
		queued = new ArrayList<String>();
		workingList = new ArrayList<String>();
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