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
    	Looper.prepare();//1、初始化Looper

        try {  // 模拟执行某项任务，下载等
            Thread.sleep(5000);
            //handler.obtainMessage();
            
            // 任务完成后通知activity更新UI
            Message msg = prepareMessage("task completed!");
            // message将被添加到主线程的MQ中
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
