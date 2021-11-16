package Lab09;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class processor extends ViewableAtomic
{
	
	protected Queue q;
	protected entity job;
	protected double processing_time;
	
	protected int size ;
	protected int proc_num;
	protected loss_msg loss_msg;
	protected double temp_time;
	
	public processor()
	{
		this("procQ", 20, 2);
	}

	public processor(String name, double Processing_time, int _size)
	{
		super(name);
		
		size = _size;
    
		addInport("in");
		addOutport("out1");
		addOutport("out2");
		
		proc_num = Integer.parseInt(name.substring(9));
		
		processing_time = Processing_time;
	}
	
	public void initialize()
	{
		loss_msg = new loss_msg("none", 0);
		temp_time = 0;
		
		q = new Queue();
		job = new entity("");
		
		holdIn("passive", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.size(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					job = x.getValOnPort("in", i);
					holdIn("busy", processing_time);
				}
			}
		}
		else if (phaseIs("busy"))
		{
			for (int i = 0; i < x.size(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					if(q.size() < size) {
						entity x_job = x.getValOnPort("in", i);
						q.add(x_job);
					}
					else {
						temp_time = sigma;
						holdIn("loss", 0);
					}
				}
			}
		}
	}
	
	public void deltint()
	{
		if (phaseIs("loss")) {
			holdIn("busy", temp_time);
		}
		else if(phaseIs("busy")) {
			if(!q.isEmpty()) {
				job = (entity)q.removeFirst();
				holdIn("busy", processing_time);
			}
			else {
				job = new entity("");
				loss_msg = new loss_msg("none", 0);
				holdIn("passive", INFINITY);
			}
//			if (!q.isEmpty())
//			{
//				job = (entity) q.removeFirst();
//				
//				holdIn("busy", processing_time);
//			}
//			else
//			{
//				job = new entity("");
//				
//				holdIn("passive", INFINITY);
//			}
		}
	}

	public message out()
	{
		message m = new message();
		
		if (phaseIs("busy"))
		{
			m.add(makeContent("out1", job));
		}
		else if(phaseIs("loss")) {
			loss_msg = new loss_msg("Proc#" + proc_num + " is lost job : ", proc_num);
			m.add(makeContent("out2", loss_msg));
		}
		return m;
	}	
	
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + "queue length: " + q.size()
        + "\n" + "queue itself: " + q.toString();
	}

}



