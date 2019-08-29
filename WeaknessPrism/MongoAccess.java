package WeaknessPrism;

import com.mongodb.client.FindIterable; 
import com.mongodb.client.MongoCollection; 
import com.mongodb.client.MongoDatabase;  
import com.mongodb.BasicDBObject;
import org.bson.Document;

import java.util.Iterator; 


import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;  

public class MongoAccess { 
	
private static MongoClient mongo;	
	
	
	public static MongoDatabase connectDB()
	{
	      
	      // Creating a Mongo client
		String hostname = "localhost";
		mongo = new MongoClient( hostname , 27017 ); 

	      // Creating Credentials 
	      MongoCredential credential;
	      credential = MongoCredential.createCredential("sampleUser", "VeriPrism", "password".toCharArray()); 
	      System.out.println("Connected to the database successfully using "+ credential);  
	      
	      // Accessing the database 
	      MongoDatabase database = mongo.getDatabase("VeriPrism");  
	      return(database);
	}
	
	public static void closeDB()
	{
		mongo.close();
	
	}
	
	public static MongoCollection<Document> Collection(String collectname, MongoDatabase database)
	{
	     // Retrieving a collection 
	      MongoCollection<Document> collection = database.getCollection(collectname);
	      System.out.println("Collection " + collectname+ " selected successfully"); 
	      return(collection);
	}
	
   
   public static void main( String args[] ) {  
      
      MongoDatabase database = connectDB();
      MongoCollection<Document> collection = Collection("callGraph", database);
     // Getting the iterable object 
     
      BasicDBObject query = new BasicDBObject("loc", new BasicDBObject("$gt", 70));
      
      FindIterable<Document> iterDoc = collection.find(query); 
           
      // Getting the iterator 
      Iterator it = iterDoc.iterator(); 
    
      while (it.hasNext()) {  
         System.out.println("Vulnerability:" + it.next());  
    
      }
      
      closeDB();    
      
   } 
}