package linkInterMessageDetector.res;

import java.util.Map;
import java.util.Set;

public interface CollocationNetworkModel {
	
	public Map<String,Double> getWord1Map(String word1);
	
	public Double getCollocationScore(String word1, String word2);
	
	public void add_occurence(String word1, String word2);
	
	public Set<String> keySet();
	
	public void echo();
	
	public void save(String filename);
}
