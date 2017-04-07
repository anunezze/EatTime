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
		int id= piTID;
		philosophers[id]=Status.HUNGRY;
		
		while(philosophers[mod((id-1),philosophers.length)] == Status.EATING ||  
				philosophers[mod((id+1),philosophers.length)] == Status.EATING)
		{
		try{
			wait();
		}catch(Exception e){
			System.out.println(e);
		}
		}
		philosophers[id] = Status.EATING;
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		this.philosophers[piTID] = Status.THINKING;
		notifyAll();
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
