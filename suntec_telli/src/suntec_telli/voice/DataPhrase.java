package suntec_telli.voice;

import model.*;

/*
 * phrase TXT to sendXMLInfo
 * phrase XML to receiveMsgInfo
 */
public class DataPhrase {

	/*
	 * change txt to XML data
	 */
	public SendXMLInfo PhraseTxtToXML(String txt){
		return sendXMLinfo;
	}
	
	public ReceiveMsgInfo PhraseXMLToMsg(String XML){
		return receiveMsginfo;
	}
	
	private SendXMLInfo sendXMLinfo;
	private ReceiveMsgInfo receiveMsginfo;
}
