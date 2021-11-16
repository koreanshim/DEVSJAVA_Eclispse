package finalexam;
import genDevs.modeling.*;



import GenCol.*;
import simView.*;

public class proc_meal extends ViewableAtomic
{
  
	protected job job_ex;
	protected double processing_time;

	public proc_meal()
	{
		this("meal", 20);
	}

	public proc_meal(String name, double Processing_time)
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
		
		System.out.println("\n PROC MEAL");
		
		if(job_ex._option == 6) { //cancellation의 경우
			if((job_ex._age >= 2 && job_ex._age <= 8) || (job_ex._age >= 65 && job_ex._age <= 80)) {
				job_ex._meal = 'D'; //decrease
				if (phaseIs("busy"))
				{
					m.add(makeContent("out", new job("Passenger-1, Meal:" + job_ex._meal + "-1 meal", job_ex._option, job_ex._plane, job_ex._age, job_ex._meal)));
					System.out.println("Passenger-1, Meal-1");
					System.out.println("Random Option: " + job_ex._option);
					System.out.println("Random Plane: " + job_ex._plane);
					System.out.println("Random Age: " + job_ex._age);
					System.out.println("Serving Meal: " + job_ex._meal);
				}
			}
			else {
				job_ex._meal = 'S'; //Stay
				if (phaseIs("busy"))
				{
					m.add(makeContent("out", new job("Passenger-1, Meal:" + job_ex._meal + " -0 meal", job_ex._option, job_ex._plane, job_ex._age, job_ex._meal)));
					System.out.println("Passenger-1, Meal-0");
					System.out.println("Random Option: " + job_ex._option);
					System.out.println("Random Plane: " + job_ex._plane);
					System.out.println("Random Age: " + job_ex._age);
					System.out.println("Serving Meal: " + job_ex._meal);
				}
			}
		}
		else { //reservation
			if((job_ex._age >= 2 && job_ex._age <= 8) || (job_ex._age >= 65 && job_ex._age <= 80)) {
				job_ex._meal = 'Y'; //yes
				if (phaseIs("busy"))
				{
					m.add(makeContent("out", new job("Passenger+1, Meal:" + job_ex._meal +" +1 meal", job_ex._option, job_ex._plane, job_ex._age, job_ex._meal)));
					System.out.println("Passenger+1, Meal+1");
					System.out.println("Random Option: " + job_ex._option);
					System.out.println("Random Plane: " + job_ex._plane);
					System.out.println("Random Age: " + job_ex._age);
					System.out.println("Serving Meal: " + job_ex._meal);
				}
			}
			else {
				job_ex._meal = 'N'; //no
				if (phaseIs("busy"))
				{
					m.add(makeContent("out", new job("Passenger+1, Meal:" + job_ex._meal + " +0 meal", job_ex._option, job_ex._plane, job_ex._age, job_ex._meal)));
					System.out.println("Passenger+1, Meal+0");
					System.out.println("Random Option: " + job_ex._option);
					System.out.println("Random Plane: " + job_ex._plane);
					System.out.println("Random Age: " + job_ex._age);
					System.out.println("Serving Meal: " + job_ex._meal);
				}
			}
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

