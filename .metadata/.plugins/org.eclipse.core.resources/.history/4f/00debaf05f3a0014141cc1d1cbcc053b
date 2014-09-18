package thread.Test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 工作提示fragment
 * 
 * @author zhnagjian
 */
public class UserFragment extends Fragment {

    private View mStatusPanel;

    private TextView mStatusView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_prompt_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        //mStatusPanel = root.findViewById(R.id.status_panel);
        mStatusView = (TextView) root.findViewById(R.id.textView1);
    }
    
    public void SetPromptString(int id)
    {
    	mStatusView.setText(id);
    }
    
    public void SetFragmentVisiblity(boolean flag)
    {
    	if ( true == flag ){
    		mStatusView.setVisibility(View.VISIBLE);
    	}
    	else{
    		mStatusView.setVisibility(View.GONE);
    	}
    }
   
}
