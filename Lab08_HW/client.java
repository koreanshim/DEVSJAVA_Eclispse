package Lab08_HW;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class client extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int num1, num2;
	protected int op_num;
	protected String op;
	
	protected request req_msg;
	protected result res_msg;
	
	
	public client() 
	{
		this("user", 30);
	}
  
	public client(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		
		num1 = 0;
		num2 = 0;
		req_msg = new request("none", 0, 0, "none");
		res_msg = new result("none", 0);

		
		holdIn("active", int_arr_time);
	}
  
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("active"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					//사용을 하진 않음
					
				}
			}
		}
	}

	public void deltint()
	{
		if (phaseIs("active"))
		{
			num1 = (int)(Math.random()*100)+1;
			num2 = (int)(Math.random()*100)+1;
			op_num = (int)(Math.random()*4)+1;

			//0+1<=Math.random()*100+1<100+1 �Ǽ�
			//1<=Math.random()*100+1<101 �Ǽ�
				
			
			//연산자의 처리 이루어진다.
			if(op_num == 1) {
				op = "+";
			}
			else if(op_num == 2) {
				op = "-";
			}
			else if(op_num == 3) {
				op = "*";
			}
			else if(op_num == 4) {
				op = "/";
			}
			
			
			
			//req_msg = new request(num1 + " + " + num2, num1, num2);

			req_msg = new request(num1 + op + num2, num1, num2, op);
		
			holdIn("active", int_arr_time);
		}
	}

	public message out()
	{
		message m = new message();
		m.add(makeContent("out", req_msg));
		//m.add(makeContent("out", new request(num1 + " + " +num2, num1, num2));
		
		return m;
	}
  
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time;
	}

}
