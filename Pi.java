//import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Pi{

    public static int numThrows = 0;
    public static int numSuccess = 0;

    public static void monteCarlo(long iter){
	int x = -1;
	int y = -1;
	
	for (int i = 0; i < iter; i++){
	    x = ThreadLocalRandom.current().nextInt(0, 1);
	    y = ThreadLocalRandom.current().nextInt(0, 1);
	    //System.out.println("x: " + x + ", y: " + y);

	    numThrows++;

	    if (x * x + y * y <= 1){
		numSuccess++;
	    }
	}
    }
	    

    public static void main(String[] args){

	int numThreads = -1;
	long iterations = -1;
	Thread myThreads[];
	int x = -1;
	int y = -1;

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
	System.out.println("Ratio = " + (double)numSuccess/(double)numThrows);
	System.out.println("Pi = " + 4 * (double)numSuccess/(double)numThrows);
	
		
    }
}
