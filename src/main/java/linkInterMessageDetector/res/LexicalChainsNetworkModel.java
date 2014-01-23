package linkInterMessageDetector.res;

import java.util.Set;

import linkInterMessageDetector.datamodel.LexicalChain;

public interface LexicalChainsNetworkModel {
	
	public void add_chain(String messageId, LexicalChain lc);
	
	public Set<LexicalChain> getMessageChains(String messageId);
	
	public void save(String filename);
}
