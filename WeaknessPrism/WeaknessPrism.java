/**
 * this is the main program. This process runs and spawns the rest of the values
 */

package WeaknessPrism;

/**
 * @author Bill Dickenson
 *
 */


import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WeaknessPrism { 
    public static void main(String[] args) 
     { 

    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = new Date();    	
    	// format is appname configfile
    	/*
    	 * I would like to make these parameterized (e.g -app appname -level CISQ
    	 * But I don't know how. Need help there
    	 */
    	String appName = args[0];
    	String iFile = args[1];
    	String configFile = args[2];
    	String weaknessLevel = args[3];
    	
    	if (appName.length() == 0 ) {
    		
    		System.out.println("WeaknessStart requires at least an application name");
    		System.out.println("   syntax: Java WeaknessPrism.cls appname systemconfig.vpc");
    		System.out.println("   appname is required");
    		System.out.println("   systemConfig will default to veriSystem.vpc");
    		System.out.println("   while appConfig will default to veri[appname].vpc");   
    		System.out.println("   level is the weakness set we use (CISQ,Sec,Private,All,Cloud)");   
    		System.exit(0);
        } 
    	
    	
    	if (iFile.length() == 0 ) {
            iFile = "veriSystem.vpc";
        } 

    	String veriVersion = "Starting WeaknessPrism - Weakness Inspector - version:1.a "+dateFormat.format(date);
    	System.out.println(veriVersion);
    	
    	// create object vpLog
        veriLogger vpLog = new veriLogger();
        vpLog.makeVeriLog(); 
        
        // create log file from global logs
        
        LogManager lgmngr = LogManager.getLogManager(); 
        // lgmngr now contains a reference to the log manager. 
        Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME); 
        // Getting the global application level logger  
        // from the Java Log Manager 
        log.log(Level.INFO, veriVersion); 
        // Log Created and Initialized - now start to do real work
        // load system config - flat file
        sysConfig systemParam = new sysConfig(iFile);
        log.log(Level.INFO, systemParam.showAllSysProperty());
        // The message has a level of Info 
        // loading the config for the application. 
        appConfig appParam = new appConfig(iFile, "MyApp");
        log.log(Level.INFO, appParam.showAllAppProperty());

        /* 
         * weaknessList is a set of weaknesses ( e.g. CWE-252, CWE-255)
         * Each Weakness inherits most of its methods from WeaknessTemplate
         * Each Weakness will execute in its own thread. Each connects to the DB independently
         * 
         */
        //
        String weaknessList = appParam.getWeaknessList(weaknessLevel);
        // create the threadrunner object to manage these. The constructor runs the objects
        VpThreadRunner threadrunner = new VPThreadRunner(weaknesslist);
       
        		
    }

} 
