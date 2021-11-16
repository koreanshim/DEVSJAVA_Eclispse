package Lab03;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class proc extends ViewableAtomic
{
  
	protected entity job;
	protected double processing_time;

	public proc()
	{
		this("proc", 40);
		//40으로 맞추는 것이 좋다
	}

	public proc(String name, double Processing_time)
	{
		super(name);
		   
		addOutport("out");
		addInport("in");
    
		processing_time = Processing_time;
		
	}
  
	public void initialize()
	{
		job = new entity("");
		holdIn("passive", INFINITY);
		//간섭이 있기 전까지는 passive 유지하라
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if(messageOnPort(x, "in", i)) {
					job = x.getValOnPort("in", i);
					
					holdIn("busy", processing_time);
				}

			}
		}
		
	}
  
	public void deltint()
	{
		if(phaseIs("busy")) {
			job = new entity("");
			holdIn("passive", INFINITY);
		}


	}

	public message out()
	{
		message m = new message();
		
		if(phaseIs("busy")) {
			m.add(makeContent("out", job));
		}
		
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText()
		+ "\n" + "job: " + job.getName();
	}

}