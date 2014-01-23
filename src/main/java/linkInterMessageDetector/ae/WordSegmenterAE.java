/**
 * 
 */
package linkInterMessageDetector.ae;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import linkInterMessageDetector.res.StopWordModel;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.jcas.JCas;

import common.types.Token;


/**
 * Annotator that segments the text into words and filter the one present 
 * in a stop word set
 */
public class WordSegmenterAE extends JCasAnnotator_ImplBase {
	final static String WORD_SEPARATOR_PATTERN = "[^\\s\\p{Punct}]+"; //"[^\\s\\.:,'\\(\\)!]+";

	// TODO add http://www.w3.org/TR/html-markup/elements.html
	public final static String RES_KEY = "aKey";
	@ExternalResource(key = RES_KEY)
	private StopWordModel stopWords;
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		// Prints the instance ID to the console - this proves the same instance
		// of the SharedModel is used in both Annotator instances.
		//System.out.println(getClass().getSimpleName()+": Start process WordSegmenter");
		//System.out.println(getClass().getSimpleName() + ": " + stopWords);
		
		Pattern wordSeparatorPattern = Pattern.compile(WORD_SEPARATOR_PATTERN);
		Matcher matcher = wordSeparatorPattern.matcher(aJCas.getDocumentText());
		while (matcher.find()) {
			String match = matcher.group().toLowerCase();
			if (!stopWords.contains(match) && match.length() > 1 && !match.matches(".*[0-9].*"))
				new Token(aJCas, matcher.start(), matcher.end()).addToIndexes(); 
		}
		//System.out.println(getClass().getSimpleName()+": End process WordSegmenter");
	}
}
