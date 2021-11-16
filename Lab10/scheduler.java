package Lab10;

import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class scheduler extends ViewableAtomic {

	protected entity job;
	protected double scheduling_time;
	
	protected int NODE;
	protected int outport_num;
	
	protected double[] q_size_list;
	protected double[] workload_list;
	protected double[] load_rate_list;
	
	protected double min_workload;

	public scheduler() {
		this("scheduler", 0, 4, new int[5]);
	}

	public scheduler(String name, double Processing_time, int _node, int[] _q_size_list) {
		super(name);

		NODE = _node;
		
		addInport("in");
		addOutport("out_loss");

		for(int i = 1; i<=NODE; i++) {
			addInport("in" + i);
			addOutport("out" + i);
		}
		
		scheduling_time = Processing_time;
		
		q_size_list = new double[NODE+1];
		
		for(int i=1; i<=NODE; i++) {
			q_size_list[i] = _q_size_list[i];
		}
	}

	public void initialize() {
		outport_num = 1;
		
		job = new entity("");

		holdIn("passive", INFINITY);
		
		workload_list = new double[NODE+1];
		
		load_rate_list = new double[NODE+1];
	}

	public void deltext(double e, message x) {
		Continue(e);
		
		if (phaseIs("passive")) {
			for (int i = 0; i < x.getLength(); i++) {
				for(int j = 0; j<=NODE; j++) {
					if (messageOnPort(x, "in"+j, i)) {
						workload_list[j]--;
					}
				}
			}
			
			for (int i = 0; i < x.getLength(); i++) {
				if (messageOnPort(x, "in", i)) {
					job = x.getValOnPort("in", i);
					
					System.out.println("workload - ");
					for(int j = 1; j<=NODE; j++) {
						load_rate_list[j] = (workload_list[j]*100/q_size_list[j]);
						System.out.println("\t processor" + j + ":" + load_rate_list[j] + "%");
					}
					
					outport_num = 1;
					min_workload = load_rate_list[1];
					
					for(int j = 1; j <= NODE; j++) {
						if(min_workload > load_rate_list[j]) {
							min_workload = load_rate_list[j];
							outport_num = j;
						}
					}
					if(load_rate_list[outport_num] == 100) {
						System.out.println("!!! Can't schedule " + job.getName());
						holdIn("loss", 0);
					}
					else {
						System.out.println("*processor " + outport_num + "* has minimum workload!");
						holdIn("busy", scheduling_time);
					}
				}
			}
		}
	}

	public void deltint() {
		if (phaseIs("busy")) {
			workload_list[outport_num]++;
			
			job = new entity("");

			holdIn("passive", INFINITY);
		}
		else if(phaseIs("loss")) {
			holdIn("passive", INFINITY);
		}
	}

	public message out() {
		message m = new message();
		if (phaseIs("busy")) {
			m.add(makeContent("out"+outport_num, job));
		}
		else if(phaseIs("loss")) {
			m.add(makeContent("out_loss", job));
		}
		return m;
	}

	public String getTooltipText() {
		return super.getTooltipText() + "\n" + "job: " + job.getName();
	}

}
