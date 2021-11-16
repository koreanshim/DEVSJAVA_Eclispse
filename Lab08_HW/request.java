package Lab08_HW;
import GenCol.*;

public class request extends entity
{   

	int num1, num2;
	String op;
	
	public request(String name, int _num1, int _num2, String _op)
	{  
		super(name);  
		
		num1 = _num1;
		num2 = _num2;
		op = _op;
	}
	
}
