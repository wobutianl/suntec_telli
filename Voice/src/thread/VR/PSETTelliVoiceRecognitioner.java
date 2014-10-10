/**
 * @file PSETTelliVoiceRecognitioner.java
 * 
 * @brief Source File of Voice Recognition. 
 * 
 *    In the file, we use the Voice Recognition SDK of BaiDu. In addition, we define a independent thread 
 * to do the recognition work.
 */
package thread.VR;

import com.baidu.voicerecognition.android.Candidate;
import com.baidu.voicerecognition.android.DataUploader;
import com.baidu.voicerecognition.android.VoiceRecognitionClient;
import com.baidu.voicerecognition.android.VoiceRecognitionClient.VoiceClientStatusChangeListener;
import com.baidu.voicerecognition.android.VoiceRecognitionConfig;

import android.os.Handler;
import android.os.Message;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;


import java.util.List;

import thread.Test.Constraints;
import thread.Test.R;
import thread.Test.ThreadTestActivity;

/**
 * @brief Class of Voice Recognition Thread.     
 *
 *The class implements three functions:
 *(1)configure the Voice Recognition Engine and then start it.
 *(2)implement the VoiceRecognitionListener.
 *(3)Communicate with the MainThread.
 */ 
public class PSETTelliVoiceRecognitioner extends Thread { 
    private boolean isRecognition = false;    ///< recognition flag 

    private static final int POWER_UPDATE_INTERVAL = 100;    ///< interval of refresh volume

    private PSETTelliVoiceRecogListener mListener = new PSETTelliVoiceRecogListener();    ///< recognition listener 
    
    private Message toClient;    ///< message for communication with mainthread 
	
	
    /**
    * @brief run function of the thread
    *
    *the function configure the recognition engine and start it
    *
    * @param none            
    *
    * @return none     
    */ 
	public void run() {
        this.setName("VoiceRecognitionerThread");
        System.out.println("recogthread is running!");
		
        Looper.prepare();
		
		Constraints.mVoiceRecognitionerHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
				    case Constraints.USER_START_SPEECH:
				    	VoiceRecognitionConfig config = new VoiceRecognitionConfig();
				    	config.setProp(Config.CURRENT_PROP);
				        config.setLanguage(Config.getCurrentLanguage());
				        config.enableContacts(); // 启用通讯录
				        config.enableVoicePower(Config.SHOW_VOL); // 音量反馈。
				        if (Config.PLAY_START_SOUND) {
				            config.enableBeginSoundEffect(R.raw.bdspeech_recognition_start); // 设置识别开始提示音
				        }
				        if (Config.PLAY_END_SOUND) {
				            config.enableEndSoundEffect(R.raw.bdspeech_speech_end); // 设置识别结束提示音
				        }
				        config.setSampleRate(VoiceRecognitionConfig.SAMPLE_RATE_8K); // 设置采样率,需要与外部音频一致
				    	
						// 下面发起识别
                        int code = Constraints.mASREngine.startVoiceRecognition(mListener, config);
                        if (code != VoiceRecognitionClient.START_WORK_RESULT_WORKING) {
                            //向Client主线程报告错误
                        } 
                        break;
					case Constraints.USER_CANCEL_SPEECH:
						// 取消识别过程
						Constraints.mASREngine.stopVoiceRecognition();
                        break; 
					case Constraints.USER_FINISH_SPEECH:
						// 说话完成
						Constraints.mASREngine.speakFinish();
                        break;
                    default:
                        break;						
				}
            }

        };
		//准备接收消息
		Looper.loop();
		System.out.println("to the run() end!");
	}
	
	/**
	 * @brief Inner class of recognition class.     
	 *
	 *The class implement the recognition listener and communicate with the main thread.
	 */ 
    class PSETTelliVoiceRecogListener implements VoiceClientStatusChangeListener {

    	/**
    	* @brief listen the status of recognition engine and communicate with mainthread
    	*
    	*
    	*
    	* @param status status of recognition engine      
    	* @param object carry the recognition result    
    	*
    	* @return none     
    	*/
        public void onClientStatusChange(int status, Object obj) {
        	toClient = Constraints.handler.obtainMessage();
            switch (status) {
            // 语音识别实际开始，这是真正开始识别的时间点，需在界面提示用户说话。
                case VoiceRecognitionClient.CLIENT_STATUS_START_RECORDING:
                    isRecognition = true;
					toClient.what = Constraints.RECOGNITION_IS_READY;
					Constraints.handler.sendMessage(toClient);
                    break;
                case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_START: // 检测到语音起点
                	System.out.println("engine detect the start of speech");
                	toClient.what = Constraints.RECOGNITION_SPEECH_START;
                	Constraints.handler.sendMessage(toClient);
					System.out.println("engine detect the start of speech,and notify client");
					
                    break;
                // 已经检测到语音终点，等待网络返回
                case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_END:
                    toClient.what = Constraints.RECOGNITION_SPEECH_END;
                    Constraints.handler.sendMessage(toClient);
					System.out.println("engine detect the end of speech,and notify client");
                    break;
                // 语音识别完成，显示obj中的结果
                case VoiceRecognitionClient.CLIENT_STATUS_FINISH:
                    isRecognition = false;
					toClient.what = Constraints.RECOGNITION_RECOGNITION_FINISH;
					toClient.obj = updateRecognitionResult(obj);
					Constraints.handler.sendMessage(toClient);
					System.out.println("engine detect the finish of recognition,and notify client");
                    break;
                // 处理连续上屏
                case VoiceRecognitionClient.CLIENT_STATUS_UPDATE_RESULTS:
                    toClient.what = Constraints.RECOGNITION_RECOGNITION_PARTIALFINISH;
					toClient.obj = updateRecognitionResult(obj);
					Constraints.handler.sendMessage(toClient);
                    break;
                // 用户取消
                case VoiceRecognitionClient.CLIENT_STATUS_USER_CANCELED:
                    toClient.what = Constraints.RECOGNITION_RECOGNITION_CANCELED;
                    Constraints.handler.sendMessage(toClient);
                    isRecognition = false;
                    break;
                default:
                    break;
            }

        }

        /**
    	* @brief receive the error returned by recognition engine and notify the mainthread
    	*
    	*
    	*
    	* @param errorType type of error      
    	* @param errorCode code of error    
    	*
    	* @return none     
    	*/
        public void onError(int errorType, int errorCode) {
            isRecognition = false;
            
            toClient = Constraints.handler.obtainMessage();
            toClient.what = Constraints.RECOGNITION_RECOGNITION_ERROR;
			toClient.obj = Integer.toHexString(errorCode);
			Constraints.handler.sendMessage(toClient);
        }

        @Override
        public void onNetworkStatusChange(int status, Object obj) {
            // 这里不做任何操作不影响简单识别
        }
    }

    /**
    * @brief return the recognition result
    *
    *
    *
    * @param result recognition result     
    *
    * @return String result of recognition     
    */
    private String updateRecognitionResult(Object result) {
    	String retString = new String();
        if (result != null && result instanceof List) {
            List results = (List) result;
            if (results.size() > 0) {
                if (results.get(0) instanceof List) {
                    List<List<Candidate>> sentences = (List<List<Candidate>>) result;
                    StringBuffer sb = new StringBuffer();
                    for (List<Candidate> candidates : sentences) {
                        if (candidates != null && candidates.size() > 0) {
                            sb.append(candidates.get(0).getWord());
                        }
                    }
                    retString = (sb.toString());
                } else {
                	retString = (results.get(0).toString());
                }
            }
        }
        
        return retString;
    }

}
