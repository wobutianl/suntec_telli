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

import thread.Test.Constraints;
import thread.Test.DataThread;
import thread.Test.VoiceThread;

public class ThreadTestActivity extends Activity {
    
	private String TAG = "chapter8_3";
    private Button btnEnd;
    private Button btnBeg;
    private TextView labelTimer;
    private Thread clockThread;
    private boolean isRunning = true;
    private Handler handler;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnEnd = (Button) findViewById(R.id.button1);
        btnBeg = (Button) findViewById(R.id.button2);
        labelTimer = (TextView) findViewById(R.id.textView1);
        
        btnEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //isRunning = false;
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               VoiceThread.voiceThread(handler, "abdc");
            }
        });
        
        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                case Constraints.dataMsg:
                	ModelMsg mesg = VoiceThread.getMsg();
                	DataThread.dataThread(handler, mesg);
                	Log.d("data ", " data Thread End ");
                	labelTimer.setText(Constraints.getTestString());
                    break;
                case 1:
                	//DataThread.dataThread(handler, msg.obj);
                	labelTimer.setText(Constraints.getTestString());
                }
            }

        };

    }
	
}