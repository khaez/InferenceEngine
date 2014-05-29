/**
 * InferenceEngine.java
 * Created on 19/5/2014
 * Author: Azim Khazali (1757733)
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InferenceEngine {

	public static void main(String args[]) throws IOException
	{
		String _tell = "";
		String _ask = "";
		ArrayList<String> inputStrings = new ArrayList<String>();
		
		if(checkInput(args))
		{
			try{
				FileInputStream fr = new FileInputStream(args[1]);
				Scanner sc = new Scanner(fr);
				while(sc.hasNextLine())
				{
					inputStrings.add(sc.nextLine());
				}
				
				for(int i = 0; i < inputStrings.size(); i++)
				{
					//System.out.println(inputStrings.get(i));
					if(inputStrings.get(i).toUpperCase().equals("TELL"))
					{
						_tell = inputStrings.get(i+1);
					}
					if(inputStrings.get(i).toUpperCase().equals("ASK"))
					{
						_ask = inputStrings.get(i+1);
					}
				}
				runEngine(args, _tell, _ask);
				sc.close();
			}catch (FileNotFoundException e){ 
			    System.err.println("FileNotFoundException: " + e.getMessage());
			}
		}
		else
			inputMessage();
	}
	
	// This method will run the engine base on what method is selected
	public static void runEngine(String file[], String tell, String ask)
	{
		// This if statement calls the Truth Table method
		if(file[0].toUpperCase().equals("TT"))
		{
			TruthTable tt = new TruthTable(ask, tell);
			System.out.println(tt.printResult());
		}
		// This if statement calls the Forward Chaining method
		else if(file[0].toUpperCase().equals("FC"))
		{
			ForwardChaining fc = new ForwardChaining(ask, tell);
			System.out.println(fc.printResult());
		}
		// This if statement calls the Backward Chaining method
		else if(file[0].toUpperCase().equals("BC"))
		{
			BackwardChaining bc = new BackwardChaining(ask, tell);
			System.out.println(bc.printResult());
		}
		
	}
	
	// CheckInput method will check if the number of arguments are correct
	public static boolean checkInput(String file[])
	{
		if(file.length != 2)
		{
			return false;
		}
		
		else return true;
	}
	
	// The inputMessage method will display a message if wrong arguments are inserted
	public static void inputMessage()
	{
		System.out.println("\nPlease input the following format:");
		System.out.println("Program Method File.txt");
		System.out.println("1) TT - Truth Table");
		System.out.println("2) FC - Forward Chaining");
		System.out.println("3) BC - Backward Chaining");
	}
}
