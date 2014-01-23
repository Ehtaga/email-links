package linkInterMessageDetector.res;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

import common.util.MiscUtil;

import linkInterMessageDetector.datamodel.LexicalChain;

public final class LexicalChainsNetworkModel_Impl implements
		LexicalChainsNetworkModel, SharedResourceObject {
	private Map<String,Set<LexicalChain>> chains = new HashMap<String,Set<LexicalChain>>();

	public void add_chain(String messageId, LexicalChain lc) {
		if (!chains.containsKey(messageId))
			chains.put(messageId, new HashSet<LexicalChain>());
		chains.get(messageId).add(lc);
	}

	@Override
	public synchronized void save(String filename) {
		MiscUtil.writeToFS(toString(),filename);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String messageId : chains.keySet()) {
			sb.append(">>> "+messageId+"\n");
			for (LexicalChain chain : chains.get(messageId)) {
				for (String word : chain.getLexicalChain()) {
					sb.append(word).append(" ");
				}
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	@Override
	public void load(DataResource aData) throws ResourceInitializationException {
		
		System.out.println(getClass().getSimpleName()+": Start loading LC resource");
		System.out.println(aData.getUri());
		
		chains = new HashMap<String,Set<LexicalChain>>();
		
//		
//		InputStream inStr = null;
//		try {
//			// open input stream to data
//			inStr = aData.getInputStream();
//			// read each line
//			BufferedReader reader = new BufferedReader(new InputStreamReader(inStr));
//			String line;
//			String messageId = "";
//			while ((line = reader.readLine()) != null) {
//				if(line.startsWith("Message-ID:")) {
//					messageId = line.replace("Message-ID: ", "");
//					chains.put(messageId, new HashSet<LexicalChain>());
//				}
//				else {
//					LexicalChain lc = new LexicalChain(new HashSet<>(Arrays.asList(line.split(" "))));
//					chains.get(messageId).add(lc);
//				}
//					
//			}
//		} catch (IOException e) {
//			throw new ResourceInitializationException(e);
//		} finally {
//			if (inStr != null) {
//				try {
//					inStr.close();
//				} catch (IOException e) {
//				}
//			}
//		}
		
		System.out.println(getClass().getSimpleName()+": End loading LC resource");
	}

	@Override
	public Set<LexicalChain> getMessageChains(String messageId) {
		if (chains.containsKey(messageId))
			return chains.get(messageId);
		else
			return new HashSet<LexicalChain>();
	}

}
