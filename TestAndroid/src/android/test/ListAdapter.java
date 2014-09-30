package android.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * List view adapter 
 * @author zhuxiaolin
 *
 */
public class ListAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
	// private List<Object> items;
//	private String section;
	private List<Map<String, Object>> mData;
//	private ArrayList mData = new ArrayList();


	public ListAdapter(Context context, List<Map<String, Object>> mData) {
		mInflater = LayoutInflater.from(context);
		// items = new ArrayList<Object>();
//		this.section = section;
		this.mData = mData;
	}
	
	private Context context;  
    public ListAdapter(Context context)  
    {  
    	mInflater = LayoutInflater.from(context);
//        this.context = context;  
    }  

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void addItems(List<Object> list) {
		if (list != null && list.size() > 0) {
			// items.add(list);
			notifyDataSetChanged();
		}
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("getView " + position + " " + convertView);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_list, null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(mData.get(position).toString());
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
	}


}