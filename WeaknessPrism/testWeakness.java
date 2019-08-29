package WeaknessPrism;

import java.util.Iterator;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class testWeakness extends WeaknessTemplate {
	
	@Override
	public void ruleCode() {
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
		 
		BasicDBObject query = new BasicDBObject("aep", new BasicDBObject("$gt", 500));
		  
		FindIterable<Document> iterDoc = collection.find(query); 
			   
		  // Getting the iterator - the index
		Iterator it = iterDoc.iterator(); 
		
		/*
		 * change in design. We will only create ONE vulnerability and wipe it clean after each post
		 * less memory
		 */
		Vulnerability vulnerability = new Vulnerability(weaknessID);
		/*
		 * int DATA
		 * if the rule applies to the whole block (loc, complexity etc) then 
		 * invoke the special purpose one.		 * 
		 */
		 BasicDBObject allDataObject;
		while (it.hasNext()) {  
			 vulFound++;
			 /*
			  * int allDataObject = 0;
			  * To grab a field, create a basicobject (allData) and then pull 
			  */
			 allDataObject = (BasicDBObject) it.next();
			 String allDataMethodValue = allDataObject.getString("nodename");
			 String thisisAEP = allDataObject.getString("AEP");
			 vulnerability.setMethod(allDataMethodValue);
			 /*
			  * since this method affects the whole method, might as well mark it
			  */
			vulnerability.initMethodBlock(weaknessID, allDataMethodValue);
			vulnerability.post();
			vulnerability.print();
		}	
	
		
	}

}
