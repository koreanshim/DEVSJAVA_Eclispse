package midterm;
import genDevs.modeling.*;

import GenCol.*;
import simView.*;

public class proc_power extends ViewableAtomic
{
	protected job job_ex;
	protected double processing_time;

	public proc_power()
	{
		this("power", 10);
	}

	public proc_power(String name, double Processing_time)
	{
		super(name);
    
		addInport("ariv");
		addOutport("final");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		job_ex = new job("none", 0);
		
		holdIn("passive", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "ariv", i))
				{
					job_ex = (job)x.getValOnPort("ariv", i);
					
					holdIn("busy", processing_time);
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			job_ex = new job("none", 0);
			
			
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
			m.add(makeContent("final", new job("job" + job_ex._num, job_ex._num)));
			System.out.println("Square of " + job_ex._num + " is " + job_ex._num * job_ex._num);
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText()
		+ "\n" + "job: " + job_ex.getName();
	}

}

