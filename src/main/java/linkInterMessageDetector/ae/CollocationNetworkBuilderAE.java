package linkInterMessageDetector.ae;

import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.jcas.JCas;

import common.types.Token;

import linkInterMessageDetector.res.CollocationNetworkModel;

public class CollocationNetworkBuilderAE extends JCasAnnotator_ImplBase {

	public final static int WINDOWSIZE = 3;
	public final static String RES_KEY = "aKey";

	@ExternalResource(key = RES_KEY)
	private CollocationNetworkModel collocationNetwork;
	
	@Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
		String tok_minus_1 = null;
		String tok_minus_2 = null;
		String tok_minus_3 = null;
		for (Token currentToken : select(aJCas, Token.class)) {
			String tok = currentToken.getCoveredText().toLowerCase();
			if (tok_minus_1 != null) {
				collocationNetwork.add_occurence(tok, tok_minus_1);
				collocationNetwork.add_occurence(tok_minus_1, tok);
			}
			if (tok_minus_2 != null) {
				collocationNetwork.add_occurence(tok, tok_minus_2);
				collocationNetwork.add_occurence(tok_minus_2, tok);
			}
			if (tok_minus_3 != null) {
				collocationNetwork.add_occurence(tok, tok_minus_3);
				collocationNetwork.add_occurence(tok_minus_3, tok);
			}
			tok_minus_3 = tok_minus_2;
			tok_minus_2 = tok_minus_1;
			tok_minus_1 = tok;
		}
    }
}
