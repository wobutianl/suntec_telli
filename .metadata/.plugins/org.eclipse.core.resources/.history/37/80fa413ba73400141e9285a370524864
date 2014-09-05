package thread.Test;

import android.os.Handler;
import android.os.Message;
import android.test.IsolatedContext;
import android.util.Log;

import thread.Test.ModelMsg;

public class DataThread {

	//private static Handler handler;
	private static boolean isRunning = true;
	
	/*
	 * handler 句柄
	 * obj     上一个线程传递的数据
	 */
	public static void dataThread(final Handler handler, final ModelMsg mesg){
		Log.d("data", "data Thread really begin");
	    new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(isRunning){
					try {
						//Thread.currentThread()
						
						Message msg = new Message();
						//msg.obj = obj;
						AppendString(mesg);
						msg.what = Constraints.dataMsg;
						Log.d("data", msg.obj.toString());
						//Constraints.setTestString(msg.obj.toString());
						//AppendString(msg.obj.toString());
						//msg.obj = mdata;
						handler.sendMessage(msg);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		}
	    ).start();
	}
	private static ModelData mdata = new ModelData();
	
	public static ModelData getMdata() {
		return mdata;
	}
	

	private static void AppendString(ModelMsg msg){
		// ModelMsg msg = (ModelMsg)obj;
			
		mdata.setDid(msg.getMsgID());
		mdata.setMessage(msg.getMsgStr());
		String str = "abd";
	
		Constraints.setTestString(str);
		Log.d("str", str);
		isRunning = false;
		//return mdata;
		
	}
	
	
	
    
}
