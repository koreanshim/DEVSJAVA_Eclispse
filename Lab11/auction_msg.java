package Lab11;
import GenCol.*;

public class auction_msg extends entity
{   
	
	int post_price;
	
	public auction_msg(String name, int _post_price)
	{  
		super(name);  
		
		post_price = _post_price;
	}
	
}
