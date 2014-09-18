package thread.Test;
/**
 * @brief the constant string
 * 
 * @author zhuxiaolin
 */
import com.baidu.voicerecognition.android.VoiceRecognitionClient;

import android.os.Handler;

public class Constraints {
	
		///////  vr  ////
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
		
		////  handler
		public static Handler handler, mVoiceRecognitionerHandler;
		
		///  baidu API Key
	    public static final String API_KEY = "plsB3YLqYtjNqPxsMRBpNywS";
	    public static final String SECRET_KEY = "NzMCBcGSTRovw3C7RPCiDcbWquNB7xl5";
}
