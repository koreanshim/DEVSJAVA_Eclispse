package midterm_1;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class procQ extends ViewableAtomic
{
	//Queue 변수를 추가
	protected Queue q;
	protected job job_ex;
	protected entity job;
	protected double processing_time;
	protected int num;

	public procQ()
	{
		this("procQ", 10);
	}

	public procQ(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		//Queue 초기화
		q = new Queue();
		
		job_ex = new job("none", 0);
		
		holdIn("passive", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive")) //passive 일 때
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					job = x.getValOnPort("in", i);
					
					holdIn("busy", processing_time);
				}
			}
		}
		else if (phaseIs("busy")) //busy 일 때
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					entity temp = x.getValOnPort("in", i);
					
					q.add(temp); //q에 원소 하나 추가
					
				}
			}
		}
	}
  
	public void deltint()
	{
		if (!q.isEmpty())
		{
			job = (job) q.removeFirst();
			
			holdIn("busy", processing_time);
		}
		else
		{
			job = new entity("");
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
			m.add(makeContent("out", job));
			System.out.println("Proc: ");
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

