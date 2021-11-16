package midterm;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class genr extends ViewableAtomic
{
	protected job job;
	protected double int_arr_time;
	protected int count;
	protected int rand;
  
	public genr() 
	{
		this("genr", 30);
	}
  
	public genr(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		job = new job("none", 0);
		
		rand = (int)(Math.random() * 100) + 1;
		count = 1;
		
		holdIn("active", int_arr_time);
	}
  
	public void deltext(double e, message x)
	{
		Continue(e);
//		if (phaseIs("active"))
//		{
//			for (int i = 0; i < x.getLength(); i++)
//			{
//				if (messageOnPort(x, "in", i))
//				{
//					holdIn("stop", INFINITY);
//				}
//			}
//		}
	}

	public void deltint()
	{
		job = new job("none", 0);

		
		if (phaseIs("active"))
		{
			rand = (int)(Math.random() * 100) + 1;
			count += 1;
			
			holdIn("active", int_arr_time);
		}
	}

	public message out()
	{
		message m = new message();
		m.add(makeContent("out", new job("job" + rand, rand)));
		System.out.println("\nRandom Number: " + rand);
		return m;
	}
  
	public String getTooltipText()
	{
		return
        super.getTooltipText();
	}

}
