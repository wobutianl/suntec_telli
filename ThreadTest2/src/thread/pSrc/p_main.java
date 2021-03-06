/******main interface*****/
package thread.pSrc;

import thread.Test.Constraints;
import thread.Test.ThreadTestActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class p_main  // extends Thread
{
	private String TAG = "p_main";
	private static String Cnt;
	private static String Sid;
	URLMsg urlMsg;
//	Handler handler;
    
    public p_main( ) {
    	Cnt = "telli";
		Sid = "telli";
    }

    private static String strXML;
    
//	1.	如果返回的StrXML为Telli，说明传递过来的UMsg有Bug；
//	2.	如果UMsg传递p_stop参数，则返回的StrXML为Stop，同时通知Server关闭服务。
    public String doTask(URLMsg UMsg){
    	
//		String StrXML = "Telli";
//		String StrURL = "Telli";
//		
//		if("0" == UMsg.Msg_DID && "p_start" == UMsg.Msg_Flag)						//New language
//		{
//			Cnt = "0";
//			
//			BlockURL TempURL = new BlockURL(UMsg);
//			StrURL = TempURL.GetStrURL();
//			
//			CallServer TempXML = new CallServer(StrURL);
//			StrXML = TempXML.GetStrXML();
//			
//		    int beginIdx = StrXML.indexOf("<sid>") + "<sid>".length();				//Get SID
//		    int endIdx = StrXML.indexOf("</sid>");
//		    Sid = StrXML.substring(beginIdx, endIdx);
//		    				
//			Cnt =String.valueOf(Integer.parseInt(Cnt)+1);							//If successful, Cnt++, then next DID judge
//		}
//		if("0" !=UMsg.Msg_DID && Cnt == UMsg.Msg_DID && Sid == UMsg.Msg_SID)		//Judge UMsg DID
//		{
//			BlockURL TempURL = new BlockURL(UMsg);
//			StrURL = TempURL.GetStrURL();
//			
//			CallServer TempXML = new CallServer(StrURL);
//			
//			if("p_stop" == UMsg.Msg_Flag)
//			{
//				StrXML = "stop";
//			}
//			else
//			{
//				StrXML = TempXML.GetStrXML();
//			}
//			Cnt =String.valueOf(Integer.parseInt(Cnt)+1);							//If successful, Cnt++, then next DID judge
//		}		
//		System.out.println(strXML);
//		return StrXML;
    	
//    	System.out.println(" xml thread ");
//    	
    	String StrXML = "Telli";
		String StrUML = "Telli";
		
		BlockURL TempURL = new BlockURL(UMsg);
		StrUML = TempURL.GetStrURL();
		Log.d("URL" , StrUML );
		
		CallServer TempXML = new CallServer(StrUML);
		StrXML = TempXML.GetStrXML();
		
		Log.d("XML" , StrXML);
		return StrXML;
    }
    
    public URLMsg getUrlMsg() {
		return urlMsg;
	}

	public void setUrlMsg(URLMsg urlMsg) {
		this.urlMsg = urlMsg;
	}

}
