/******main interface*****/
package thread.pSrc;

import thread.Test.Constraints;
import thread.Test.ModelMsg;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class p_main extends Thread
{
	private String TAG = "p_main";

	URLMsg urlMsg;
    Handler handler;
    
    public p_main(Handler handler, URLMsg urlMsg) {
        super();
        this.handler = handler;
        this.urlMsg = urlMsg;
    }

    @Override
    public void run() {

        try {  // 模拟执行某项任务，下载等
        	Log.d(TAG , " conserver Thread begin ");
            String strXML = doTask(urlMsg);
            
            // 任务完成后通知activity更新UI
            Message msg = prepareMessage(strXML);
            // message将被添加到主线程的MQ中
            handler.sendMessage(msg);
            
        } catch (Exception e) {
            Log.d(TAG, "interrupted!");
        }

    }

    private Message prepareMessage(String str) {
        Message result = handler.obtainMessage();
        result.obj = str;
        result.what = Constraints.dataMsg;
        
        return result;
    }
    
    private String doTask(URLMsg UMsg){
    	System.out.println(" data thread ");
    	
    	String StrXML = "Telli";
		String StrUML = "Telli";
		
		BlockURL TempURL = new BlockURL(UMsg);
		StrUML = TempURL.GetStrURL();
		Log.d(TAG , StrUML );
		
		CallServer TempXML = new CallServer(StrUML);
		StrXML = TempXML.GetStrXML();
		
		Log.d(TAG , StrXML );
		return StrXML;
    }
    

}
