package thread.Test;

import thread.Model.T_URIMsg;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class ConServer extends Thread {
	
	private String TAG = "ConServerThread";
	private static boolean isRunning = true;
	private static ModelMsg mesg = new ModelMsg();
	
	T_URIMsg URImsg ;
	
    Handler mHandler;
    //Handler sHandler;
    
    public ConServer(Handler mhandler, T_URIMsg URImsg) {
        super();
        this.mHandler = mhandler;
        //this.sHandler = shandler;
        this.URImsg = URImsg;
    }

    @Override
    public void run() {
    	//Looper.prepare();//1、初始化Looper

        try {  // 模拟执行某项任务，下载等
//        	Looper.prepare();//1、初始化Looper
//            sHandler = new Handler(){//2、绑定 shandler到CustomThread实例的Looper对象
//                public void handleMessage (Message msg) {//3、定义处理消息的方法
//                    switch(msg.what) {
//                    case 1:
//                        Log.d(TAG, "Server started :" + (String) msg.obj);
//                        //break;
//                    //case 2:
//                    }
//                }
//            };
            //Looper.loop();//4、启动消息循环

            
            //mHandler.obtainMessage();
        	Log.d(TAG , " conserver Thread begin ");
            // 任务完成后通知activity更新UI
            Message msg = prepareMessage("task completed!");
            // message将被添加到主线程的MQ中
            mHandler.sendMessage(msg);
        } catch (Exception e) {
            Log.d(TAG, "interrupted!");
        }
        //Looper.loop();
    }

    private Message prepareMessage(String str) {
        Message result = mHandler.obtainMessage();
        
        ModelMsg mdata = new ModelMsg();
        mdata.setMsgID(1);
		mdata.setMsgStr( str );
		
        Bundle data = new Bundle();     
        data.putParcelable("data", mdata);
        Log.d(TAG , str);
        result.setData(data);
        return result;
    }
    
    private void doTask(){
    	System.out.println(" data thread ");
    }

}
