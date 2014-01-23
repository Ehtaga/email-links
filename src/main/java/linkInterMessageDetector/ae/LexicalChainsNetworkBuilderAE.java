package linkInterMessageDetector.ae;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import linkInterMessageDetector.res.CollocationNetworkModel;
import linkInterMessageDetector.res.LexicalChainsNetworkModel;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;

import common.types.Token;

import linkInterMessageDetector.datamodel.LexicalChain;
import my.types.Message;

public class LexicalChainsNetworkBuilderAE extends JCasAnnotator_ImplBase {

	public final static String RES_LC_KEY = "LC_Key";
	@ExternalResource(key = RES_LC_KEY)
	private LexicalChainsNetworkModel lexicalChainsNetwork;
	
	public final static String RES_CN_KEY = "CN_Key";
	@ExternalResource(key = RES_CN_KEY)
	private CollocationNetworkModel collocationNetwork;
	
	public final static String resourceDestFilename = "lexicalChains.txt";
	
	public final static double THRESHOLD = 5.0;
	
	@Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {

		try {
			collocationNetwork = (CollocationNetworkModel) getContext().getResourceObject(RES_CN_KEY);

		} catch (ResourceAccessException e) {

			e.printStackTrace();
			System.out.println("Erreur cn");
		}
		try {
			lexicalChainsNetwork = (LexicalChainsNetworkModel) getContext().getResourceObject(RES_LC_KEY);
		} catch (ResourceAccessException e) {
			e.printStackTrace();
			System.out.println("Erreur lc");
		}

		
		Message message = JCasUtil.selectSingle(aJCas, Message.class);
		if (message.getId() != null)
		{
			//System.out.println(message.toString());
			//System.out.println(message.getId());
			String id = message.getId().substring(1, message.getId().length()-1);
			//System.out.println(message.getId());
			Map<LexicalChain,Integer> chains = new HashMap<LexicalChain,Integer>();
			for (Token currentToken : JCasUtil.selectCovered(Token.class, message)) {
				String currentWord = currentToken.getCoveredText().toLowerCase();
				//System.out.println(currentWord);
				Double max_score = 0.0;
				//Set<LexicalChain> max_chain = new HashSet<LexicalChain>();
				LexicalChain max_chain = new LexicalChain();
				Set<LexicalChain> oldChains = new HashSet<LexicalChain>();
				for (LexicalChain lc : chains.keySet()) {
					chains.put(lc,chains.get(lc)+1);
					if(chains.get(lc) >= 20) {
						oldChains.add(lc);
					}
					//System.out.println("LC: "+lc.toString());
					Double average = 0.0;
					for (String chainWord : lc.getLexicalChain()) {
						average += collocationNetwork.getCollocationScore(currentWord, chainWord);	
					}
					average /= lc.size();
					if (average > max_score) {
						max_score = average;

						max_chain = lc;
					}
					else if (average == max_score) {
						System.out.println("Chaines à merger");
					}
				}
				if (chains.isEmpty() || max_score < THRESHOLD) {
					LexicalChain lc = new LexicalChain();
					lc.addItem(currentWord);
					chains.put(lc,0);
				}
				else {
					max_chain.addItem(currentWord);
					chains.put(max_chain,0);
					if (oldChains.contains(max_chain)) {
						oldChains.remove(max_chain);
					}
				}
				for (LexicalChain old : oldChains) {
					chains.remove(old);
					lexicalChainsNetwork.add_chain(id, old);
				}
			}
			for (LexicalChain lc : chains.keySet()) {
				lexicalChainsNetwork.add_chain(id, lc);
			}		
		}
	}
	
	@Override
	public void collectionProcessComplete()  throws AnalysisEngineProcessException {
		System.out.println("Start collectionProcessComplete LCBuilder");
		lexicalChainsNetwork.save(resourceDestFilename);
		System.out.println("End collectionProcessComplete LCBuilder");
	}
}
