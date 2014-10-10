package com.example.myapp.View;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapp.DB.GtdTable;
import com.example.myapp.DataParse.smsDataParse;
import com.example.myapp.Module.gtdContent;
import com.example.myapp.Module.smsContent;
import com.example.myapp.MyActivity;
import com.example.myapp.R;

public class Fragment2 extends Fragment {

    private Button insertBtn;
    private EditText content;
    private smsDataParse parse;
    private gtdContent gtdcontent;
    private Context ctx ;

    public Fragment2(Context context){
        this.ctx = context;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflateAndSetupView(inflater, container, savedInstanceState,
				R.layout.sms_content_list);
	}


	private View inflateAndSetupView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState, int layoutResourceId) {
		View layout = inflater.inflate(layoutResourceId, container, false);

		return layout;
	}

    @Override
    public void onResume()
    {
        super.onResume();
        System.out.println("LeftFragment--->onResume");

        insertBtn = (Button) getActivity().findViewById(R.id.sms_btn);
        content = (EditText)getActivity().findViewById(R.id.sms_content_edit);
        MyButtonClickListener clickListener = new MyButtonClickListener();
        insertBtn.setOnClickListener(clickListener);
    }

    /** 按钮的监听器 */
    class MyButtonClickListener implements View.OnClickListener
    {
        public void onClick(View v)
        {
            Button button = (Button) v;
            if (button == insertBtn){
                System.out.println(" insert Button ");
                GtdTable table = new GtdTable(ctx);
                System.out.println(" table ");
                String str = content.getText().toString();
                System.out.println(str);
                gtdcontent = new gtdContent();
                parse = new smsDataParse();
                gtdcontent = parse.parse(str);
                System.out.println(" gtd " + gtdcontent.getContent().toString());
                table.insert(gtdcontent);

                content.setText("");
            }
        }
    }

}
