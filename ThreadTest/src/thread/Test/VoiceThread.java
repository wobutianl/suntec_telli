package thread.Test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class VoiceThread extends Thread {

	private String TAG = "VoiceThread";
	private static boolean isRunning = true;
	private static ModelMsg mesg = new ModelMsg();
	
    Handler handler;
    
    public VoiceThread(Handler handler) {
        super();
        this.handler = handler;
    }

    @Override
    public void run() {
    	Looper.prepare();//1����ʼ��Looper

        try {  // ģ��ִ��ĳ���������ص�
            Thread.sleep(5000);
            //handler.obtainMessage();
            
            // ������ɺ�֪ͨactivity����UI
            Message msg = prepareMessage("task completed!");
            // message������ӵ����̵߳�MQ��
            handler.sendMessage(msg);
        } catch (InterruptedException e) {
            Log.d(TAG, "interrupted!");
        }
        Looper.loop();
    }

    private Message prepareMessage(String str) {
        Message result = handler.obtainMessage();
        
        ModelMsg mdata = new ModelMsg();
        mdata.setMsgID(1);
		mdata.setMsgStr(str );
		
        Bundle data = new Bundle();     
        data.putParcelable("data", mdata);
        
        result.setData(data);
        return result;
    }
    
    private void doTask(){
    	System.out.println(" data thread ");
    }
		
	
}
