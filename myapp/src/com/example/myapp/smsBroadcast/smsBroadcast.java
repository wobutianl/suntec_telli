package com.example.myapp.smsBroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import com.example.myapp.Module.smsContent;

/**
 * Created by zhuxiaolin on 2014/10/8.
 */
public class smsBroadcast extends BroadcastReceiver {

    private static MessageListener mMessageListener;
    private static smsContent sms = new smsContent();
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
                    sms.setSmsAddress(SMSAddress.toString());
                    sms.setSmsContent(SMSContent.toString());
                    System.out.println(" " + SMSAddress + "\n" + " "
                            + SMSContent);

                    mMessageListener.OnReceived(sms);
                }
            }
        }
    }

    // �ص��ӿ�
    public interface MessageListener {
        public void OnReceived(smsContent message);
    }

    public void setOnReceivedMessageListener(MessageListener messageListener) {
        // TODO Auto-generated method stub
        this.mMessageListener=messageListener;
    }
}
