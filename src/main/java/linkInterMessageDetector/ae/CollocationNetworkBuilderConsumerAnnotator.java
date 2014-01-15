package linkInterMessageDetector.ae;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.jcas.JCas;

import linkInterMessageDetector.res.CollocationNetworkModel;

public class CollocationNetworkBuilderConsumerAnnotator extends
		JCasAnnotator_ImplBase {
	
	public final static String RES_KEY = "anotherKeyOrNot";
	@ExternalResource(key = RES_KEY)
	private CollocationNetworkModel collocationNetwork;
	
	public final static String resourceDestFilename = "collocationNetwork.csv";
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void collectionProcessComplete()  throws AnalysisEngineProcessException {
		// Prints the instance ID to the console - this proves the same instance
		// of the SharedModel is used in both Annotator instances.
		System.out.println(getClass().getSimpleName() + ": " + collocationNetwork);
		collocationNetwork.echo(); 
		collocationNetwork.save(resourceDestFilename); // with resourceDestFilename to define...

	}

}
