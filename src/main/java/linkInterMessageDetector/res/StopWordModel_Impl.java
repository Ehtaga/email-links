/**
 * 
 */
package linkInterMessageDetector.res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

/**
 * Implementation of the model of stop word list
 * No resource actually loaded 
 */
public final class StopWordModel_Impl implements StopWordModel, SharedResourceObject {
	private Set<String> stopWordSet;

	private synchronized Set<String> getStopWords () {
		return stopWordSet;
	}
	
	private synchronized void add (String key) {
		getStopWords().add(key);
	}
	
	public Boolean contains(String key) {
		return getStopWords().contains(key);
	}
	
	/***/
	public synchronized void load(DataResource aData) throws ResourceInitializationException {
		System.out.println(getClass().getSimpleName()+": Start loading stopwords resource");
		System.out.println(aData.getUri());
		if (stopWordSet == null) {		
			stopWordSet = new HashSet<String>();
			InputStream inStr = null;
			try {
				// open input stream to data
				inStr = aData.getInputStream();
				// read each line
				BufferedReader reader = new BufferedReader(new InputStreamReader(inStr));
				String line;
				while ((line = reader.readLine()) != null) {
					//System.out.println(line);
					if (! line.startsWith("#"))
					add(line.trim());
				}
			} catch (IOException e) {
				throw new ResourceInitializationException(e);
			} finally {
				if (inStr != null) {
					try {
						inStr.close();
					} catch (IOException e) {
					}
				}
			}

		}
		System.out.println(getClass().getSimpleName()+": End loading stopwords resource");
	}
}