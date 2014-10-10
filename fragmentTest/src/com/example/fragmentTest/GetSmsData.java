package com.example.fragmentTest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import com.example.fragmentTest.android.DB.SmsContent;

/**
 * SMS message receive
 * get the msg Address (number) and content (String)
 * @author zhuxiaolin
 *
 */
public class GetSmsData extends BroadcastReceiver {
	
	private static MessageListener mMessageListener;
	private static SmsContent smsContent = new SmsContent();
	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {
			StringBuffer SMSAddress = new StringBuffer();
			StringBuffer SMSContent = new StringBuffer();
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdusObjects = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[pdusObjects.length];
				for (int i = 0; i < pdusObjects.length; i++) {
					messages[i] = SmsMessage
							.createFromPdu((byte[]) pdusObjects[i]);
				}
				
				for (SmsMessage message : messages) {
					SMSAddress.append(message.getDisplayOriginatingAddress());
					SMSContent.append(message.getDisplayMessageBody());
					smsContent.setSmsAddress(SMSAddress.toString());
					smsContent.setSmsContent(SMSContent.toString());
					System.out.println(" address " + SMSAddress + "\n" + " content "
							+ SMSContent);
					
					mMessageListener.OnReceived(smsContent);
				}
			}
		}
	}
	
	// �ص��ӿ�
	public interface MessageListener {
		public void OnReceived(SmsContent message);
	}

	public void setOnReceivedMessageListener(MessageListener messageListener) {
		// TODO Auto-generated method stub
		this.mMessageListener=messageListener;
	}
	
}
