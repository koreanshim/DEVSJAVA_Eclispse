package Lab11;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class auctioneer extends ViewableAtomic
{
	protected int [] bid_list;
	protected int customer_number;
	protected int customer_counter;
	protected int count;
	protected int max_price;
	protected int end_price;
	
	protected String max_bidder;
	protected String end_bidder;
	
	protected auction_msg auction_msg;
	protected bid_msg bid_msg;
	
	
	
	
	
	
	
	

	public auctioneer()
	{
		this("auctioneer", 5);
	}

	public auctioneer(String name, int _customer_number)
	{
		super(name);
    
		addInport("product_in");
		addInport("bid_in");
		addOutport("result_out");		
		addOutport("bid_out");
		
		addOutport("auction_init");
		
		
		customer_counter = _customer_number;
	}
  
	public void initialize()
	{
		bid_list = new int[customer_number +1];
		customer_counter = 0;
		count = 0;
		max_bidder = "0";
		max_price=0;
		end_bidder = "0";
		auction_msg = new auction_msg("none", 0);
		bid_msg = new bid_msg("none", 0, 0);
		
		holdIn("wait", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("wait"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "product_in", i))
				{
					auction_msg = (auction_msg)x.getValOnPort("product_in", i);
					System.out.println(auction_msg.getName()+ ": init_price " +
					auction_msg.post_price);
				
					holdIn("start", 0);
				}
			}
		}
		else if (phaseIs("bidding"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "bid_in", i))
				{
					
					bid_msg = (bid_msg)x.getValOnPort("bid_in", i);
					
					bid_list[bid_msg.customer_id] = bid_msg.bidding_price;
					
					System.out.println("customer#" + bid_msg.customer_id
							+ ": " + bid_msg.bidding_price);
					
					customer_counter++;
					
					if(customer_counter == customer_number)
					{
						customer_counter = 0;
						max_price = bid_list[1];
						max_bidder ="1";
						for(int j =2; j< customer_number +1 ; j++)
						{
							if(max_price<bid_list[j]) {
								max_price = bid_list[j];
								max_bidder = Integer.toString(j);
							}
						}
						auction_msg.post_price = max_price;
						
						count++;
						
						System.out.println("\nstage" + count);
						System.out.println("customer# " + max_bidder + "is winner");
						if(count == 5) 
						{
							count = 0;
							holdIn("result", 0);
						}
						
						else 
						{
							holdIn("sent", 0);
						}	
					}
				}
			}
			
		}
		
	}
  
	public void deltint()
	{
		
		if (phaseIs("start"))
		{			
			holdIn("bidding", INFINITY);
		}
		
		else if (phaseIs("sent"))
		{
			holdIn("bidding",INFINITY);
		}
		else if(phaseIs("result"))
		{
			end_bidder = max_bidder;
			end_price = max_price;
			
			System.out.println(": winner is customer#" + end_bidder
					+ ", result price is " + end_price);
			
			holdIn("wait",INFINITY);
		}
		
		
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("start"))
		{
			m.add(makeContent("auction_init", new auction_msg(
					"auction_msg #a(start)", auction_msg.post_price)));
		}
		else if (phaseIs("sent"))
		{
			m.add(makeContent("bid_out", new auction_msg("auction_msg #a(sent)"
					, auction_msg.post_price)));
		}
		else if (phaseIs("result"))
		{
			m.add(makeContent("result_out", new auction_msg("auction_msg #a(result)"
					, max_price)));	}


		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText();
	}

}

