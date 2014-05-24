
public class InferenceEngine {

	public static void main(String args[])
	{
		System.out.println("Hello World");
		
		ForwardChaining fc = new ForwardChaining("d","p2=> p3; p3 => p1; c => e; b&e => f; f&g => h; p1=>d; p1&p3 => c; a; b; p2;");
		System.out.println(fc.execute());
		
		BackwardChaining bc = new BackwardChaining("d","p2=> p3; p3 => p1; c => e; b&e => f; f&g => h; p1=>d; p1&p3 => c; a; b; p2;");
		System.out.println(bc.execute());
	}
	
	
	
	
}
