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
	Status[] philosophers;
	
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

		
	}
	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// ...
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		// ...
	}
	
	public synchronized void test(int i){
		if(philosophers[i-1] != Status.EATING && philosophers[i] == Status.HUNGRY && philosophers[i+1] != Status.EATING)
		{
			philosophers[i] = Status.EATING;
			this.notifyAll();	
		}
	}
}

// EOF
