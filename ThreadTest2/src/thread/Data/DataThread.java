/**
 * @file Data Thread
 * 
 * @brief parse String data from Server 
 * 
 * parse XML data to L_MSG data module
 */


package thread.Data;

import thread.Model.L_XMLMsg;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * @brief parse String data from Server  
 *
 *parse XML data to L_MSG data module
 *
 *@author linbing
 */ 
public class DataThread extends Thread {

	private String TAG = "DataThread";     ///< Class tag name for log

	private L_XMLMsg l_msg = new L_XMLMsg(); ///< LingBing XML Message

	/**
	 * Construct
	 */
	public DataThread() {
		super();
	}

	/**
	 * parse XML String
	 * @param objXml from Server
	 * @return an data struct
	 */
	public L_XMLMsg parseXml(String objXml) {
		// 1、将传入的字符串流化
		InputStream in = null;
		if (objXml == "stop") {
			l_msg.setType("stop");
		} else {
			try {
				in = new ByteArrayInputStream(objXml.getBytes("UTF-8"));
				SAXReader reader = new SAXReader();
				// 2、将流转成document对象
				Document document = reader.read(in);
				Element rootElement = document.getRootElement(); // 拿到根节点

				Element did = rootElement.element("did");
				String Did = did.getTextTrim();

				Element sid = rootElement.element("sid");
				String Sid = sid.getTextTrim();

				 System.out.println("did--->" + Did);
				 System.out.println("sid--->" + Sid);

				l_msg.setDid(Did);
				l_msg.setSid(Sid);

				// //////解析多次重复的循环 ///////////
				for (Iterator whereArray = rootElement.elementIterator(); whereArray
						.hasNext();) {
					Element type = (Element) whereArray.next();
					String nodeName = type.getName();
					if (!nodeName.equals("op")) { // 如果这个节点不是op就跳出。
						continue;
					}

					Element hins = type.element("hints");
					Element prompt = type.element("prompt");
					Element appName = type.element("appName");
					String Display = null;
					String Tts;
					String AppName;
					// String promptFlag;
					String Type;

					if (null != hins) {
						Element content = hins.element("content");
						if (null != content) {
							Display = content.getTextTrim();
							System.out.println("content-->" + Display);
							l_msg.setDisplay(Display);
						}
					}
					else if (null != prompt) {
						Tts = prompt.attributeValue("tts");
						Tts = splitCommon(Tts);
									
						if(null == prompt.attributeValue("display") && 
								null == prompt.attributeValue("tts")){
							l_msg.setTts(null);
							l_msg.setDisplay(null);
						}
						if(null != prompt.attributeValue("display") && 
								null == prompt.attributeValue("tts")){
							l_msg.setTts(Display);
							l_msg.setDisplay(Display);
						}
						if(null == prompt.attributeValue("display") && 
								null != prompt.attributeValue("tts")){
							l_msg.setTts(Tts);
							l_msg.setDisplay(Tts);
						}
						if(null != prompt.attributeValue("display") && 
								null != prompt.attributeValue("tts")){
							l_msg.setTts(Tts);
							l_msg.setDisplay(Display);
							
						}
//						if (null != prompt.attributeValue("display")) {
//							l_msg.setDisplay(Display);
//						}
//						if (null == prompt.attributeValue("tts")) {
//							Tts = null;
//							l_msg.setTts(null);
//						} else {
//							Tts = prompt.attributeValue("tts");
//							Pattern pattern = Pattern.compile("\\[.+?\\]");
//							Matcher matcher = pattern.matcher(Tts);
//							String string = matcher.replaceAll("");
//							System.out.println(string);
//							System.out.println("tts-->" + Tts);
//							l_msg.setTts(string);
//						}
					}
					if (null != appName) {
						AppName = appName.getTextTrim();
						System.out.println("appName-->" + AppName);
						l_msg.setAppName(AppName);
					} 
					else {
						Type = type.attributeValue("type");
						 System.out.println("Type-->" + Type);
						l_msg.setType(Type);
					}
				}
			} catch (UnsupportedEncodingException e) {
				System.out.println("流操作异常");
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				System.out.println("Document对象操作异常");
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("流关闭异常");
					e.printStackTrace();
				}
			}
		}
		return l_msg;
	}
	
	/**
	 * split the [] from Display and TTS 
	 * @param Display & tts String
	 * @return a string which split from []
	 */
	private String splitCommon(String string){
		String str;
		Pattern pattern = Pattern.compile("\\[.+?\\]");
		Matcher matcher = pattern.matcher(string);
		str = matcher.replaceAll("");
		return str;
	}

}
