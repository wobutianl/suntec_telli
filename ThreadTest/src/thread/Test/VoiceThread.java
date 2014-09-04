package thread.Test;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class VoiceThread {

	
	private static boolean isRunning = true;
	private static ModelMsg mesg = new ModelMsg();
	
	public static void voiceThread(final Handler handler, final Object obj){
		Log.d("Voice", "Voice Thread");
	    new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(isRunning){
					try {
						Log.d("voiceThread", "123456");
						//Thread.currentThread()
						
						Message mesg = new Message();
						//msg.obj = obj;
						AppendString(obj);
						Log.d("voice ", " append end ");
						mesg.what = Constraints.dataMsg;
						Log.d("voice 2 data", " data Thread begin");
						//Constraints.setTestString(msg.obj.toString());
						//AppendString(msg.obj.toString());
						//mesg.obj = msg;
						//Log.d("voice ", mesg.getMsgStr());
						handler.sendMessage(mesg);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		}
	    ).start();
	}
	
	private static void AppendString(Object obj){
		
		Log.d("voice ", " begin append");
		mesg.setMsgID(1);
		mesg.setMsgStr(" from nanjing to shanghai");
		Log.d("voice ", mesg.getMsgStr());
		
		isRunning = false;
		//return msg;
	}

	public static  ModelMsg getMsg() {
		return mesg;
	}
	
}
