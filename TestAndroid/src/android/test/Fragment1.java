package android.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Fragment1 extends ListFragment {

	private LayoutInflater mInflater;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		String[] array = new String[] { "C", "C++", "Java" };
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, array));
		
//		ListView listView=null;  
//		ListAdapter listAdapter ;
//		listView = (ListView) Inflater.
//        adapter = new SimpleAdapter(getActivity(), getData(list), 
//        		R.layout.item_list, new String[]{"title"}, new int[]{R.id.title});
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}
	
    private List<Map<String, Object>> getData3() {  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
  
        Map<String, Object> map = new HashMap<String, Object>();  
        map.put("title", "阿迪达斯 时尚运动鞋 特价 促销中");   
        list.add(map);  
  
        return list;  
    }  

}
