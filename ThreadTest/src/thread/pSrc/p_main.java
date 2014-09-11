/******main interface*****/
package thread.pSrc;

import thread.Test.Constraints;
import thread.Test.ModelMsg;
import thread.Test.ThreadTestActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class p_main extends Thread
{
	private String TAG = "p_main";

	URLMsg urlMsg;
//	Handler handler;
    
    public p_main( ) {
    }
    
    public void setStr(URLMsg urlMsg) {
    	this.urlMsg = urlMsg;
    }

//    @Override
//    public void run() {
//
//        try {  
//        	Log.d(TAG , " conserver Thread begin ");
//            String strXML = doTask(urlMsg);
//            
//            Message msg = prepareMessage(strXML);
//            ThreadTestActivity.handler.sendMessage(msg);   
//        } catch (Exception e) {
//            Log.d(TAG, "interrupted!");
//        }
//    }
    private static String strXML;
    private static Message msg;
    private static Bundle bd = new Bundle();
    private static URLMsg p_msg = new URLMsg();
    
	public void run() {
		this.setName("ServerThread");
		System.out.println("server is running!");
        Looper.prepare();		
		ThreadTestActivity.mServerHandler = new Handler() {
			
            @Override           
            public void handleMessage(Message msg) {
                switch(msg.what){
				    case 110:
				    	// start
				    	bd = msg.getData();
				    	p_msg = bd.getParcelable("toServer");
				    	
//				    	Log.d(TAG, p_msg.Msg_Flag);
			            strXML = doTask(p_msg);
			            
			            msg = prepareMessage(strXML);
//			            Log.d(TAG, strXML);
			            ThreadTestActivity.handler.sendMessage(msg);  
                        break;
					case 111:
						// vr
						bd = msg.getData();
						p_msg = bd.getParcelable("toServer");
			            strXML = doTask(p_msg);
//			            Log.d("111", p_msg.getMsg_STT());
			            
			            msg = prepareMessage(strXML);
			            ThreadTestActivity.handler.sendMessage(msg); 
                        break; 
					case 112:
						// stop 
						bd = msg.getData();
						p_msg = bd.getParcelable("toServer");
			            strXML = doTask(p_msg);
			            
			            msg = prepareMessage(strXML);
//			            Log.d(TAG, strXML);
			            ThreadTestActivity.handler.sendMessage(msg); 
                    default:
                        break;						
				}
            }
        };
		//准备接收消息
		Looper.loop();
		System.out.println("to the run() end!");
	}	

    private Message prepareMessage(String str) {
        Message result = ThreadTestActivity.handler.obtainMessage();
        result.obj = str;
        result.what = Constraints.dataMsg;       
        return result;
    }
    
    private String doTask(URLMsg UMsg){
    	System.out.println(" xml thread ");
    	
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
