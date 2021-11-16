package finalexam;
import genDevs.modeling.*;
import GenCol.*;
import finalexam.job;
import simView.*;
import java.util.Arrays;

public class proc_check extends ViewableAtomic
{
  
	protected job job_ex;
	protected double processing_time;
	protected char[] a_plane = new char[40];
	protected char[] b_plane = new char[40];
	protected int a_count;
	protected int b_count;
	protected int a_meal;
	protected int b_meal;
	
	public proc_check()
	{
		this("check", 20);
	}

	public proc_check(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		job_ex = new job("none", 0, 'X', 0, '?');
		
		for(int i = 0; i < a_plane.length; i++) {
			a_plane[i] = 'E'; //empty로 초기화
		}
		for(int i = 0; i < b_plane.length; i++) {
			b_plane[i] = 'E'; //empty로 초기화
		}
		a_count = 0;
		b_count = 0;
		a_meal = 0;
		b_meal = 0;
		
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
		//예약
		//A_PLANE
		if(job_ex._plane == 'A') {
			if(job_ex._meal == 'N') { //empty->occupied
				if(a_count >= 0 && a_count < 40) {
					a_count++;
					int temp = 0;
					for(int i = 0; i < 40; i++) {
						if(a_plane[i] == 'E') {
							temp = i;
							break;
						}
					}
					a_plane[temp] = 'O';
				}
			}
			if(job_ex._meal == 'Y') { //empty->meal
				if(a_count >= 0 && a_count < 40) {
					a_count++;
					a_meal++;
					int temp = 0;
					for(int i = 0; i < 40; i++) {
						if(a_plane[i] == 'E') {
							temp = i;
							break;
						}
					}
					a_plane[temp] = 'M';
				}
			}
		}
		//B_PLANE
		else if(job_ex._plane == 'B') { 
			if(job_ex._meal == 'N') { //empty->occupied
				if(b_count >= 40) {
					b_count = 40;
				}
				else if(b_count >= 0 && b_count < 40) {
					b_count++;
					int temp = 0;
					for(int i = 0; i < 40; i++) {
						if(b_plane[i] == 'E') {
							temp = i;
							break;
						}
					}
					b_plane[temp] = 'O';
				}
			}
			if(job_ex._meal == 'Y') { //empty->meal
				if(b_count >= 0 && b_count < 40) {
					b_count++;
					b_meal++;
					int temp = 0;
					for(int i = 0; i < 40; i++) {
						if(b_plane[i] == 'E') {
							temp = i;
							break;
						}
					}
					b_plane[temp] = 'M';
				}
			}
		}
		//취소 LRU방식으로 제일 처음 예약한 순부터 Cancel
		//A_PLANE
		if(job_ex._plane == 'A') {
			if(job_ex._meal == 'S') { //occupied->empty
				if(a_count > 0 && a_count <= 40) {
					a_count--;
					int temp = 0;
					for(int i = 0; i < 40; i++) {
						if(a_plane[i] == 'O') {
							temp = i;
							break;
						}
					}
					a_plane[temp] = 'E';
				}
			}
			if(job_ex._meal == 'D') { //meal->empty
				if(a_count >= 0 && a_count < 40) {
					a_count--;
					a_meal--;
					int temp = 0;
					for(int i = 0; i < 40; i++) {
						if(a_plane[i] == 'M') {
							temp = i;
							break;
						}
					}
					a_plane[temp] = 'E';
				}
			}
		}
		//B_PLANE
		else if(job_ex._plane == 'B') {
			if(job_ex._meal == 'S') { //occupied->empty
				if(b_count > 0 && b_count <= 40) {
					b_count--;
					int temp = 0;
					for(int i = 0; i < 40; i++) {
						if(b_plane[i] == 'O') {
							temp = i;
							break;
						}
					}
					b_plane[temp] = 'E';
				}
			}
			if(job_ex._meal == 'D') { //meal->empty
				if(b_count > 0 && b_count <= 40) {
					b_count--;
					b_meal--;
					int temp = 0;
					for(int i = 0; i < 40; i++) {
						if(b_plane[i] == 'M') {
							temp = i;
							break;
						}
					}
					b_plane[temp] = 'E';
				}
			}
		}
		
		
		
		message m = new message();
		
		System.out.println("\n PROC CHECK");

		if(job_ex._option > 6) {
			if(a_count == 40 && b_count == 40) {
				
				m.add(makeContent("out", new job("Both Plane's Seat Full!", job_ex._option, job_ex._plane, job_ex._age, job_ex._meal)));
				System.out.println("---RESERVATION ENDED---");
				System.out.println("\n---RESERVATION STATUS---");
				System.out.println("A_plane Occupied Seats: " + a_count);
				System.out.println("A_plane Unoccupied Seats: " + (40 - a_count));
				System.out.println("A_plane Served Meals: " + a_meal);
				System.out.println("\nB_plane Occupied Seats: " + b_count);
				System.out.println("B_plane Unoccupied Seats: " + (40 - b_count));
				System.out.println("B_plane Served Meals: " + b_meal);
				
				System.out.println("\nE = Empty. O = Occupied. M = Meal Served.");
				System.out.println("A_plane seats from 1st to 40th seat: \n" + Arrays.toString(a_plane));
				System.out.println("B_plane seats from 1st to 40th seat: \n" + Arrays.toString(b_plane));
				
				System.exit(0);
			}
			if (phaseIs("busy"))
			{
				m.add(makeContent("out", new job("Meal/Passenger=> A Plane:" + a_meal + "/" + a_count + ", B Plane:" + b_meal + "/" + b_count
						, job_ex._option, job_ex._plane, job_ex._age, job_ex._meal)));
				System.out.println("from GENR");
				System.out.println("A_plane Occupied Seats: " + a_count);
				System.out.println("A_plane Unoccupied Seats: " + (40 - a_count));
				System.out.println("A_plane Served Meals: " + a_meal);
				System.out.println("\nB_plane Occupied Seats: " + b_count);
				System.out.println("B_plane Unoccupied Seats: " + (40 - b_count));
				System.out.println("B_plane Served Meals: " + b_meal);
				
				System.out.println("\nE = Empty. O = Occupied. M = Meal Served.");
				System.out.println("A_plane seats from 1st to 40th seat: \n" + Arrays.toString(a_plane));
				System.out.println("B_plane seats from 1st to 40th seat: \n" + Arrays.toString(b_plane));
			}
			else {
				System.out.println("from MEAL");
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

