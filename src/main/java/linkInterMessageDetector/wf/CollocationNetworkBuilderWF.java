/**
 * 
 */
package linkInterMessageDetector.wf;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.factory.ExternalResourceFactory.createExternalResourceDescription;

import java.io.File;

import linkInterMessageDetector.ae.CollocationNetworkBuilderAE;
import linkInterMessageDetector.ae.WordSegmenterAE;
import linkInterMessageDetector.ae.JCasFSWriterAE;

import linkInterMessageDetector.cr.ZimReaderCR;
import linkInterMessageDetector.res.CollocationNetworkModel_Impl;
import linkInterMessageDetector.res.StopWordModel_Impl;
import linkInterMessageDetector.res.WordCounterModel_Impl;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ExternalResourceDescription;

import linkInterMessageDetector.ae.CollocationNetworkBuilderAnnotator;
import linkInterMessageDetector.ae.CollocationNetworkBuilderConsumerAnnotator;

/**
 * Illustrate how to configure 
 * and run annotators with the shared model object.
 */
public class CollocationNetworkBuilderWF {

	public static void main(String[] args) throws Exception {
		
		// Creation of the external resource description
		ExternalResourceDescription stopWordsResourceDesc = createExternalResourceDescription(StopWordModel_Impl.class,
				 "file:resourceManagement/data/stopwords-fr.txt");
		
	
		ExternalResourceDescription collocationNetworkResourceDesc = createExternalResourceDescription(CollocationNetworkModel_Impl.class,
				"file:resourceManagement/data/collocationNetwork.csv");
		
		
		// Binding external resource to each Annotator individually
		AnalysisEngineDescription aed0 = createEngineDescription(WordSegmenterAE.class, WordSegmenterAE.RES_KEY, 
				stopWordsResourceDesc);
		
		AnalysisEngineDescription aed3 = createEngineDescription(CollocationNetworkBuilderAnnotator.class, CollocationNetworkBuilderAnnotator.RES_KEY,
				collocationNetworkResourceDesc);
		
		AnalysisEngineDescription aed4 = createEngineDescription(CollocationNetworkBuilderConsumerAnnotator.class,CollocationNetworkBuilderConsumerAnnotator.RES_KEY,
				collocationNetworkResourceDesc);
		
		/*AnalysisEngineDescription aed4 = createEngineDescription(JCasFSWriterAE.class,
				JCasFSWriterAE.PARAM_DESTDIRNAME, 
				"/tmp/doc.txt",
				JCasFSWriterAE.PARAM_DESTFILEEXTENSION,
				".txt");
		*/
		
		
		
		// Check the external resource was injected
		AnalysisEngineDescription aaed = createEngineDescription(aed0, aed3, aed4);
		AnalysisEngine ae = createEngine(aaed);
		
		
		// Creation of the collection reader description 
		// ZimReaderCR read 7864 articles of ubuntudoc_fr_01_2009.zim but only 4124 html with 4076 non null
		CollectionReaderDescription crd = createReaderDescription(ZimReaderCR.class,
				ZimReaderCR.PARAM_ZIM_SRCPATH, "/home/agathe/ATAL/nlp-software-development/data/ubuntu-fr/doc.zim/ubuntudoc_fr_01_2009.zim", 
				ZimReaderCR.PARAM_LANGUAGE, "fr",
				ZimReaderCR.PARAM_ENCODING, "utf-8");

		
		// Run the pipeline
		SimplePipeline.runPipeline(crd,aaed);
	}
    
    
}
