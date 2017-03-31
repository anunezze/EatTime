/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */

public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	private Status[] philosophers;
	private boolean silence;
	
	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		
		philosophers = new Status[piNumberOfPhilosophers];
		for(int i = 0; i < piNumberOfPhilosophers; i++)
			philosophers[i] = Status.THINKING;
			
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		// ...
		philosophers[piTID]=Status.HUNGRY;
		
		test(piTID);
		
		if (philosophers[piTID] != Status.EATING);
		try{
			wait();
		}catch(Exception e){
			System.out.println(e);
		}
		
		
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		this.philosophers[piTID] = Status.THINKING;
		test((piTID + philosophers.length-1)%philosophers.length);
		test((piTID+1)%philosophers.length);
		
	}
	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		// ...
	}
	
	/**
	 * Testing if any of the neighbours8 of a philosopher is negbours of the 
	 */
	public synchronized void test(int i){
		if(philosophers[(i-1) % philosophers.length] != Status.EATING && philosophers[i] == Status.HUNGRY && 
				philosophers[(i+1 % philosophers.length)] != Status.EATING)
		{
			philosophers[i] = Status.EATING;
			this.notifyAll();	
		}
	}
	public synchronized void testparole(int i){
		for(int j=0;j<philosophers.length;j++){
			if(philosophers[j]==Status.TALKING){
				try{
					this.wait();
				}catch(Exception e){
					System.out.println(e);
				}
			}
			philosophers[i] = Status.TALKING;
		}
		
		if(philosophers[(i-1) % philosophers.length  ] != Status.EATING && philosophers[i] == Status.HUNGRY && 
				philosophers[(i+1 % philosophers.length)] != Status.EATING)
		{
			philosophers[i] = Status.EATING;
			this.notifyAll();	
		}
	}
}

// EOF
