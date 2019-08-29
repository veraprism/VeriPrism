/**
 * 
 */
package WeaknessPrism;
import java.text.SimpleDateFormat;  
import java.util.Date; 
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 
  
/**
 * @author Bill Dickenson
 * The goal is to take the list of weaknesses retrieved from appConfig and create a thread for each. 
 * The thread count can be adjusted but for performance reasons, we limited the threadpool for this run to a fixed number
 * That keeps this application from stalling all the other java apps
 * Thread limit is about 50,000 so we have room 
 * 
 * 
 */
// Task class to be executed (Step 1) 
	class threadRunner implements Runnable 
	{ 
	  private String name; 
	  static final int MAX_T = 4;    
	  // I am going to allocate all the memory at once 200 should do it. 
	  WeaknessPrism[] weakness = new WeaknessPrism[200];
	      
	    // This Whole process is repeated MAX_T times
	    public void run(String[] weaknessList) 
	    { 
	        try
	        { 
	        	int i = 0;
	         	for (String temp : weaknessList) {
	            { 
	                if (i==0) 
	                { 
	                    Date d = new Date(); 
	                    SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss"); 
	                    System.out.println("Initialization Time for"
	                            + " task name - "+ name +" = " +ft.format(d));    
	                    //prints the initialization time 
	                }
	                System.out.println("Executing Time for - " + temp);    
	                /* 
	                 * We need a case for each rule. Clumsy but I really felt that reflection was overkill
	                 * if we get above 200, I might change my mind
	                 */
	                    switch (temp) {
	                    case "WeaknessTemplate":
	                    	Runnable weakness[i] = new WeaknessTemplate(i,temp); 
	                        break;
	                    case "CWE-252":
	                    	Runnable weakness[i] = new WeaknessTemplate(i,"test"); 
	                        break;    
	                    }
	                    i++;
	                }
	            // since I "pre-allocated 200, length wont work. So I just counted - and since it counted at the end I had to subtract 1
	            int ruleCount = i--;
	            
	            
	            /* 
	             * if we are set to optimize speed, then grab all the threads we can
	             * if we are set to optimize coop, then lets only grab 4
	             * I hard coded optimize SPEED for now
	             */
	            String OPTIMZE = "SPEED";
	            if (OPTIMZE.toUpperCase() == "SPEED")
	            {
	            	
	            	ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());   
	            }
	            else {
	            	ExecutorService pool = Executors.newFixedThreadPool(MAX_T);
	            }
	            
	            for (i = 0; i < ruleCount; i++) { 
		        // passes the Task objects to the pool to execute (Step 3) 
	            	pool.execute(weakness[i]); 
	            }
		        // pool shutdown ( Step 4) 
		        pool.shutdown();     
	                	            } 
	            System.out.println(name+" complete"); 
	        } 
	          
	        catch(InterruptedException e) 
	        { 
	            e.printStackTrace(); 
	        } 
	    }

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}

	
}