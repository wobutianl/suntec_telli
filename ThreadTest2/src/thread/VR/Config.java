
package thread.VR;

import com.baidu.voicerecognition.android.VoiceRecognitionConfig;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;

/**
 * 涓存椂淇濆瓨鍙傛暟淇℃伅锛孌emo婕旂ず浣跨敤銆傚紑鍙戣�涓嶉』鍏虫敞
 * 
 * @author yangliang02
 */
public class Config {
    /** 瀵硅瘽妗嗘牱寮�*/
    public static int DIALOG_THEME = BaiduASRDigitalDialog.THEME_BLUE_LIGHTBG;

    /**
     * 褰撳墠璇嗗埆璇█
     */
    public static String CURRENT_LANGUAGE = VoiceRecognitionConfig.LANGUAGE_CHINESE;

    private static int CURRENT_LANGUAGE_INDEX = 0;
    
    /**
     * 褰撳墠鍨傜洿棰嗗煙绫诲瀷
     */
    public static int CURRENT_PROP = VoiceRecognitionConfig.PROP_INPUT;
    
    private static int CURRENT_PROP_INDEX = 0;

    public static String getCurrentLanguage() {
        return CURRENT_LANGUAGE;
    }

    public static int getCurrentLanguageIndex() {
        return CURRENT_LANGUAGE_INDEX;
    }

    public static void setCurrentLanguageIndex(int index) {
        switch (index) {
            case 1:
                CURRENT_LANGUAGE = VoiceRecognitionConfig.LANGUAGE_CANTONESE;
                break;
            case 2:
                CURRENT_LANGUAGE = VoiceRecognitionConfig.LANGUAGE_ENGLISH;
                break;

            default:
                CURRENT_LANGUAGE = VoiceRecognitionConfig.LANGUAGE_CHINESE;
                index = 0;
                break;
        }
        CURRENT_LANGUAGE_INDEX = index;
    }
    
    public static int getCurrentPropIndex() {
        return CURRENT_PROP_INDEX;
    }

    public static void setCurrentPropIndex(int index) {
        switch (index) {
	        case 1:
	        	CURRENT_PROP = VoiceRecognitionConfig.PROP_SEARCH;
	            break;
	        case 2:
	        	CURRENT_PROP = VoiceRecognitionConfig.PROP_MAP;
	            break;
	        case 3:
	        	CURRENT_PROP = VoiceRecognitionConfig.PROP_MUSIC;
	            break;
	        case 4:
	        	CURRENT_PROP = VoiceRecognitionConfig.PROP_VIDEO;
	            break;
	        case 5:
	        	CURRENT_PROP = VoiceRecognitionConfig.PROP_APP;
	        	break;
	        case 6:
	        	CURRENT_PROP = VoiceRecognitionConfig.PROP_WEB;
	        	break;
	        case 7:
                CURRENT_PROP = VoiceRecognitionConfig.PROP_HEALTH;
                break;
	        case 8:
                CURRENT_PROP = VoiceRecognitionConfig.PROP_SHOPPING;
                break;
	        case 9:
                CURRENT_PROP = VoiceRecognitionConfig.PROP_PHONE;
                break;
	        default:
	        	CURRENT_PROP = VoiceRecognitionConfig.PROP_INPUT;
	        	index = 0;
	            break;
        }
        CURRENT_PROP_INDEX = index;
    }

    /**
     * 鎾斁寮�闊�     */
    public static boolean PLAY_START_SOUND = true;

    /**
     * 鎾斁缁撴潫闊�     */
    public static boolean PLAY_END_SOUND = true;
    
    /**
     * 瀵硅瘽妗嗘彁绀洪煶
     */
    public static boolean DIALOG_TIPS_SOUND = true;

    /**
     * 鏄剧ず闊抽噺
     */
    public static boolean SHOW_VOL = true;

}
