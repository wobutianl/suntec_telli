package thread.Test;

import android.os.Handler;
import android.os.Message;

public class ConnectServer {

	public static void DataThread(final Handler handler){
	    new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub

					try {
						//Thread.currentThread()
						Message msg = new Message();
						msg.obj = " Data Class + ";
						msg.what = Constraints.ServerMsg;
						//Constraints.setTestString(msg.obj.toString());
						AppendString(msg.obj.toString());
						handler.sendMessage(msg);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}				
			}
		});
	}
	
	private static void AppendString(String str){
		Constraints.setTestString(str);
	}
	
}
