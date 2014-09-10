package thread.Test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.test.IsolatedContext;
import android.util.Log;

import thread.Model.L_XMLMsg;
import thread.Test.ModelMsg;
import thread.VR.Config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.baidu.voicerecognition.android.VoiceRecognitionClient;
import com.baidu.voicerecognition.android.VoiceRecognitionConfig;

public class DataThread extends Thread {

	private String TAG = "DataThread";

	private static boolean isRunning = true;
	private L_XMLMsg l_msg = new L_XMLMsg();

	String xmlStr;

	public DataThread() {
		super();
		// this.xmlStr = str;
	}

//	public void setStr(String str) {
//		this.xmlStr = str;
//	}

	// @Override
	// public void run() {
	// Looper.prepare();//
	//
	// try { //
	// ThreadTestActivity.handler.obtainMessage();
	//
	// Message msg = prepareMessage(xmlStr);
	// ThreadTestActivity.handler.sendMessage(msg);
	// } catch (Exception e) {
	//
	// }
	// Looper.loop();
	// }
//	Message msg;
//
//	@Override
//	public void run() {
//
//		Looper.prepare();
//
//		ThreadTestActivity.mDataHandler = new Handler() {
//
//			@Override
//			public void handleMessage(Message msg) {
//				switch (msg.what) {
//				case 210:
//					// start
//					ThreadTestActivity.handler.obtainMessage();
//
//					msg = prepareMessage(msg.obj.toString());
//					ThreadTestActivity.handler.sendMessage(msg);
//					break;
//				// case 211:
//				// // vr
//				// ThreadTestActivity.handler.obtainMessage();
//				//
//				// msg = prepareMessage(msg.obj.toString());
//				// ThreadTestActivity.handler.sendMessage(msg);
//				// break;
//				// case 212:
//				// // stop
//				// ThreadTestActivity.handler.obtainMessage();
//				//
//				// msg = prepareMessage(msg.obj.toString());
//				// ThreadTestActivity.handler.sendMessage(msg);
//				default:
//					break;
//				}
//			}
//		};
//		// 准备接收消息
//		Looper.loop();
//		System.out.println("to the run() end!");
//	}
//
//	private Message prepareMessage(String str) {
//		Message result = ThreadTestActivity.handler.obtainMessage();
//		l_msg = parseXml(str);
//		Bundle data = new Bundle();
//		// l_msg.writeToParcel(out, flags)
//		data.putParcelable("data", l_msg);
//		result.setData(data);
//		result.what = Integer.parseInt(l_msg.getFlag());
//		Log.d(TAG, l_msg.getTts().toString());
//		return result;
//	}

	public L_XMLMsg parseXml(String objXml) {
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
				String promptFlag;
				String Type;

				if (null != hins) {
					Element content = hins.element("content");
					if (null != content) {
						Display = content.getTextTrim();
						System.out.println("content-->" + Display);

						l_msg.setDisplay(Display);
					}
				} else if (null != prompt) {
					if (null != prompt.attributeValue("display")) {
						Display = prompt.attributeValue("tts");
						System.out.println("display-->" + Display);

						l_msg.setDisplay(Display);
					}
					if (null == prompt.attributeValue("tts")) {
						Tts = null;

						l_msg.setTts(null);
					} else {
						Tts = prompt.attributeValue("tts");
						System.out.println("tts-->" + Tts);

						l_msg.setTts(Tts);
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
					l_msg.setFlag(promptFlag);
				}
				if (null != appName) {

					AppName = appName.getTextTrim();
					System.out.println("appName-->" + AppName);
					l_msg.setAppName(AppName);
				} else {
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
		return l_msg;

	}

}
