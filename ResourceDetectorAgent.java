
/* Owner: Dimitrios Gennimatas
 * University of Thessaly . E-CE Department .
 * 7/2/2020 
 * JADE implementation of a Mobile Agent 
 * who finds the PC with the most available JVM Memory 
 * inside a two-container platform and finally migrates to the PC with the most available Memory
 */



import jade.core.*;
import java.lang.Runtime;
import jade.core.behaviours.*;

public class ResourceDetectorAgent extends Agent {
	
	
	int node = 0;
	String betterLocation;
	float mem1 = 0;
	float mem2 = 0;
	float max = 0;
	

	private static final long serialVersionUID = 1L;

public void setup() {
	
	
	addBehaviour(new CyclicBehaviour(this) { 			//Create a new CyclicBehaviour
	
		private static final long serialVersionUID = 1L;

		public void action() {							// implement action method inside addBevahiour
			
			switch(node) {								// Create Finite State Machine 

			case 0:
				String name = System.getenv("COMPUTERNAME"); 
				System.out.println("Free Memory in JVM of:" +name+ " is "+ Runtime.getRuntime().freeMemory() );
				float mem1 = Runtime.getRuntime().freeMemory();
				if(mem1>max) {
					max = mem1;
					
					betterLocation = "Container-1";
				}
				
				node++;
				myAgent.doMove(new ContainerID("Main-Container", null)); 
				break;									
				
				
			case 1:
				String name2 = System.getenv("COMPUTERNAME"); 
				System.out.println("Free Memory in JVM of:" +name2+ " is "+ Runtime.getRuntime().freeMemory() );
				float mem2 = Runtime.getRuntime().freeMemory();
				
				if(mem2>max) {
					max = mem2;
					
					betterLocation =  "Main-Container";
				}
				
				node++;
				break;
				
			
			case 2:
				node++;
				myAgent.doMove(new ContainerID(betterLocation,null));
				break;
				
			case 3:
				System.out.println("Agent moved here" + "("+betterLocation+")"+ " because it has more free JVM Memory .");
				node++;
				break;
				
			
			default:
			myAgent.doDelete(); //delete agent when done to avoid loop of CylicBehaviour
			
			}
		
	}

	
});	


}
}



