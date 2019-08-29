package WeaknessPrism;

import java.util.Properties;

public class appConfig {
	   Properties sysConfigFile;
	   Properties appConfigFile;
	   String Context;
	   private String version;
	   private String[] localWeakness;
	   private String[] projectWeakness;
	   private String[] cisqWeakness;
	   private String[] deepWeakness;   
	      
// if they give us just filename, load	   
	   
public appConfig(String iFile) {
		   this.version = "";
		   this.localWeakness[0] = "";
		   this.projectWeakness[0] = "";
		   this.cisqWeakness[0] = "";
		   this.deepWeakness[0] = "";
		   this.version = "";
	   
		  	
		   sysConfigFile = new java.util.Properties();
		   try {
			   sysConfigFile.load(this.getClass().getClassLoader().
					   getResourceAsStream(iFile));
		   }catch(Exception eta){
			   eta.printStackTrace();
			   System.exit(0);
		   }
	   	}

// file and application name 

public appConfig(String iFile, String appname) {
	// appname config is actually appname.vpc so we dont need a file name
		   this.version = "";
		   this.localWeakness[0] = "";
		   this.projectWeakness[0] = "";
		   this.cisqWeakness[0] = "";
		   this.deepWeakness[0] = "";
			
		   sysConfigFile = new java.util.Properties();
		   try {
			   sysConfigFile.load(this.getClass().getClassLoader().
					   getResourceAsStream(iFile));
		   }catch(Exception eta){
			   eta.printStackTrace();
			   System.exit(0);
		   }
		   
		   // loaded system config, now load appconfig - paths need to be added 
		   String appCfgFileName = appname + ".vpc";
		   appConfigFile = new java.util.Properties();
		   try {
			   appConfigFile.load(this.getClass().getClassLoader().
					   getResourceAsStream(appCfgFileName));
		   }catch(Exception eta){
			   eta.printStackTrace();
			   System.exit(0);
		   }
	   
		   
	   	} 


 
public String getAppProperty(String key)
	   {
		   String value = this.appConfigFile.getProperty(key.toUpperCase());
		   return value;
	   }
public String showAllAppProperty()
	   {
		   String logString = "version:"+this.appConfigFile.getProperty("VERSION");
		   logString = logString + " localWeakness:"+this.appConfigFile.getProperty("LOCAL");
		   logString = logString + " projWeakness:"+this.appConfigFile.getProperty("PROJECT");
		   logString = logString + " cisqWeakness:"+this.appConfigFile.getProperty("CISQ");
		   logString = logString + " deepWeakness:"+this.appConfigFile.getProperty("DEEP");
		   return logString;
	   }	   
		   
public String getWeaknessList(String context)
	   {
		   String value = this.appConfigFile.getProperty(context.toUpperCase());
		   return value;
	   }		   
			   
	   

	}