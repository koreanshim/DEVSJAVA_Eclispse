package finalexam;
import simView.*;



import genDevs.modeling.*;
import GenCol.*;
import finalexam.job;

public class genr extends ViewableAtomic
{
	protected job job;
	protected double int_arr_time;
	protected int count;
	protected int option;
	protected int p;
	protected int age;
	protected char plane;
	protected char meal;
	protected int opt_count;
	
	public genr() 
	{
		this("genr", 70);
	}
	
	public void initialize()
	{
		job = new job("none", 0, 'X', 0, '?');
		
		option = (int)(Math.random() * 10) + 1; //1~10, 1~5:RESERVE, 6:CANCEL, 7~10:CHECK
		p = (int)(Math.random() * 2) + 1; //1=A, 2=B
		age = (int)(Math.random() * 78) + 2; //2~80살
		meal = '?';
		
		count = 1;
		opt_count = 0;
		
		holdIn("active", int_arr_time);
	}
  
	public genr(String name, double Int_arr_time)
	{
		super(name);

		addInport("in");
		addOutport("out");
		addOutport("check");

		int_arr_time = Int_arr_time;
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
		if (phaseIs("active"))
		{
			job = new job("none", 0, 'X', 0, '?');
			
			option = (int)(Math.random() * 10) + 1;
			p = (int)(Math.random() * 2) + 1;
			age = (int)(Math.random() * 78) + 2;
			meal = '?';

			count = count + 1;
			
			holdIn("active", int_arr_time);
		}
	}

	public message out()
	{
		message m = new message();
		
		if(p == 1) {
			plane = 'A';
		}
		else if(p == 2) {
			plane = 'B';
		}
		
		System.out.println("\n GENR USER");
		
		if(count == 1) { //무조건 reserve 하는 경우
			option = 1;
			m.add(makeContent("out", new job(
					"user" + count + ", option:" + option + ", plane:" + plane + ", age:" + age,
					option, plane, age, meal)));

			System.out.println("to RESERVE");
			System.out.println("Random Option: " + option);
			System.out.println("Random Plane: " + plane);
			System.out.println("Random Age: " + age);
			System.out.println("Serving Meal: " + meal);
			
			opt_count = 0;
		}
		
		else {
			if(option == 6) { //cancellation
				m.add(makeContent("out", new job(
						"user" + count + ", option:" + option + ", plane:" + plane + ", age:" + age,
						option, plane, age, meal)));
				
				System.out.println("to RESERVE/CANCEL");
				System.out.println("Random Option: " + option);
				System.out.println("Random Plane: " + plane);
				System.out.println("Random Age: " + age);
				System.out.println("Serving Meal: " + meal);
				
				opt_count++; //cancel의 횟수 증가. opt_count == 2될 경우 강제로 무조건 reserve 하는 경우로 넘어감
			}
			
			else if(option > 6) { //check
				m.add(makeContent("check", new job(
						"user" + count + ", option:" + option + ", plane:" + plane + ", age:" + age,
						option, plane, age, meal)));

				System.out.println("to CHECK");
				System.out.println("Random Option: " + option);
				System.out.println("Random Plane: " + plane);
				System.out.println("Random Age: " + age);
				System.out.println("Serving Meal: " + meal);
			}
			
			else if(option < 6) { //reservation
				m.add(makeContent("out", new job(
						"user" + count + ", option:" + option + ", plane:" + plane + ", age:" + age,
						option, plane, age, meal)));
				
				System.out.println("to RESERVE/CANCEL");
				System.out.println("Random Option: " + option);
				System.out.println("Random Plane: " + plane);
				System.out.println("Random Age: " + age);
				System.out.println("Serving Meal: " + meal);
			}
			
		}
		
		return m;
	}
  
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time
        + "\n" + " count: " + count;
	}

}
