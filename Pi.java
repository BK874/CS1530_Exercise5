import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.*;

public class Pi{

    public static AtomicInteger numThrows = new AtomicInteger(0);
    public static AtomicInteger numSuccess = new AtomicInteger(0);

    public static void monteCarlo(long iter){
	double x = -1.0;
	double y = -1.0;
	
	for (int i = 0; i < iter; i++){
	    x = ThreadLocalRandom.current().nextDouble(0, 1);
	    y = ThreadLocalRandom.current().nextDouble(0, 1);

	    numThrows.incrementAndGet();

	    if (x * x + y * y <= 1){
		numSuccess.incrementAndGet();
	    }
	}
    }
	    

    public static void main(String[] args){

	int numThreads = -1;
	long iterations = -1;
	Thread myThreads[];
	int x = -1;
	int y = -1;
	final Object ref = new Object();

	try {
	    numThreads = Integer.parseInt(args[0]);
	    iterations = Long.parseLong(args[1])/numThreads;
	    if (numThreads < 1 || iterations < 1){
		throw new Exception();
	    }
	} catch (Exception e){
	    System.err.println("Expected positive long as argument!");
	    System.exit(1);
	}
	final long numIter = iterations;

	myThreads = new Thread[numThreads];

	for (int i = 0; i < numThreads; i++){
	    myThreads[i] = new Thread(() -> {
		    //		    synchronized(ref){
		    monteCarlo(numIter);
		    
		});
	    myThreads[i].start();
	}

	for (int i = 0; i < numThreads; i++){
	    try{
		myThreads[i].join();
	    } catch (InterruptedException iex){}
	    
	}

	System.out.println("Total = " + numThrows);
	System.out.println("Inside = " + numSuccess);
	System.out.println("Ratio = " + (AtomicDouble)numSuccess/(AtomicDouble)numThrows);
	System.out.println("Pi = " + 4 * (AtomicDouble)numSuccess/(AtomicDouble)numThrows);
	
		
    }
}
