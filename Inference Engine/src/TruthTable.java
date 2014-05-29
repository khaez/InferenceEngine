/**
 * TruthTable.java
 * Created on 20/5/2014
 * Author: Kaishley Lingachetti (4303350)
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;


public class TruthTable {
    private ArrayList<ArrayList<Integer>> truthTable = new ArrayList<ArrayList<Integer>>();		//A 2D ArrayList of values in truth table
    private HashMap<String, Integer> positionTruthTable = new HashMap<String, Integer>();		//position of symbols and rules in truth table
    private ArrayList<String> symbols = new ArrayList<String>();			//list of symbols: p1, p2, d, c, a, etc...
    private ArrayList<String> KB;								//list of horn clauses in the knowledge base, mixture of rules and facts
    private ArrayList<String> rules;						//list of rules: p1&p3 => c, etc...
    private String ask;										//ask symbol : d, etc...
    private int modelsFound = 0;							//number of models found where "ask" is true and KB is true
    
	public TruthTable(String ask, String tell) {
		this.ask = ask;
		TellSplitter ts = new TellSplitter(tell);
		KB = ts.getKB();
		rules = ts.getRules();
		
		
	/*
		for loops below are used to split the KB into symbols and then remove duplicates symbols
	*/
		for(String sentence : KB)		//p1 & p2 => c
		{
			for(String splitRule : sentence.split("=>"))		//p1 & p2, c
			{
				for(String symbol : splitRule.split("&"))	//p1, p2, c
				{
					symbols.add(symbol);
				}
			}
		}
		
		//remove any duplicates symbols
		Set<String> setItems = new LinkedHashSet<String>(symbols);
		symbols.clear();
		symbols.addAll(setItems);
		
	/*
		for loops below are used to map position of symbols and rules to the truth table
	*/
		for(int i = 0; i < symbols.size(); i++)
		{
			positionTruthTable.put(symbols.get(i), i);
		}
		
		for(int i = 0; i < rules.size(); i++)
		{
			int offset = symbols.size();
			positionTruthTable.put(rules.get(i), i + offset);
        }
        
		//generate truth table depending of how many symbols are there
		//and perform calculations
		generateTruthTable(symbols.size());
	}

	private void generateTruthTable(int n) {
        int rows = (int) Math.pow(2,n);			//get number of rows needed depending of how many symbols are there
        
        for (int i=0; i<rows; i++) {			//for each row
        	ArrayList<Integer> columnValuesTruthTable = new ArrayList<Integer>();	//stores values of each symbol and rules on one row
            for (int j=n-1; j>=0; j--) {
                columnValuesTruthTable.add((i/(int) Math.pow(2, j))%2);				//calculate value for particular symbol
            }
            truthTable.add(columnValuesTruthTable);									//add values to truth table
            for (String rule : rules) {												//for each rule
            	String[] splitSide = rule.split("=>");								//split sides of implication symbol
            	int resultVal;
            	if(splitSide[0].contains("&"))			//p1&p2 => c
            	{
            		String[] tempLeftSplit = splitSide[0].split("&");				//split left side into two symbols separated by &
            		String firstLeftSymbol = tempLeftSplit[0];				//p1	//first symbol on left side
            		String secondLeftSymbol = tempLeftSplit[1];				//p2	//second symbol on left side
            		String rightSide = splitSide[1];						//c		//symbol on the right side
            		
            		int valFirstLeftSymbol = getValueTruthTable(firstLeftSymbol, i);		//get value(0,1) of firstLeftSymbol from truth table
            		int valSecondLeftSymbol = getValueTruthTable(secondLeftSymbol, i);		//get value(0,1) of secondLeftSymbol from truth table
            		int valRightSideSymbol = getValueTruthTable(rightSide, i); 				//get value(0,1) of rightSideSymbol from truth table
            		
            		int valLeft = performConjunction(valFirstLeftSymbol, valSecondLeftSymbol);	//p1&p2	//perform conjunction on appropriate values
            																		//p1&p2 => c
            		resultVal = performImplication(valLeft, valRightSideSymbol);	//perform implication with value from above conjunction and rightSideSymbol
            	}
            	else										//p1 => c
            	{
            		String leftSide = splitSide[0];			//p1	//symbol on left side
            		String rightSide = splitSide[1];		//c		//symbol on right side
            		
            		int valLeftSideSymbol = getValueTruthTable(leftSide, i);	//p1	//get value(0,1) of leftSideSymbol from truth table
            		int valRightSideSymbol = getValueTruthTable(rightSide, i);	//c		//get value(0,1) of rightSideSymbol from truth table
            		
            		resultVal = performImplication(valLeftSideSymbol, valRightSideSymbol);	//p1 => c	//perform implication with appropriate values
            	}
            	columnValuesTruthTable.add(resultVal);		//add resultant value of rule in column values
            }
            truthTable.remove(i);							//remove previously added partial column values
            truthTable.add(columnValuesTruthTable);			//update truth table with complete column values
            checkRowTruthTable(i);							//check if current complete row is true
        }
    }
	
	public String printResult() {
		if(modelsFound > 0)						//if models were found
			return "YES: " + modelsFound;		//return results to InferenceEngine
		else
			return "NO";
	}
	
	private int getValueTruthTable(String symbol, int rowTruthTable)
	{
		int positionCol = positionTruthTable.get(symbol);				//lookup position of symbol in truth table
		int value = truthTable.get(rowTruthTable).get(positionCol);		//retrieve value of symbol at particular row(rowTruthTable) in truth table
		return value;													//return value
	}
	
	private int performConjunction(int firstVal, int secondVal)		//return p1&p2
	{
		if(firstVal == 1 && secondVal == 1)							//if p1 is true and p2 is true, return true
			return 1;
		else														//else return false
			return 0;
	}
	
	private int performImplication(int firstVal, int secondVal)		//return p1 => p2
	{
		if(firstVal == 1 && secondVal == 0)							//if p1 is true and p2 is false, return false
			return 0;
		else														//else return true
			return 1;
	}
	
	private void checkRowTruthTable(int rowTruthTable)
	{
		boolean isModel = true;				//initially we set isModel as true and then change to false if truth table values not true
		int askVal = getValueTruthTable(ask, rowTruthTable);		//ASK value in truth table at particular row
		
		if(askVal == 0)					//if ask value is false
			isModel = false;			//not model
		
		for(String sentence : KB)		//for each sentence in KB
		{
			int sentenceVal = getValueTruthTable(sentence, rowTruthTable);		//get sentence value in truth table at particular row
			if(sentenceVal == 0)			//if sentence value false
				isModel = false;			//not model
		}
		
		if(isModel)					//if model
			modelsFound++;			//increment number of models found
	}
	
	/*private void printTruthTable() {
	    for(int i = 0; i < truthTable.size(); i++)
	    {
	    	for(Integer tempCol : truthTable.get(i))
	    	{
	    		System.out.print(tempCol + " ");
	    	}
	    	System.out.println();
	    }
	}*/
}