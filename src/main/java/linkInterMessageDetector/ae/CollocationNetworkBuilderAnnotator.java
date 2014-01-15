package linkInterMessageDetector.ae;

import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import common.types.Token;

import linkInterMessageDetector.res.CollocationNetworkModel;

public class CollocationNetworkBuilderAnnotator extends JCasAnnotator_ImplBase {

	public final static int WINDOWSIZE = 3;
	public final static String RES_KEY = "aKey";

	@ExternalResource(key = RES_KEY)
	private CollocationNetworkModel collocationNetwork;
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		FSIterator<Annotation> tokenIterator =  aJCas.getAnnotationIndex(Token.type).iterator();
		while(tokenIterator.isValid()) {
			Token currentToken = (Token) tokenIterator.get();
			System.out.println(currentToken.getCoveredText());
			int cpt = 0;
			for (int i=0; i<WINDOWSIZE; i++) {
				tokenIterator.moveToNext();
				if (tokenIterator.isValid()) {
					cpt++;
					Token followingToken = (Token) tokenIterator.get();
					collocationNetwork.add_occurence(currentToken.getCoveredText(), followingToken.getCoveredText());
				}
			}
			while (cpt != 1) {
				cpt--;
				tokenIterator.moveToPrevious();	
			}
		}
		
		//collocationNetwork.echo();
	}

}
