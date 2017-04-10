/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class DiningPhilosophers
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	/**
	 * This default may be overridden from the command line
	 */
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;
	public static String input;

	/**
	 * Dining "iterations" per philosopher thread
	 * while they are  socializing there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosphers to consult
	 */
	public static Monitor soMonitor = null;
	

	/*
	 * -------
	 * Methods
	 * -------
	 */

	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv)
	{		
		try
		{
			String input="";
			int iPhilosophers;

			if(argv.length >0 && argv[0]!= null){ // if there is a parameter passed to the program set it as the # of philosophers
				input = argv[0];

				iPhilosophers = Integer.parseInt(input);
			}
				
			else // else set default # of philosophers
				iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;		

			// Make the monitor aware of how many philosophers there are
			soMonitor = new Monitor(iPhilosophers);

			// Space for all the philosophers
			Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];



			System.out.println
			(
				iPhilosophers +
				" philosopher(s) came in for a dinner."
			);
			
			// Let 'em sit down
						for(int j = 0; j < iPhilosophers; j++)
						{
							aoPhilosophers[j] = new Philosopher();
							aoPhilosophers[j].start();
						}

			// Main waits for all its children to die...
			// I mean, philosophers to finish their dinner.
			for(int j = 0; j < iPhilosophers; j++)
				aoPhilosophers[j].join();

			System.out.println("All philosophers have left. System terminates normally.");
		}
		catch(InterruptedException e)
		{
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}
		catch(NumberFormatException e){
			System.out.println("% java DiningPhilosopers " + input+"\n\"" + input +"\" is not a positive decimal integer" +
					"\n\nUsage: java DiningPhilosophers[NUMBER_OF_PHILOSOPHERS]\n%");
						System.exit(0);
		}
	} // main()

	/**
	 * Outputs exception information to STDERR
	 * @param poException Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException)
	{
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}
}

// EOF
