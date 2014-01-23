package linkInterMessageDetector.ae;

import java.util.Set;

import linkInterMessageDetector.datamodel.LexicalChain;
import linkInterMessageDetector.res.LexicalChainsNetworkModel;
import linkInterMessageDetector.res.ThreadModel;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;

import common.util.MiscUtil;

import my.types.Message;

public class LinkerAE extends JCasAnnotator_ImplBase {

	public final static String RES_LC_KEY = "LC_Key";
	@ExternalResource(key = RES_LC_KEY)
	private LexicalChainsNetworkModel lexicalChainsNetwork;
	
	public final static String RES_Thread_KEY = "Thread_Key";
	@ExternalResource(key = RES_Thread_KEY)
	private ThreadModel threads;
	
	public final static String resultsFilename = "results.txt";
	public static StringBuilder sb = new StringBuilder();
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		Message message = JCasUtil.selectSingle(aJCas, Message.class);
		
		try {
			lexicalChainsNetwork = (LexicalChainsNetworkModel) getContext().getResourceObject(RES_LC_KEY);
		} catch (ResourceAccessException e) {
			e.printStackTrace();
			System.out.println("Erreur lc");
		}
		
		try {
			threads = (ThreadModel) getContext().getResourceObject(RES_Thread_KEY);
		} catch (ResourceAccessException e) {
			e.printStackTrace();
			System.out.println("Erreur thread");
		}
		
		if(message.getId() != null) {
			String id = message.getId().substring(1, message.getId().length()-1);
			
			Integer threadId = threads.getThreadId(id);
			if (threadId != null) {
				Set<String> threadMessages = threads.getMessagesFromThread(threadId);
				
				Double best = 0.0;
				String bestMessage = "";
				
				for (String otherMessage: threadMessages) {
					if (!otherMessage.equals(id)) {
						Double sim = 0.0;
						Set<LexicalChain> messageChains = lexicalChainsNetwork.getMessageChains(id);
						Set<LexicalChain> otherChains = lexicalChainsNetwork.getMessageChains(otherMessage);
						if (otherChains.isEmpty())
							continue;
						Set<LexicalChain> chains1 = messageChains;
						Set<LexicalChain> chains2 = otherChains;;
						if(chains1.size() < chains2.size()) {
							chains1 = otherChains;
							chains2 = messageChains;
						}
						for (LexicalChain lc1 : chains1) {
							for (LexicalChain lc2 : messageChains) {
								sim += lc1.compare(lc2);
							}
						}
						sim /= chains2.size();
						if (sim > best) {
							best = sim;
							bestMessage = otherMessage;
						}
					}
				}
				
				
				if (bestMessage != "") {
					sb.append(bestMessage).append(":").append(id).append("\n");
				}
			}
		}
	}
	
	@Override
	public void collectionProcessComplete() throws AnalysisEngineProcessException {
		MiscUtil.writeToFS(sb.toString(), resultsFilename);
	}

}
