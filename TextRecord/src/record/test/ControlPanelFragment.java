
package record.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 璇煶鎺у埗闈㈡澘
 * 
 * @author yangliang02
 */
public class ControlPanelFragment extends Fragment {
    private static final int STATUS_IDLE = 1;

    public static final int STATUS_RECORDING_START = STATUS_IDLE << 1;

    public static final int STATUS_SPEECH_START = STATUS_IDLE << 2;

    public static final int STATUS_SPEECH_END = STATUS_IDLE << 3;

    public static final int STATUS_FINISH = STATUS_IDLE << 4;

    private int mStatus = STATUS_IDLE;

    private View mStatusPanel;

    private TextView mStatusView;

    private ProgressBar mVolumeBar;

    /** 鍙栨秷鎸夐挳 */
    private Button mCancelButton = null;

    /** 瀹屾垚鍜岄噸璇曟寜閽�*/
    private Button mFinishButton = null;

    private OnEventListener mEventListener;

    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mEventListener != null) {
                switch (v.getId()) {
                    case R.id.start:
                        switch (mStatus) {
                            case STATUS_IDLE:
                                if (mEventListener.onStartListening()) {
                                    mStatusPanel.setVisibility(View.VISIBLE);
                                    mFinishButton.setEnabled(false);
                                }
                                break;
                            case STATUS_SPEECH_START:
                            case STATUS_RECORDING_START:
                                if (mEventListener.onStopListening()) {
                                    mFinishButton.setEnabled(false);
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    case R.id.cancelButton:
                        if (mEventListener.onCancel()) {
                            mCancelButton.setEnabled(false);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.control_panel_fragment, container, true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        mStatusPanel = root.findViewById(R.id.status_panel);
        mStatusView = (TextView) root.findViewById(R.id.statusTextView);
        mVolumeBar = (ProgressBar) root.findViewById(R.id.volumeProgressbar);
        mCancelButton = (Button) root.findViewById(R.id.cancelButton);
        mCancelButton.setOnClickListener(mClickListener);
        mFinishButton = (Button) root.findViewById(R.id.start);
        mFinishButton.setOnClickListener(mClickListener);
    }

    public void statusChange(int status) {
        switch (status) {
            case STATUS_RECORDING_START:
                mFinishButton.setEnabled(true);
                mFinishButton.setText(R.string.tofinish);
                mCancelButton.setEnabled(true);
                mStatusView.setText(R.string.please_speak);
                mVolumeBar.setVisibility(View.VISIBLE);
                mStatus = STATUS_RECORDING_START;
                break;
            case STATUS_SPEECH_START:
                mStatusView.setText(R.string.speaking);
                mStatus = STATUS_SPEECH_START;
                break;
            case STATUS_SPEECH_END:
                mStatusView.setText(R.string.in_recog);
                mFinishButton.setEnabled(false);
                mVolumeBar.setVisibility(View.INVISIBLE);
                mStatus = STATUS_SPEECH_END;
                break;
            case STATUS_FINISH:
                mStatusView.setText(null);
                mCancelButton.setEnabled(false);
                mFinishButton.setEnabled(true);
                mFinishButton.setText(R.string.start);
                mStatusPanel.setVisibility(View.GONE);
                mStatus = STATUS_IDLE;
                break;
            default:
                break;
        }
    }

    public void setOnEventListener(OnEventListener listener) {
        this.mEventListener = listener;
    }

    public void volumeChange(int power) {
        mVolumeBar.setProgress(power);
    }

    /**
     * 闈㈡澘鎸夐挳浜嬩欢鐩戝惉
     * 
     * @author yangliang02
     */
    public interface OnEventListener {
        /**
         * 寮�鍊惧惉鎸夐挳浜嬩欢鍥炶皟
         * 
         * @return true鎴愬姛鍚姩銆�         */
        public boolean onStartListening();

        /**
         * 缁撴潫鍊惧惉鎸夐挳浜嬩欢鍥炶皟
         * 
         * @return true 鎴愬姛缁撴潫
         */
        public boolean onStopListening();

        /**
         * 鍙栨秷鎸夐挳浜嬩欢
         * 
         * @return true 鎴愬姛鍙栨秷
         */
        public boolean onCancel();
    }
}
