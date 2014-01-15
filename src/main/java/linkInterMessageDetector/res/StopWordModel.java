/**
 * 
 */
package linkInterMessageDetector.res;

/**
 * Interface which models the stop word list ; 
 * Declare the methods handled by the annotators to access the resource 
 */
public interface StopWordModel {
	/** Test if a word is present in the list */
	public Boolean contains (String key);
}