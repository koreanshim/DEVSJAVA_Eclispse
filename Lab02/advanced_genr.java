package Lab02; //패키지 이름 동기화
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class advanced_genr extends ViewableAtomic //pulbic class 이름을 advanced_genr로 변경. genr로 할 시 같은 Lab1에 있는 genr.java랑 충돌한다.
{
	
	protected double int_arr_time;
	protected int count;
  
	public advanced_genr() 
	{
		this("advanced_genr", 20); //advanced_genr가 동작할 때 얼마만큼마다 동작하는지 설정. 현재의 경우에는 첫 동작만 해당
	}
  
	public advanced_genr(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
		
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		count = 1; //Job1부터 시작하므로 count를 1로 초기화
		
		holdIn("active", int_arr_time); //active 시간 동안 int_arr_time 즉 초기값은 20이 입력됨
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
					holdIn("stop", INFINITY);
				}
			}
		}
	}

	public void deltint()
	{
		if (phaseIs("active"))
		{
			count += 1; //Job뒤의 숫자 증가
			
			if(count % 2 == 0) {
				holdIn("active", 50); //짝수자리의 Job이 실행될때 50의 기간 소요
			}
			if(count % 2 == 1) {
				holdIn("active", 40); //홀수자리의 Job이 실행될 때 40의 기간 소요
			}
		}
	}

	public message out()
	{	
		message m = new message(); //message 객체 생성
		m.add(makeContent("out", new entity("Job" + count)));
		//Job뒤에 숫자 즉 count 를 합하여 출력
		return m; //message 객체를 반환
	}
  
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time
        + "\n" + " count: " + count;
	}

}