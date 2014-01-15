/**
 * 
 */
package linkInterMessageDetector.ae;

import linkInterMessageDetector.res.CollocationNetworkModel;
import linkInterMessageDetector.res.WordCounterModel;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import common.types.Token;


/**
 * Annotator that builds collocation 
 * 
 */
public class CollocationNetworkBuilderAE extends JCasAnnotator_ImplBase {

	public final static int WINDOWSIZE = 3;
	
	public final static String RES_KEY = "aKey";
	@ExternalResource(key = RES_KEY)
	private CollocationNetworkModel collocationNetwork;

	
	public static final String PARAM_RESOURCE_DEST_FILE = "resourceDestFilename";
	@ConfigurationParameter(name = PARAM_RESOURCE_DEST_FILE, mandatory = true, defaultValue="/tmp/collocationNetwork.csv")
	private String resourceDestFilename;
	
	// Size 
	public static final String PARAM_WINDOW_SIZE = "windowSize";
	@ConfigurationParameter(name = PARAM_WINDOW_SIZE, mandatory = false, defaultValue="3")
	private Integer windowSize;


	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		// Prints the instance ID to the console - this proves the same instance
		// of the SharedModel is used in both Annotator instances.
		//System.out.println(getClass().getSimpleName() + ": " + collocationNetwork);

		FSIterator<Annotation> tokenIterator =  aJCas.getAnnotationIndex(Token.type).iterator();
		while(tokenIterator.isValid()) {
			Token currentToken = (Token) tokenIterator.get();
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
	}
	
	@Override
	public void collectionProcessComplete()  throws AnalysisEngineProcessException {
		// Prints the instance ID to the console - this proves the same instance
		// of the SharedModel is used in both Annotator instances.
		System.out.println(getClass().getSimpleName() + ": " + collocationNetwork);
		collocationNetwork.echo(); 
		collocationNetwork.save(resourceDestFilename);
	}
}
