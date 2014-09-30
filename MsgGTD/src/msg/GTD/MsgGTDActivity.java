package msg.GTD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import msg.GTD.GetSmsData.MessageListener;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
 
/**
 * 
 * @version 1.00
 * @author 
 */
public class MsgGTDActivity extends Activity {

    private ListView gList;
    private GetSmsData mSMSBroadcastReceiver;
    List<SmsData> data = null;
    
    private SimpleAdapter adapter;
    private List<LinkedHashMap<String,Object>> mdata ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        gList = (ListView)this.findViewById(R.id.listView1);
        
        init();
        
        // load db data when it start .
        data = new GtdTable(MsgGTDActivity.this).retrieveData(null);
        
        mdata = new ArrayList<LinkedHashMap<String,Object>>(); 
        
		for (SmsData sms : data) {
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("TextView_SmsBody", sms.getContent());
			Log.d("Main", sms.getContent());
			displayToast(sms.getContent());
			mdata.add(map);
		}
		adapter = new SimpleAdapter(MsgGTDActivity.this, mdata,
				R.layout.smsitem, new String[] { "TextView_SmsBody" },
				new int[] { R.id.TextView_SmsBody });
		gList.setAdapter(adapter);		
	}

    SmsParse parse = new SmsParse();
    SmsData smsdata = new SmsData();
	private void init() {
		mSMSBroadcastReceiver = new GetSmsData();
		mSMSBroadcastReceiver
				.setOnReceivedMessageListener(new MessageListener() {
					@Override
					public void OnReceived(SmsContent sms) {
						displayToast(sms.getSmsContent());
						GtdTable es = new GtdTable(MsgGTDActivity.this);
						smsdata = parse.splitSms(sms.getSmsContent());
//						SmsData om = new SmsData();
//						om.setContent(sms.getSmsContent());
//						om.setWrite_time(System.currentTimeMillis());
						es.addData(smsdata);
						sendToList(sms.getSmsContent());
					}
				});
	}
	/**
	 * send message to Chat ListView notify list Data
	 * @param str	:message
	 * @param isComeMsg     :diacharge the message
	 */
	private void sendToList(String str) { // 
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("TextView_SmsBody", str);
		mdata.add(0, map);  // insert data to front
		adapter.notifyDataSetChanged(); // refresh data
//		gList.setSelection(mdata.size() - 1);
	}
	
	private void displayToast(String str){
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
 

}