/**
 * 
 */
package linkInterMessageDetector.ae;


import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import org.apache.uima.jcas.JCas;

import com.auxilii.msgparser.Message;
import com.auxilii.msgparser.RecipientEntry;

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
		// Prints the instance ID to the console - this proves the same instance
		// of the SharedModel is used in both Annotator instances.
		//System.out.println(getClass().getSimpleName() + ": " + wordCounter);


		MBoxParser mboxParser = new MBoxParser();
		Message message = null;
		try {
			message  = mboxParser.parse(aJCas.getDocumentText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(messageSummary(message));
		

	}


	private static String messageSummary(Message message) {
		String summary;
		summary = "Subject "+message.getSubject() +"\n";
		summary += "Date "+message.getDate()+"\n";
		summary += "MessageId "+message.getMessageId()+"\n";
		summary += "From name "+message.getFromName()+ " email "+message.getFromEmail()+"\n";
		summary += "To name "+message.getToName()+ " email "+message.getToEmail()+"\n";
		summary += "Recipients ";
		for (RecipientEntry r : message.getRecipients()) {
			summary += r.getToName()+ " " +r.getToEmail()+", ";
		}
		summary += "\n";
		summary += "DisplayTo "+message.getDisplayTo()+"\n";
		summary += "DisplayCc "+message.getDisplayCc()+"\n";
		summary += "DisplayBcc "+message.getDisplayBcc()+"\n";


		for (String p : message.getProperties()) {
			summary += "Property ";
			summary += p+":"+message.getProperty(p) +"\n";
		}
		summary += "\n";
		return summary;
	}

	private static String lightMessageSummary(Message msg) {
		String summary;
		summary = "Subject "+msg.getSubject() +"\n";
		summary += "MessageId "+msg.getMessageId()+"\n";
		summary += "From name "+msg.getFromName()+ " email "+msg.getFromEmail()+"\n";
		summary += "To name "+msg.getToName()+ " email "+msg.getToEmail()+"\n";

		for (String p : msg.getProperties()) {
			summary += "Property ";
			summary += p+":"+msg.getProperty(p) +"\n";
		}
		summary += "\n";
		return summary;
	}
}
