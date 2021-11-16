package Lab08_HW;
import java.awt.*;

import simView.*;

public class csm2 extends ViewableDigraph
{

	public csm2()
	{
		super("gpt");
    	
		ViewableAtomic g = new client("g", 30);
		ViewableAtomic p = new server("p", 30);
    	
		add(g);
		add(p);
  
		addCoupling(g, "out", p, "in");
		
     
		addCoupling(p, "out", g, "in");
	}

    
    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(988, 646);
        ((ViewableComponent)withName("g")).setPreferredLocation(new Point(399, 426));
        ((ViewableComponent)withName("p")).setPreferredLocation(new Point(628, 230));
    }
}
