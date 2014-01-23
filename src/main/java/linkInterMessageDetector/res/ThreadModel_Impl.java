package linkInterMessageDetector.res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

public class ThreadModel_Impl implements ThreadModel, SharedResourceObject {

	private Map<String, Integer> messages = new HashMap<String, Integer>();
	private Map<Integer, Set<String>> threads = new HashMap<Integer, Set<String>>();
	
	@Override
	public void load(DataResource aData) throws ResourceInitializationException {
		System.out.println(getClass().getSimpleName()+": Start loading threads resource");
		System.out.println(aData.getUri());
		
		InputStream inStr = null;
		try {
			// open input stream to data
			inStr = aData.getInputStream();
			// read each line
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStr));
			String line;
			int thread_id = 1;
			while ((line = reader.readLine()) != null) {
				if(line.startsWith("#"))
					thread_id++;
				else {
					messages.put(line.trim(),thread_id);
					if(!threads.containsKey(thread_id)) {
						threads.put(thread_id, new HashSet<String>());
					}
					threads.get(thread_id).add(line.trim());
				}
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
		System.out.println(messages.size());
		System.out.println(threads.size());
		System.out.println(getThreadId("1246376859.30087.2.camel@serge-ubuntu"));
		System.out.println(getMessagesFromThread(3));
		System.out.println(getClass().getSimpleName()+": End loading threads resource");
	}
	
	public Integer getThreadId(String messageId) {
		return messages.get(messageId);
	}

	@Override
	public Set<String> getMessagesFromThread(Integer id) {
		return threads.get(id);
	}

}
