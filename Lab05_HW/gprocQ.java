package Lab05_HW;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class gprocQ extends ViewableAtomic
{
	//Queue에 대한 변수 추가
	protected Queue q;
	protected entity job;
	protected double processing_time;

	public gprocQ()
	{
		this("gprocQ", 55);
	}

	public gprocQ(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		//Queue 변수 초기화
		q = new Queue();
		
		job = new entity("");
		
		holdIn("passive", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive")) //passive일 때
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
		if (!q.isEmpty()) //q가 비어 있지 않을 때
		{
			job = (entity) q.removeFirst();
			
			holdIn("busy", processing_time);
		}
		else //q가 비어있을 때
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

