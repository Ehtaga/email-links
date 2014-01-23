package linkInterMessageDetector.ae;

import java.io.IOException;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.jcas.JCas;

import linkInterMessageDetector.res.CollocationNetworkModel;

public class CollocationNetworkBuilderConsumerAE extends
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
		System.out.println("Start collectionProcessComplete CNBuilderConsumer");
		try {
			collocationNetwork.save(resourceDestFilename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("End collectionProcessComplete CNBuilderConsumer");
	}

}
