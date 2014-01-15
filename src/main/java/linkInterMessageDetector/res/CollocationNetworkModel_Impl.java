package linkInterMessageDetector.res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

import common.util.MiscUtil;

public final class CollocationNetworkModel_Impl implements CollocationNetworkModel, SharedResourceObject {
	private Map<String,Map<String,Double>> collocationNetworkMap;
	
	private synchronized  Map<String, Map<String,Double>> getCollocationNetwork() {
		return this.collocationNetworkMap;
	}
	
	public synchronized Map<String,Double> getWord1Map(String word1) {
		if (!getCollocationNetwork().containsKey(word1)) {
			Map<String,Double> word1Map = new HashMap<String,Double>();
			getCollocationNetwork().put(word1, word1Map);
		}
		return getCollocationNetwork().get(word1);
	}
	
	public synchronized Double getCollocationScore(String word1, String word2) {
		if (!getCollocationNetwork().containsKey(word1)) {
			Map<String,Double> word1Map = new HashMap<String,Double>();
			getCollocationNetwork().put(word1, word1Map);
		}
		if (!getWord1Map(word1).containsKey(word2)) {
			getWord1Map(word1).put(word2,0.0);
		}
		return getCollocationNetwork().get(word1).get(word2);
	}

	public synchronized void add_occurence(String word1, String word2) {
		getWord1Map(word1).put(word2,getCollocationScore(word1,word2)+1.0);
	}
	
	public synchronized Set<String> keySet() {
		return getCollocationNetwork().keySet();
	}

	public void load(DataResource aData) throws ResourceInitializationException {
		System.out.println(getClass().getSimpleName()+": load +1");
		System.out.println(aData);
		if (collocationNetworkMap == null) {
			collocationNetworkMap = new HashMap<String,Map<String,Double>>();
			InputStream inStr = null;
			try {
				// open input stream to data
				inStr = aData.getInputStream();
				// read each line
				BufferedReader reader = new BufferedReader(new InputStreamReader(inStr));
				String line;
				while ((line = reader.readLine()) != null) {
					if(line.length() > 0) {
						String word1 = line.split("\t")[0];
						String word2 = line.split("\t")[1];
						Double score = Double.parseDouble(line.split("\t")[2]);
						if (!getCollocationNetwork().containsKey(word1)) {
							Map<String,Double> word1Map = new HashMap<String,Double>();
							getCollocationNetwork().put(word1, word1Map);
						}
						if (!getWord1Map(word1).containsKey(word2)) {
							getWord1Map(word1).put(word2,score);
						}
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
		}
	}

	public void echo() {
		for (String word1 : keySet()) {
			Map<String,Double> collocations = getWord1Map(word1);
			for (String word2 : collocations.keySet()) {
				System.out.printf("(%s,%s) = %f\n",word1,word2,getCollocationScore(word1,word2));
			}
		}	
	}
	
	public synchronized void save(String filename) {
		String s = "";
		for (String word1 : keySet()) {
			Map<String,Double> collocations = getWord1Map(word1);
			for (String word2 : collocations.keySet()) {
				s += word1+"\t"+word2+"\t"+getCollocationScore(word1,word2)+"\n";
			}
		}
		MiscUtil.writeToFS(s,filename);
	}

	
}
