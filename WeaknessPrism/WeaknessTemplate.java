package WeaknessPrism;

import com.mongodb.client.FindIterable; 
import com.mongodb.client.MongoCollection; 
import com.mongodb.client.MongoDatabase;  
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

import org.bson.Document;

import java.util.Iterator;
import java.util.List;


class WeaknessTemplate implements Runnable {
	
	String weaknessName = "";
	long checksRun = 0;
	long vulFound = 0;
	int linestart = 0;
	int lineend = 0;
	int colstart = 0;
	int colend = 0;
	String methodname = "";
	long weaknessID = 0;
	MongoDatabase database;
	private static MongoClient mongo;	
	private Performance performance;

	public WeaknessTemplate() 
	{
		super();
		this.weaknessID = 000;
		this.weaknessName = "contruct";
		
		this.database = connectDB();
	}	
	public WeaknessTemplate(String weaknessName) 
	{
		super();
		this.weaknessID = 0;
		this.weaknessName = weaknessName;
		
		this.database = connectDB();
	}
	
	public void start() 
	{
		this.weaknessID = 0;
		this.database = connectDB();
		this.performance = new Performance(weaknessID);
	}	
	
	public MongoDatabase connectDB()
	{
	      
	      // Creating a Mongo client 
	      mongo = new MongoClient( "localhost" , 27017 ); 

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
	
	
	
	public MongoCollection<Document> Collection(String collectname)
	{
	     // Retrieving a collection 
	      MongoCollection<Document> collection = database.getCollection(collectname);
	      System.out.println("Collection " + collectname+ " selected successfully"); 
	      return(collection);
	}	
	
	public void ruleCode(String weaknessName) {
		
		MongoCollection<Document> collection = Collection("callGraph");
		/*
		 *  this will check all of the methods
		 *  in this case the count of methods = checks
		 */
		checksRun = collection.countDocuments();
		 /* 
		  * Getting the iterable object
		  * we are cheating here - I am asking database to look at lines of code (loc) 
		  */
		 
		BasicDBObject query = new BasicDBObject("loc", new BasicDBObject("$gt", 70));
		  
		FindIterable<Document> iterDoc = collection.find(query); 
			   
		  // Getting the iterator - the index
		Iterator it = iterDoc.iterator(); 
		
		/*
		 * change in design. We will only create ONE vulnerability and wipe it clean after each post
		 * less memory
		 */
		Vulnerability vulnerability = new Vulnerability(weaknessID);
		/*
		 * if the rule applies to the whole block (loc, complexity etc) then 
		 * invoke the special purpose one.		 * 
		 */
		
		while (it.hasNext()) {  
			 vulFound++;
			 /*
			  * To grab a field, create a basicobject (allData) and then pull 
			  */
			 BasicDBObject allDataObject = (BasicDBObject) it.next();
			 String allDataMethodValue = allDataObject.getString("nodename");
			 vulnerability.setMethod(allDataMethodValue);
			 /*
			  * since this method affects the whole method, might as well mark it
			  */
			vulnerability.initMethodBlock(weaknessID, allDataMethodValue);
			vulnerability.post();
			vulnerability.print();
		}	
		

		
		
	}
	
	public void run(String args[]) 
	{
		connectDB();
		ruleCode(this.weaknessName);
		closeAll();
	}
	
	public void closeAll() {
		this.performance.close(checksRun, vulFound);
		closeDB();  
			
	}
	
	

	

	public long getWeaknessID() {
		return weaknessID;
	}
	public void setWeaknessID(long weaknessID) {
		this.weaknessID = weaknessID;
	}
	public String getWeaknessName() {
		return weaknessName;
	}
	public void setWeaknessName(String weaknessName) {
		this.weaknessName = weaknessName;
	}
	public long getChecksRun() {
		return checksRun;
	}
	public void setChecksRun(long checksRun) {
		this.checksRun = checksRun;
	}
	public long getVulFound() {
		return vulFound;
	}
	public void setVulFound(long vulFound) {
		this.vulFound = vulFound;
	}
	public int getLinestart() {
		return linestart;
	}
	public void setLinestart(int linestart) {
		this.linestart = linestart;
	}
	public int getLineend() {
		return lineend;
	}
	public void setLineend(int lineend) {
		this.lineend = lineend;
	}
	public int getColstart() {
		return colstart;
	}
	public void setColstart(int colstart) {
		this.colstart = colstart;
	}
	public int getColend() {
		return colend;
	}
	public void setColend(int colend) {
		this.colend = colend;
	}
	     
	     
	     
	     

	
}
