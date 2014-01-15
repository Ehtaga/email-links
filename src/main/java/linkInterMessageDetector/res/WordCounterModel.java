/**
 * 
 */
package linkInterMessageDetector.res;

import java.util.Set;

/**
 * Interface which models the Word Counters ; 
 * Declare the methods handled by the annotators to access the resource 
 */
public interface WordCounterModel {

	/** Get the number of occurrences of a given word*/
	public Integer get(String key);

	/** Increment the occurrence counter of a given word*/
	public  void inc (String key);

	/** Decrement the occurrence counter of a given word*/
	public void dec (String key);

	/** Get the number of word counter */
	public Integer size();

	/** Get a set of all the counted words */
	public Set<String> keySet();
	
	/** Get the sum of all word counters DONE */
	public Integer getTotalCounter();

	/** Display the content of the current word counter */
	public void echo();

	/**
	 * Save the content of the resource as a CSV file
	 * a line per word, word and counter as columns with tab character as separator
	 * use the MiscUtil.writeToFS(textString,filenameString) DONE */
	public void save(String filename);

}