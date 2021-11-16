package Lab11;
import GenCol.*;

public class bid_msg extends entity
{   
	
	int customer_id;
	int bidding_price;
	
	public bid_msg(String name, int _customer_id, int _bidding_price)
	{  
		super(name);  
		
		customer_id = _customer_id;
		bidding_price = _bidding_price;
	}
	
}
