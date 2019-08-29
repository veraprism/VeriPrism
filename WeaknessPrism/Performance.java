package WeaknessPrism;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;



public class Performance {

/**
* @param weaknessId
* @param startTime
* @param endTime
* @param checksRun
* @param vulnerabilityFound
*/
public Performance(long weaknessId) {
	super();
	this.weaknessId = weaknessId;
	this.startTime = System.currentTimeMillis();
	this.filename = "VPR-PRF-" + String.valueOf(this.weaknessId) + "-" + String.valueOf(System.currentTimeMillis());
}  

public void reInit() {
	this.startTime = System.currentTimeMillis();
}


public void close(long checksRun, long vulnerabilityFound) {
	this.endTime = System.currentTimeMillis();
	this.checksRun = checksRun;
	this.vulnerabilityFound = vulnerabilityFound;
	this.filename = "VPR-PRF-" + String.valueOf(this.weaknessId) + "-" + String.valueOf(System.currentTimeMillis());
	post();

}    

private long weaknessId;
private long startTime;
private long endTime;
private long elapseTime;
private long checksRun;
private long vulnerabilityFound;
private String filename;
private String[] issues;

/**
* @param args
*/
public static void main(String[] args) {
// TODO Auto-generated method stub

}

/**
* @return the weaknessId
*/
public long getWeaknessId() {
return weaknessId;
}

/**
* @param weaknessId the weaknessId to set
*/
public void setWeaknessId(long weaknessId) {
this.weaknessId = weaknessId;
}

/**
* @return the startTime
*/
public long getStartTime() {
return startTime;
}

/**
* @param startTime the startTime to set
*/
public void setStartTime(long startTime) {
this.startTime = startTime;
}

/**
* @return the endTime
*/
public long getEndTime() {
return endTime;
}

/**
* @param endTime the endTime to set
*/
public void setEndTime(long endTime) {
this.endTime = endTime;
}

/**
* @return the checksRun
*/
public long getChecksRun() {
return checksRun;
}

/**
* @param checksRun the checksRun to set
*/
public void setChecksRun(long checksRun) {
this.checksRun = checksRun;
}

/**
* @return the vulnerabilityFound
*/
public long getVulnerabilityFound() {
return vulnerabilityFound;
}

/**
* @param vulnerabilityFound the vulnerabilityFound to set
*/
public void setVulnerabilityFound(long vulnerabilityFound) {
this.vulnerabilityFound = vulnerabilityFound;
}

/**
* @return the issues
*/
public String[] getIssues() {
return issues;
}

/**
* @param issues add to the issues set
*/
public void setissues(String newissue) {
	int issuelength = issues.length;
	this.issues[issuelength] = newissue;
}


public void post() {
	this.endTime = System.currentTimeMillis();
	this.elapseTime = endTime - startTime;
	JSONObject perfout = new JSONObject();
	perfout.put("WeaknessID", this.weaknessId);
	perfout.put("startTime", this.startTime);
	perfout.put("endTime",this.endTime);
	perfout.put("elapseTime", this.elapseTime);
	perfout.put("checksRun",this.checksRun);
	perfout.put("vulnerabilityFound",this.vulnerabilityFound);
	try (FileWriter file = new FileWriter(this.filename)) {
	    file.write(perfout.toString());
	   } 
	catch (IOException e) {
	      e.printStackTrace();
	   }
	
	       System.out.print(perfout);
	
	}
public void print() {
System.out.println("Weakness ID " + this.weaknessId + " took " + this.elapseTime + " ms");

}
/**
* @return the filename
*/
public String getFilename() {
return filename;
}
/**
* @param filename the filename to set
*/
public void setFilename(String filename) {
this.filename = filename;
}

}