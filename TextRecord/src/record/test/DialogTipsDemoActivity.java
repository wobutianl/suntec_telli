
package record.test;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * 鐠囧棗鍩嗙�纭呯樈濡楀棙褰佺粈楦款嚔Demo閵嗗倿锟芥潻锟絳@link BaiduASRDigitalDialog#getParams()}閼惧嘲褰囬崚鎷岀槕閸掝偄寮弫锟界拫鍐暏
 * Bundle.putStringArray({@link BaiduASRDigitalDialog#PARAM_TIPS}, String[])
 * 閺傝纭剁拋鍓х枂閹绘劗銇氱拠顓炲灙鐞涖劊锟界拋鍓х枂閹绘劗銇氱拠顓炴倵閿涘苯婀拠鍡楀焼鐎电鐦藉鍡椾箯娑撳﹨顬戞导姘箒閳ユ粣绱甸垾婵囧瘻闁筋噯绱濋悙鐟板毊閸氬孩妯夌粈鐑樺絹缁�缚顕㈤崚妤勩�閵嗗倽顔曠純顔藉絹缁�缚顕㈤敍宀冪箷閸欘垯浜掗柅姘崇箖
 * {@link Bundle#putBoolean(String, boolean)}閺傝纭剁拋鍓х枂娴犮儰绗呴崣鍌涙殶閿涘本澧﹀锟芥祲閸忓啿濮涢懗锟�* <table border="1">
 * <tr>
 * <th>閸欏倹鏆熼崥锟�th>
 * <th>閸欏倹鏆熺猾璇茬�</th>
 * <th>姒涙顓婚崐锟�th>
 * <th>閹诲繗鍫�/th>
 * </tr>
 * <tr>
 * <td>{@link BaiduASRDigitalDialog#PARAM_SHOW_TIPS_ON_START}</td>
 * <td>boolean</td>
 * <td>false</td>
 * <td>鐎电鐦藉鍡樻▔缁�儤妞傛稉宥呮儙閸斻劏鐦戦崚顐礉鐏炴洜銇氶幓鎰仛鐠囶厼鍨悰锟�td>
 * </tr>
 * <tr>
 * <td>{@link BaiduASRDigitalDialog#PARAM_SHOW_TIP}</td>
 * <td>boolean</td>
 * <td>false</td>
 * <td>鐠囧棗鍩嗛崥顖氬З3缁夋帒鎮楅張顏咁棏濞村鍩岀拠顓㈢叾閿涘苯婀棅铏櫏閸斻劎鏁炬稉瀣煙鐏炴洜銇氭稉锟芥蒋閹绘劗銇氱拠锟�td>
 * </tr>
 * <tr>
 * <td>{@link BaiduASRDigitalDialog#PARAM_SHOW_HELP_ON_SILENT}</td>
 * <td>boolean</td>
 * <td>false</td>
 * <td>閻㈠彉绨張顏咁棏濞村鍩岀拠顓㈢叾閼板苯绱撶敮鍝ョ波閺夌喐妞傞敍灞炬禌閹广垹褰囧☉鍫熷瘻闁筋喕璐熺敮顔煎И閹稿鎸抽敍宀�暏閹撮鍋ｉ崙璇叉倵鐏炴洜銇氶幓鎰仛鐠囶厼鍨悰锟�td>
 * </tr>
 * </table>
 * 
 * @author yangliang02
 */
public class DialogTipsDemoActivity extends PreferenceActivity {
    private static final String SHOW_TIPS_ONSTART = "dialog_show_tips_onstart";

    private static final String SHOW_TIP_ONSILENT = "dialog_show_tip_onsilent";

    private static final String SHOW_HELP_ONSILENT = "dialog_show_help_onsilent";

    private static final int RECOGNITION_DIALOG = 1;

    private static final String INTENT_ACTION_START = "baidu.voicedemo.intent.action.START";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PreferenceManager.setDefaultValues(getApplication(), R.xml.dialog_tips, false);
        //addPreferencesFromResource(R.xml.dialog_tips);
        startRecognition(getIntent());
    }

    private void startRecognition(Intent intent) {
        if (INTENT_ACTION_START.equals(intent.getAction())) {
            showDialog(RECOGNITION_DIALOG);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        startRecognition(intent);
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        if (id == RECOGNITION_DIALOG) {
            Bundle params = new Bundle();
            params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, Constants.API_KEY);
            params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY, Constants.SECRET_KEY);
            params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, Config.DIALOG_THEME);
            BaiduASRDigitalDialog mDialog = new BaiduASRDigitalDialog(this, params);
            mDialog.setDialogRecognitionListener(new DialogRecognitionListener() {

                @Override
                public void onResults(Bundle arg0) {
                    // TODO Auto-generated method stub

                }
            });
            return mDialog;
        }
        return super.onCreateDialog(id);
    }

    @Override
    @Deprecated
    protected void onPrepareDialog(int id, Dialog dialog) {
        if (id == RECOGNITION_DIALOG) {
            BaiduASRDigitalDialog mDialog = (BaiduASRDigitalDialog) dialog;
            // 璁剧疆鎻愮ず璇垪琛ㄣ�璁剧疆鎻愮ず璇悗锛屽湪璇嗗埆瀵硅瘽妗嗗乏涓婅浼氭湁鈥滐紵鈥濇寜閽紝鐐瑰嚮鍚庢樉绀烘彁绀鸿鍒楄〃銆�            mDialog.getParams().putStringArray(BaiduASRDigitalDialog.PARAM_TIPS,
                    getResources().getStringArray(R.array.command_tips);
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(getApplication());
            // 璁剧疆涓篢rue锛屽璇濇鏄剧ず鏃朵笉鍚姩璇嗗埆锛屽睍绀烘彁绀鸿鍒楄〃
            mDialog.getParams().putBoolean(BaiduASRDigitalDialog.PARAM_SHOW_TIPS_ON_START,
                    preferences.getBoolean(SHOW_TIPS_ONSTART, false));
            // 璁剧疆涓篢rue锛岃瘑鍒惎鍔�绉掑悗鏈娴嬪埌璇煶锛屽湪闊虫晥鍔ㄧ敾涓嬫柟灞曠ず涓�潯鎻愮ず璇�            mDialog.getParams().putBoolean(BaiduASRDigitalDialog.PARAM_SHOW_TIP,
                    preferences.getBoolean(SHOW_TIP_ONSILENT, false);
            // 璁剧疆涓篢rue锛屾湭妫�祴鍒拌闊宠�寮傚父缁撴潫鏃讹紝鏇挎崲鍙栨秷鎸夐挳涓哄府鍔╂寜閽紝鐢ㄦ埛鐐瑰嚮鍚庡睍绀烘彁绀鸿鍒楄〃銆�            mDialog.getParams().putBoolean(BaiduASRDigitalDialog.PARAM_SHOW_HELP_ON_SILENT,
                    preferences.getBoolean(SHOW_HELP_ONSILENT, false);
        }
        super.onPrepareDialog(id, dialog);
    }
}