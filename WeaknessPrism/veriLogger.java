package WeaknessPrism;

//Java program to log actions in Java. Didnt want to use a framework

//import java.io.IOException; 
import java.util.logging.Level; 
import java.util.logging.Logger; 
//import java.util.logging.*; 

class veriLogger { 
 private final static Logger LOGGER =  
             Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 

 // Get the Logger from the log manager which corresponds  
 // to the given name <Logger.GLOBAL_LOGGER_NAME here> 
 // static so that it is linked to the class and not to 
 // a particular log instance because Log Manage is universal 
 public void makeVeriLog() 
 { 
      LOGGER.log(Level.INFO, "Log Started"); 

     // A log of INFO level with the message "My First Log Message" 
 } 
} 

