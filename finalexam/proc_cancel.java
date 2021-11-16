package finalexam;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class proc_cancel extends ViewableAtomic
{
  
	protected job job_ex;
	protected double processing_time;

	public proc_cancel()
	{
		this("cancel", 20);
	}

	public proc_cancel(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		job_ex = new job("none", 0, 'X', 0, '?');
		
		holdIn("passive", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					job_ex = (job)x.getValOnPort("in", i);
					
					holdIn("busy", processing_time);
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			job_ex = new job("none", 0, 'X', 0, '?');
			
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if(job_ex._option == 6) { //option:6인 경우만 수용(meal에게 전달)
			if (phaseIs("busy"))
			{
				m.add(makeContent("out", new job("Cancel, Age:" + job_ex._age, job_ex._option, job_ex._plane, job_ex._age, job_ex._meal)));
				System.out.println("\n PROC CANCEL");
				System.out.println("Random Option: " + job_ex._option);
				System.out.println("Random Plane: " + job_ex._plane);
				System.out.println("Random Age: " + job_ex._age);
				System.out.println("Serving Meal: " + job_ex._meal);
			}
		}
		else {
			holdIn("passive", INFINITY);
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

