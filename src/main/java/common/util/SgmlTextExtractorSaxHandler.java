package common.util;
/** 
 * Copyright (C) 2010-*  Nicolas Hernandez
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * XML Sax handler to extract the text content of XML files
 *   
 * @author hernandez
 *
 */
public  class SgmlTextExtractorSaxHandler extends DefaultHandler {

	/**  Detagged text */
	private StringBuffer detaggedText = new StringBuffer();

	/** Tags whose content should not be kept  */
	private Set<String> notContentTagSet;


	/** in order not to consider content of some tags whatever the embedding level */
	private int notContentTagDepth = 0;

	/** insert whitespace character after extracted content when the last char is not an end of line */
	private Boolean insertWSCharAfterLastChar = false;
	
	/** in order to detect empty elements in endElement method, we check the characterFired variable 
	 empty element should not be considered as currentOpenElementWithTheBeginToSet*/
	private Boolean characterFired = false;

	/**
	 * Nombre d'éléments dont il faut spécifier le begin
	 */
	private int currentOpenElementWithTheBeginToSet = -1;
	/**
	 * Spécifie si l'on tente de positionner le texte relativement aux positions 
	 * des balises dans le XMLDoc
	 * Le problème est que lorsque character retourne un saut de ligne le start 
	 * du character rapporté est initialisé à 0 et l'on perd la position 
	 * relative au text source (le XMLDoc)
	 * Le true est donc à proscrire !!!
	 */
	private Boolean setPlainTextCursorsRelativelyToXmlTagCursors = false;


	/**
	 * 
	 * @param notContentTagSet
	 * @param insertWSCharAfterLastChar TODO
	 * @param aJCas
	 */
	public SgmlTextExtractorSaxHandler(Set<String> notContentTagSet, Boolean insertWSCharAfterLastChar) {
		setNotContentTagSet(notContentTagSet);
		setInsertWSCharAfterLastChar(insertWSCharAfterLastChar);
	}

	/**
	 * Start the parsing of the document
	 */
	public void startDocument() throws SAXException {

	}


	/**
	 * Start Element
	 * localName - The local name (without prefix), or the empty string if Namespace processing is not being performed.
	 * qName - The qualified name (with prefix), or the empty string if qualified names are not available.
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// System.out.printf ("Debug: startElement %s\n",localName);

		// Start a tag whose content is not desired
		if (containsNotContentTagSet(qName))
			notContentTagDepth++;
	}



	/**
	 *  end element
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException {
		//System.out.printf ("Debug: endElement %s\n",localName);

		// End of element whose content has not to be extracted
		if (containsNotContentTagSet(qName)) 
			notContentTagDepth--;

		// process empty elements
		// if no characters have been met then the current empty element should not be considered as a currentOpenElementWithTheBeginToSet
		if (!characterFired) {
			currentOpenElementWithTheBeginToSet--;
		}
		characterFired = false;
	}

	/**
	 * Characters 
	 */
	public void characters(char[] ch, int start, int length) throws SAXException {
		characterFired = true;
		
		String charToInsert = " ";
		// inside of a contentTag
		if (notContentTagDepth==0) {
			
			//detaggedText.append(ch, start, length);
			
			detaggedText.append((new StringBuffer()).append(ch, start, length).toString().replaceAll("^ +", "").replaceAll(" +$", ""));

			if (getInsertWSCharAfterLastChar() 
					&& detaggedText.length() >0
					&& (!detaggedText.toString().endsWith(System.lineSeparator())
							))
							//|| !detaggedText.substring(detaggedText.length()-1).equalsIgnoreCase(" "))) 
				detaggedText.append(charToInsert);
			//System.out.printf ("Debug: characters %s\n",String.valueOf(ch));
		}
	}

	/** 
	 * Associating a SAX event with a document location
	 * when attempting to get the begin/end position of start/end element
	 * */
	public void setDocumentLocator(Locator l) {
		System.err.println("Debug: systemId()>"+l.getSystemId()
				+"< publicId()>"+l.getPublicId()
				+"< columnNumber>"+l.getColumnNumber()
				+"< lineNumber>"+l.getLineNumber()+"<");
	}


	/**
	 * 
	 */
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		if (notContentTagDepth==0) 
			if (setPlainTextCursorsRelativelyToXmlTagCursors)
				detaggedText.append(ch, start, length);        
	}

	/**
	 * Return the detagged text
	 * 
	 * @return the detagged text
	 */
	public String getDetaggedText() {
		return detaggedText.toString();
	}

	/**
	 * @return the notContentTagSet
	 */
	private Set<String> getNotContentTagSet() {
		return notContentTagSet;
	}

	/**
	 * @param notContentTagSet the notContentTagSet to set
	 */
	private void setNotContentTagSet(Set<String> notContentTagSet) {
		this.notContentTagSet = notContentTagSet;
	}


	/**
	 * @param qName
	 * @return
	 */
	private boolean containsNotContentTagSet(String qName) {
		return getNotContentTagSet().contains(qName.toLowerCase());
	}

	/**
	 * @return the insertWSCharAfterLastChar
	 */
	public Boolean getInsertWSCharAfterLastChar() {
		return insertWSCharAfterLastChar;
	}

	/**
	 * @param insertWSCharAfterLastChar the insertWSCharAfterLastChar to set
	 */
	public void setInsertWSCharAfterLastChar(Boolean insertWSCharAfterLastChar) {
		this.insertWSCharAfterLastChar = insertWSCharAfterLastChar;
	}

}