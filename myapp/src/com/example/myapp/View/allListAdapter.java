package com.example.myapp.View;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.myapp.Module.gtdContent;
import com.example.myapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * List fragment adapter
 * Created by zhuxiaolin on 2014/10/8.
 */
public class allListAdapter extends BaseAdapter {

    private List<gtdContent> data = null;
    private Context ctx;
    private gtdContent gtd = new gtdContent();
    private LayoutInflater inflater;

    /**
     * 构造方法
     *
     * @param ctx
     * @param data
     */
    public allListAdapter(Context ctx, List<gtdContent> data) {
        this.ctx = ctx;
        this.data = data;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 初始化组件
        Component c = new Component();
        if (convertView == null) {
            c = new Component();
            convertView = inflater.inflate( R.layout.all_list_view, null);
            c.tv = (TextView) convertView.findViewById(R.id.sms_content_tv);
            convertView.setTag(c);
        } else {
            c = (Component) convertView.getTag();
        }
        // 给组件数据
        c.tv.setText( data.get(position).getContent());
        // 返回数据
        return convertView;
    }

    /**
     * inner class
     *
     * @author apple
     *
     */
    class Component {
        public TextView tv;
    }
}

