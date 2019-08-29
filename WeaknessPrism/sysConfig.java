package WeaknessPrism;

import java.util.Properties;

public class sysConfig {
   Properties configFile;
   public sysConfig(String iFile) {
	  	
	   configFile = new java.util.Properties();
	   try {
		   configFile.load(this.getClass().getClassLoader().
				   getResourceAsStream(iFile));
	   }catch(Exception eta){
		   eta.printStackTrace();
		   System.exit(0);
	   }
   	}
 
   public String getProperty(String key)
   {
	   String value = this.configFile.getProperty(key);
	   return value;
   }
   public String showAllSysProperty()
   {
	   String logString = "db:"+this.configFile.getProperty("DB");
	   logString = logString + " ip:"+this.configFile.getProperty("IP");
	   logString = logString + " threads:"+this.configFile.getProperty("THREADS");
	   logString = logString + " UID:"+this.configFile.getProperty("UID");
	   logString = logString + " APPCFG:"+this.configFile.getProperty("APPCFG");
	   return logString;
   }
}