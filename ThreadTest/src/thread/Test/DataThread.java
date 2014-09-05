package thread.Test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.test.IsolatedContext;
import android.util.Log;

import thread.Test.ModelMsg;

public class DataThread extends Thread {

	private String TAG = "DataThread";
	//private static Handler handler;
	private static boolean isRunning = true;
	//private ModelData mdata = new ModelData();  //model Msg
	/*
	 * handler 句柄
	 * obj     上一个线程传递的数据
	 */
	
	//private static final String TAG = DataThread.class.getSimpleName();
    Handler handler;
    
    public DataThread(Handler handler) {
        super();
        this.handler = handler;
    }

    @Override
    public void run() {
    	Looper.prepare();//1、初始化Looper

        try {  // 模拟执行某项任务，下载等
            Thread.sleep(5000);
            handler.obtainMessage();
            
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
        Bundle data = new Bundle();
        mdata.setMsgID(1);
		mdata.setMsgStr(str );
        data.putParcelable("data", mdata);
        
        result.setData(data);
        return result;
    }
    
    private void doTask(){
    	System.out.println(" data thread ");
    }
	

}
