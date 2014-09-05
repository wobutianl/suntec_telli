package thread.Test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import thread.Model.T_URIMsg;
import thread.Test.Constraints;
import thread.Test.DataThread;
import thread.Test.VoiceThread;
import thread.pSrc.URLMsg;
import thread.pSrc.p_main;

public class ThreadTestActivity extends Activity {
    
	private String TAG = "MainThread";
    private Button btnEnd;
    private Button btnBeg;
    private TextView labelTimer;
    private Thread clockThread;
    private boolean isRunning = true;
    private Handler handler ;
    private URLMsg tMsg = new URLMsg();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.d(TAG , " conserver Thread begin ");
        
        btnEnd = (Button) findViewById(R.id.button1);
        btnBeg = (Button) findViewById(R.id.button2);
        labelTimer = (TextView) findViewById(R.id.textView1);
        
        //new ConServer(handler, sHandler).start();
        
        
        btnEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isRunning = false;
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
        
            @Override
            public void onClick(View v) {

            	tMsg.Msg_Flag = "p_start";
            	tMsg.Msg_DID = "0";
            	new p_main(handler, tMsg).start();
            	Log.d(TAG , " conserver Thread begin ");

            }
        });
        
        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                case Constraints.dataMsg:
                	DataThread dataThread = new DataThread(handler);
                	dataThread.start();
                	Log.d(TAG , " data Thread End ");
                	labelTimer.setText(Constraints.getTestString());
                
                    break;
                case 1:
                	labelTimer.setText(Constraints.getTestString());
                }
            }
        };
    }
}