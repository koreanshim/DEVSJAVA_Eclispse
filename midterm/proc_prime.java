package midterm;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class proc_prime extends ViewableAtomic
{
  
	protected job job_ex;
	protected double processing_time;

	public proc_prime()
	{
		this("prime", 10);
	}

	public proc_prime(String name, double Processing_time)
	{
		super(name);
    
		addInport("ariv");
		addOutport("solved");
		
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
		//job_ex = new job("none", 0);
		
		if (phaseIs("busy"))
		{	
			holdIn("passive", INFINITY);
			
			if(job_ex._num < 2) {
				System.out.println(job_ex._num + " is not prime");
				return;
			}
			
			else if(job_ex._num == 2) {
				System.out.println(job_ex._num + "is prime");
				return;
			}
			
			else if(job_ex._num > 2) {
				for(int i = 2; i < job_ex._num; i++) 
				{
					if(job_ex._num % i == 0) {
						System.out.println(job_ex._num + " is not prime");
						return;
					}
				}
				System.out.println(job_ex._num + " is prime");
				return;
			}
		}
		
	
	}
	
//	public void isPrime() {
//		if(job_ex._num < 2) {
//			System.out.println(job_ex._num + "is not prime");
//			return;
//		}
//		
//		else if(job_ex._num == 2) {
//			System.out.println(job_ex._num + "is prime");
//			return;
//		}
//		
//		else if(job_ex._num > 2) {
//			for(int i = 2; i < job_ex._num; i++) 
//			{
//				if(job_ex._num % i == 0) {
//					System.out.println(job_ex._num + "is not prime");
//				}
//			}
//			return;
//		}
//		else {
//			System.out.println(job_ex._num + "is prime");
//		}
//		
//		return;
//	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
			m.add(makeContent("solved", new job("job" + job_ex._num, job_ex._num)));
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

