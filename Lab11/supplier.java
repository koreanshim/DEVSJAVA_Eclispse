package Lab11;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class supplier extends ViewableAtomic
{
	
	protected int initial_price;
  
	public supplier() 
	{
		this("supplier", 2000);
	}
  
	public supplier(String name, int _initial_price)
	{
		super(name);
   
		addOutport("product_out");
		addInport("product_in");
    
		initial_price = _initial_price;
	}
  
	public void initialize()
	{

		holdIn("start", 0);
	}
  
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("active"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "product_in", i))
				{
					holdIn("finished", INFINITY);
				}
			}
		}
	}

	public void deltint()
	{
		if (phaseIs("start"))
		{
			holdIn("wait", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		m.add(makeContent("product_out", new auction_msg("start", initial_price)));
		return m;
	}
  
	public String getTooltipText()
	{
		return
        super.getTooltipText();
	}

}
