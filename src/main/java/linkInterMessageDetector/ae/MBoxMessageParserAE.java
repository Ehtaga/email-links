/**
 * 
 */
package linkInterMessageDetector.ae;


import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.component.ViewCreatorAnnotator;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import org.apache.uima.jcas.JCas;

import factory.parser.MBoxParser;

/**
 * Annotator that parse the content of a JCas assuming it is an MBox message
 */
public class MBoxMessageParserAE extends JCasAnnotator_ImplBase {




	public static final String PARAM_DEST_DIR = "DestDir";
	@ConfigurationParameter(name = PARAM_DEST_DIR, mandatory = false, defaultValue="/tmp")
	private String destDir;


	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {

		MBoxParser mboxParser = new MBoxParser();
		com.auxilii.msgparser.Message message = null;
		
		JCas bodyCas = ViewCreatorAnnotator.createViewSafely(aJCas,"bodyCas");
		
		try {
			message  = mboxParser.parse(aJCas.getDocumentText());
		} catch (Exception e) {
			e.printStackTrace();
		}

		bodyCas.setDocumentText(message.getBodyText());
		my.types.Message body = new my.types.Message(bodyCas,0,message.getBodyText().length());
		body.setBody(message.getBodyText());
		body.setId(message.getMessageId());
		body.addToIndexes(bodyCas);
		
	}
}
