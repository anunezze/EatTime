/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */

public class Monitor
{
	//this method does the correct modulus
	public static synchronized int mod(int a, int b)
    {
        if (a < 0)
            return b + (a % b);
        else
            return a % b;
    }
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	

	private Status[] philosophers;
	private boolean silence;

	int talktrack=0;

	
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
		//System.out.println("ID:"+piTID);
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
		test(mod((piTID + philosophers.length-1),philosophers.length));
		test(mod((piTID+1),philosophers.length));
	}
	/**
	 * Only one philosopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk(int piTID)
	{

		testparole(piTID);

	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{

		talktrack=0;

		
	}
	
	/**
	 * Testing if any of the neighbours8 of a philosopher is negbours of the 
	 */
	public synchronized void test(int i){
		//System.out.println("test:"+i+" result:"+mod((i-1), philosophers.length));
		if(philosophers[mod((i-1),philosophers.length)] != Status.EATING && philosophers[i] == Status.HUNGRY && 
				philosophers[mod((i+1),philosophers.length)] != Status.EATING)
		{
			philosophers[i] = Status.EATING;	
		}
		this.notify();
	}
	
	public synchronized void testparole(int i){
		while(talktrack!=0){
			try{
				wait();
			}catch(Exception e){
				System.out.println(e);
			}
			talktrack=i;
		}
		
		   
		/*for(int j=0;j<philosophers.length;j++){
			if(philosophers[j]==Status.TALKING){
				try{
					this.wait();
				}catch(Exception e){
					System.out.println(e);
				}
			}
			philosophers[i] = Status.TALKING;
		}*/
		
	}
}

// EOF
