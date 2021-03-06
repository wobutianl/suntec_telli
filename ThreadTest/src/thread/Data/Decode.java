package thread.Data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Decode {

	public void parseXml(String objXml) {
		// 1、将传入的字符串流化
		InputStream in = null;
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
				String Display = null;
				String Tts;
				String promptFlag;
				String APP_name;
				String Type;

				if (null != hins) {

					Element content = hins.element("content");
					if (null != content) {
						Display = content.getTextTrim();
						System.out.println("content-->" + Display);
					}
				}

				else if (null != prompt) {

					if (null != prompt.attributeValue("display")) {
						Display = prompt.attributeValue("tts");
						System.out.println("display-->" + Display);
					}

					if (null == prompt.attributeValue("tts")) {
						Tts = null;
					} else {
						Tts = prompt.attributeValue("tts");
						System.out.println("tts-->" + Tts);
					}

					if (Display == null && Tts == null) {

						promptFlag = "20";
					} else if (Display != null && Tts == null) {

						promptFlag = "21";
					} else if (Display == null && Tts != null) {

						promptFlag = "22";
					} else {

						promptFlag = "23";
					}
					System.out.println("promptFlag-->" + promptFlag);

				}

				else {

					Type = type.attributeValue("type");
					System.out.println("Type-->" + Type);
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
}
