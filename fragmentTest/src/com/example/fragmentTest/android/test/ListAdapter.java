package com.example.fragmentTest.android.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.fragmentTest.R;

/**
 * List view adapter
 *
 * @author zhuxiaolin
 */
public class ListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Map<String, Object>> mData;

    public ListAdapter(Context context, List<Map<String, Object>> om) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = om;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_list, null);
            holder.title = (TextView) convertView.findViewById(R.id.title); // item title ( sms content)
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        holder.img.setBackgroundResource((Integer) mData.get(position).get("img"));
        holder.title.setText((String) mData.get(position).get("title"));
        holder.info.setText((String) mData.get(position).get("info"));

        holder.viewBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                showInfo();
            }
        });
        return convertView;
    }

    public final class ViewHolder{
        public ImageView img;
        public TextView title;
        public TextView info;
        public Button viewBtn;
    }
}