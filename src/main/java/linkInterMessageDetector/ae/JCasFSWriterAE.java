/**
 * 
 */
package linkInterMessageDetector.ae;



import java.io.File;
import java.io.IOException;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;

import common.util.MiscUtil;



/**
 * Annotator that export the jcas text content to the file system
 */
public class JCasFSWriterAE extends JCasAnnotator_ImplBase {


	public static final String PARAM_DESTDIRNAME = "destDirName";
	@ConfigurationParameter(name = PARAM_DESTDIRNAME, mandatory = false, defaultValue="/tmp")
	private String destDirName;

	public static final String PARAM_DESTFILEEXTENSION = "destFileExtension";
	@ConfigurationParameter(name = PARAM_DESTFILEEXTENSION, mandatory = false, defaultValue="")
	private String destFileExtension;

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		String filename =  ((SourceDocumentInformation) aJCas.getAnnotationIndex(SourceDocumentInformation.type).iterator().get()).getUri(); //aJCas.getAnnotationIndex(SourceDocumentInformation.type).iterator().get().getFeatureValueAsString(aJCas.getTypeSystem().getFeatureByFullName("uri"));
		//if (filename.lastIndexOf("/") != -1) 
		//	filename= filename.substring(filename.lastIndexOf("/"));
		
		if ((filename == null) || filename.trim().equalsIgnoreCase(""))
			try {
				filename = File.createTempFile("tempfile", ".tmp").getPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.err.printf("Debug: %s\n",filename);
		MiscUtil.writeToFS(aJCas.getDocumentText(), destDirName+"/"+filename+destFileExtension);
	}

}
