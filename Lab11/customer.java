package Lab11;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;


import java.util.Random;

public class customer extends ViewableAtomic
{
  
	protected int bid_price;
	
	protected int max_bid_price;
	protected int posted_price;

	protected int customer_id;
	protected auction_msg auction_msg;
	
	Random r = new Random();
	protected int price_elastictiy;
	
	protected bid_msg bid_msg;
	

	public customer()
	{
		this("customer", 20, 0, 0);
	}

	public customer(String name, int _price_elasticity, int _max_bid_price,
			int _customer_id)
	{
		super(name);
    
		addInport("start");
		addInport("bid_in");
		addInport("result_in");
		
		addOutport("bid_out");
		
		price_elastictiy = _price_elasticity;
		max_bid_price = _max_bid_price;
		customer_id = _customer_id;
		
		
		
	}
  
	public void initialize()
	{
		posted_price =0;
		bid_price =0;
		
		auction_msg = new auction_msg("none", 0);
		
		holdIn("wait_1", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("wait_1"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "start", i))
				{
					auction_msg = (auction_msg)x.getValOnPort("start", i);
					
					posted_price = auction_msg.post_price;
					
					bid_price = posted_price+(r.nextInt(price_elastictiy)+1);
					
					if(posted_price > max_bid_price)
					{
						bid_price=0;
					}
					
					holdIn("bidding", 0);
				}
			}
		}
		else if (phaseIs("wait_2"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "bid_in", i))
				{
					auction_msg = (auction_msg)x.getValOnPort("bid_in", i);
					
					posted_price = auction_msg.post_price;
					bid_price = posted_price+(r.nextInt(price_elastictiy)+1);
					
					if(posted_price > max_bid_price)
					{
						bid_price=0;
					}
					
					holdIn("bidding", 0);
				}
				if(messageOnPort(x, "result_in", i));
				{
					holdIn("wait_1", INFINITY);
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("bidding"))
		{
			holdIn("wait_2", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("bidding"))
		{
			m.add(makeContent("bid_out", new bid_msg("bid_msg #" + customer_id,
					customer_id, bid_price)));
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText();
	}

}

