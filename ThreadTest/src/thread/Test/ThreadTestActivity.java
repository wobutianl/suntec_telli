package thread.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;
import com.baidu.voicerecognition.android.VoiceRecognitionClient;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import thread.Model.L_XMLMsg;
import thread.Test.DataThread;
import thread.VR.PSETTelliVoiceRecognitioner;
import thread.pSrc.URLMsg;
import thread.pSrc.p_main;

public class ThreadTestActivity extends Activity implements
		SpeechSynthesizerListener {

	// ///////////////////////////////////////////
	public static final int RECOGNITION_IS_READY = 0x1;
	public static final int RECOGNITION_SPEECH_START = 0x2;
	public static final int RECOGNITION_SPEECH_END = 0x3;
	public static final int RECOGNITION_RECOGNITION_FINISH = 0x4;
	public static final int RECOGNITION_RECOGNITION_PARTIALFINISH = 0x5;
	public static final int RECOGNITION_RECOGNITION_CANCELED = 0x6;
	public static final int RECOGNITION_RECOGNITION_ERROR = 0x7;
	public static final int USER_START_SPEECH = 0x8;
	public static final int USER_CANCEL_SPEECH = 0x9;
	public static VoiceRecognitionClient mASREngine;
	// ////////////////////////////////////////////////////////////

	private String TAG = "MainThread";

	// ////// UI ////////////////
	private Button sendButton = null;
	// private Button sendButton = null ;
	private EditText contentEditText = null;
	private ListView chatListView = null;
	private List<ChatEntity> chatList = null;
	private TwoAdapter chatAdapter = null;
	private Button resBtn;
	private EditText resText;

	private ChatEntity chatEntity = new ChatEntity();

	private boolean isRunning = true;
	public static Handler handler, mVoiceRecognitionerHandler;
	public static Handler mServerHandler;
	public static Handler mDataHandler;

	// ////// VR ////////////
	private Button btnBegin;
	private Button btnCancel;
	private PSETTelliVoiceRecognitioner vr = new PSETTelliVoiceRecognitioner();

	// ///// TTS ////////////
	private SpeechSynthesizer speechSynthesizer;
	private String result;

	// ////// ConServer /////////////
	private URLMsg tMsg = new URLMsg();
	private p_main p_server = new p_main();
	private Bundle bd; // bundle

	// //// Data ////////////
	private DataThread dataThread = new DataThread();
	private L_XMLMsg l_msg;

	private String handleString;
	private static String sid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ///// VR ////////////
		btnBegin = (Button) findViewById(R.id.beginBtn);
		btnCancel = (Button) findViewById(R.id.cancelBtn);

		mASREngine = VoiceRecognitionClient.getInstance(this);
		mASREngine.setTokenApis("plsB3YLqYtjNqPxsMRBpNywS",
				"NzMCBcGSTRovw3C7RPCiDcbWquNB7xl5");

		// /////// TTS //////////
		speechSynthesizer = new SpeechSynthesizer(getApplicationContext(),
				"holder", this);
		speechSynthesizer.setApiKey("plsB3YLqYtjNqPxsMRBpNywS",
				"NzMCBcGSTRovw3C7RPCiDcbWquNB7xl5");
		speechSynthesizer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		// /////// UI ///////////
		contentEditText = (EditText) this.findViewById(R.id.sendText);
		sendButton = (Button) this.findViewById(R.id.sendBtn);

		resBtn = (Button) this.findViewById(R.id.resBtn);
		resText = (EditText) this.findViewById(R.id.resText);

		chatListView = (ListView) this.findViewById(R.id.listView1);
		chatList = new ArrayList<ChatEntity>();

		chatAdapter = new TwoAdapter(this, chatList);
		chatListView.setAdapter(chatAdapter);

		// ///// main Handler /////////////////
		handler = new Handler() {
			int ret;

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				// ///// VR handler ///////////////
				case RECOGNITION_IS_READY:
					// 提示用户可以开始说话 // 这个时候应该说出来
					send(getString(R.string.please_speak), true);
					System.out.println("receive notify that ASREngine is ready!");
					break;
				case RECOGNITION_SPEECH_START:
					// 提示说话中 //这个时候应该fragement
					send(getString(R.string.speaking), true);
					System.out.println("receive notify that user is speaking!");
					break;
				case RECOGNITION_SPEECH_END:
					// 提示识别中，请等待结果 // 这个时候应该转圈圈
					send(getString(R.string.in_recog), true);
					System.out.println("receive notify that speaking is end!");
					break;
				case RECOGNITION_RECOGNITION_FINISH:
					// 提示识别完成，并显示识别结果 // 这个时候应该显示结果，传给Server
					send(getString(R.string.finished), true);
					result = (String) (msg.obj);
					send(result, false);
					
					serverVR(result, sid);
					//URLMsg urlMsg = new URLMsg(result, "p_vr", "1");
					//p_server.setStr(urlMsg);
					break;
				case RECOGNITION_RECOGNITION_PARTIALFINISH:
					// 提示识别中，并显示识别结果
					send(getString(R.string.in_recog), true);
					result = (String) (msg.obj);
					send(result, false);
					break;
				case RECOGNITION_RECOGNITION_CANCELED:
					// 提示取消成功，请重新开始
					send(getString(R.string.is_canceled), true);
					// 刷新取消按键状态
					btnCancel.setEnabled(false);
					setParams();
					ret = speechSynthesizer.speak(getString(R.string.is_canceled));
					if (ret != 0) {
						// 显示错误信息
					}
					break;

				// ///////// Data /////////////////
				case Constraints.dataMsg:
					Log.d(TAG, " datamsg ");
					String xmlString = msg.obj.toString();
					send(xmlString , true);

					l_msg = dataThread.parseXml(xmlString);
					if (l_msg.getSid() != null){
						sid = l_msg.getSid();
					}
					if (l_msg.getTts() != null){
						setParams();
						ret = speechSynthesizer.speak(l_msg.getTts());
						if (ret != 0) {
							// 显示错误信息
						}
					}
					if (l_msg.getDisplay() != null){
						send(l_msg.getDisplay(), true);
					}
					if (l_msg.getAppName() != null){
						startApp(l_msg.getAppName());
					}
					else{
						Log.d(TAG, "ddd");
					}
//					dataThread.setStr(xmlString);
//					dataThread.start();
					break;
					
//				case 20: // both null
//					bd = msg.getData();
//					
//					l_msg = (L_XMLMsg) bd.getParcelable("data");
//					Log.d(TAG, l_msg.getDisplay());
//					sid = l_msg.getSid();
//					
//					// send(l_msg.getDisplay() , true);
//					//
//					// setParams();
//					Log.d(TAG, l_msg.getTts());
//					// ret = speechSynthesizer.speak(l_msg.getTts());
//					// if (ret != 0) {
//					// //显示错误信息
//					// }
//					break;
//				case 21: // TTS null
//					bd = msg.getData();
//
//					l_msg = (L_XMLMsg) bd.getParcelable("data");
//					Log.d(TAG, l_msg.getDisplay());
//					send(l_msg.getDisplay(), true);
//					sid = l_msg.getSid();
//					
//					// setParams();
//					Log.d(TAG, l_msg.getTts());
//					// ret = speechSynthesizer.speak(l_msg.getTts());
//					// if (ret != 0) {
//					// //显示错误信息
//					// }
//					break;
//				case 22: // Display null
//					bd = msg.getData();
//
//					l_msg = (L_XMLMsg) bd.getParcelable("data");
//					Log.d(TAG, l_msg.getDisplay());
//					send(l_msg.getDisplay(), true);
//					sid = l_msg.getSid();
//					
//					setParams();
//					Log.d(TAG, l_msg.getTts());
//					ret = speechSynthesizer.speak(l_msg.getTts());
//					if (ret != 0) {
//						// 显示错误信息
//					}
//					Log.d(TAG, " begin TTS end ");
//					break;
//				case 23: // have both
//					bd = msg.getData();
//
//					l_msg = (L_XMLMsg) bd.getParcelable("data");
//					Log.d(TAG, l_msg.getDisplay());
//					send(l_msg.getDisplay(), true);
//					sid = l_msg.getSid();
//					
//					setParams();
//					Log.d(TAG, l_msg.getTts());
//					ret = speechSynthesizer.speak(l_msg.getTts());
//					if (ret != 0) {
//						// 显示错误信息
//					}
//					break;
				default:
					dataThread.stop();
					break;
				}
			}

		};
		// /////// 软件开启调用 Server Start 返回结果Display显示////////
		p_server.start();

		// ////// 开启软件 启动VR， VR 开始轮询信息获取 ///////////////
		vr.start();

		// / 触发VR 开始 和 取消的操作
		btnBegin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				btnCancel.setEnabled(true);
				if (mVoiceRecognitionerHandler != null) {
					// 通知子线程发起识别
					Log.d(TAG, " voice reco begin");
					Message toVoiceRecognitioner = mVoiceRecognitionerHandler
							.obtainMessage();
					toVoiceRecognitioner.what = USER_START_SPEECH;
					mVoiceRecognitionerHandler
							.sendMessage(toVoiceRecognitioner);
					Log.d(TAG, " voice reco end");
				}
			}

		});

		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (mVoiceRecognitionerHandler != null) {
					// 通知子线程发起识别
					Message toVoiceRecognitioner = mVoiceRecognitionerHandler
							.obtainMessage();
					toVoiceRecognitioner.what = USER_CANCEL_SPEECH;
					mVoiceRecognitionerHandler
							.sendMessage(toVoiceRecognitioner);
				}
			}
		});

		sendButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = contentEditText.getText().toString();
				serverVR(str, sid);
			}
		});
		serverStart();

	}

	// ///// Server start ////////
	// private static int server_start_flag = 0;
	private static int server_flag = 1;

	private void serverStart() {
		if (mServerHandler != null) {
			// 通知子线程发起识别
			Log.d(TAG, " server send start ");
			Message toServer = mServerHandler.obtainMessage();
			toServer.what = 110;
			
			tMsg.setMsg_Flag("p_start");
			tMsg.setMsg_DID("0") ;
			
			Bundle bd = new Bundle();
			bd.putParcelable("toServer", tMsg);
			toServer.setData(bd);
			mServerHandler.sendMessage(toServer);
		}
	}

	private void serverEnd(String sid) {
		if (mServerHandler != null) {
			// 通知子线程发起识别
			Log.d(TAG, " server send start ");
			Message toServer = mServerHandler.obtainMessage();
			toServer.what = 112;
			
			tMsg.setMsg_Flag("p_stop");
			server_flag = server_flag + 1;
			tMsg.setMsg_SID(sid);
			tMsg.setMsg_DID(String.valueOf(server_flag)) ;
			
			Bundle bd = new Bundle();
			bd.putParcelable("toServer", tMsg);
			toServer.setData(bd);
			mServerHandler.sendMessage(toServer);
			
			server_flag = 1;
		}
	}

	private void serverVR(String str, String sid) {
		if (mServerHandler != null) {
			// 通知子线程发起识别
			Log.d(TAG, " server send start ");
			Message toServer = mServerHandler.obtainMessage();
			toServer.what = 111;
			
			server_flag = server_flag + 1;
			tMsg.setMsg_Flag("p_vr");
			tMsg.setMsg_STT(str);
			tMsg.setMsg_SID(sid);
			Log.d(TAG, sid );
			Log.d("str", str);
			tMsg.setMsg_DID(String.valueOf(server_flag)) ;
			
			Bundle bd = new Bundle();
			bd.putParcelable("toServer", tMsg);
			toServer.setData(bd);
			mServerHandler.sendMessage(toServer);
		}
	}


	// //// List View UI ///////////////
	private void send(String str, boolean isComeMsg) { // !!!! the main point
		ChatEntity chatEntity = new ChatEntity();
		chatEntity.setChatTime("2012-09-20 15:16:34");
		chatEntity.setContent(str);
		chatEntity.setComeMsg(isComeMsg);
		chatList.add(chatEntity);
		chatAdapter.notifyDataSetChanged(); // refresh data
		chatListView.setSelection(chatList.size() - 1);
		// editText.setText("");
	}

	// /////// start APP ///////////

	private List<ResolveInfo> mApps;
	private static HashMap hashmap = new HashMap();
	private ResolveInfo info;

	public void startApp(String Appname) {
		// TODO Auto-generated method stub
		Log.d("package", Appname);
		for (Iterator<String> i = hashmap.keySet().iterator(); i.hasNext();) {
			String key = i.next();
			String value = hashmap.get(key).toString();
			System.out.println(key);
			System.out.println(value);
			// sum += value;
		}
		String PacStr = hashmap.get(Appname).toString();
		Log.d("package name", PacStr);
		if (Appname != null) {
			startOtherApp(PacStr);
		}
	}

	private void getList() {
		mApps = getPackageManager().queryIntentActivities(this.getIntent(), 0);
		for (int i = 0; i < mApps.size(); i++) {
			info = mApps.get(i);
			String appLabel = info.loadLabel(getPackageManager()).toString();
			String packagename = info.activityInfo.packageName;
			String appname = info.activityInfo.name;
			hashmap.put(appLabel.toLowerCase(), packagename);
		}
	}

	private void startOtherApp(String AppPackageStr) {
		Log.d("app", "begin app");
		Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(
				AppPackageStr);
		startActivity(LaunchIntent);
	}

	// /////// TTS ////////////
	private void setParams() {
		speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "1");
		speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "5");
		speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
		speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");
		speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE, "1");
		speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE, "4");
		// speechSynthesizer.setParam(SpeechSynthesizer.PARAM_LANGUAGE, "ZH");
		// speechSynthesizer.setParam(SpeechSynthesizer.PARAM_NUM_PRON, "0");
		// speechSynthesizer.setParam(SpeechSynthesizer.PARAM_ENG_PRON, "0");
		// speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PUNC, "0");
		// speechSynthesizer.setParam(SpeechSynthesizer.PARAM_BACKGROUND, "0");
		// speechSynthesizer.setParam(SpeechSynthesizer.PARAM_STYLE, "0");
		// speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TERRITORY, "0");
	}

	@Override
	public void onStartWorking(SpeechSynthesizer synthesizer) {
		// logDebug("开始工作，请等待数据...");
	}

	@Override
	public void onSpeechStart(SpeechSynthesizer synthesizer) {
		// logDebug("朗读开始");
	}

	@Override
	public void onSpeechResume(SpeechSynthesizer synthesizer) {
		// logDebug("朗读继续");
	}

	@Override
	public void onSpeechProgressChanged(SpeechSynthesizer synthesizer,
			int progress) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSpeechPause(SpeechSynthesizer synthesizer) {
		// logDebug("朗读已暂停");
	}

	@Override
	public void onSpeechFinish(SpeechSynthesizer synthesizer) {
		// logDebug("朗读已停止");
	}

	@Override
	public void onNewDataArrive(SpeechSynthesizer synthesizer,
			byte[] dataBuffer, int dataLength) {
		// logDebug("新的音频数据：" + dataLength);
	}

	@Override
	public void onError(SpeechSynthesizer synthesizer, SpeechError error) {
		// logError("发生错误：" + error.errorDescription + "(" + error.errorCode +
		// ")");
	}

	@Override
	public void onCancel(SpeechSynthesizer synthesizer) {
		// logDebug("已取消");
	}

	@Override
	public void onBufferProgressChanged(SpeechSynthesizer synthesizer,
			int progress) {
		// TODO Auto-generated method stub

	}

}