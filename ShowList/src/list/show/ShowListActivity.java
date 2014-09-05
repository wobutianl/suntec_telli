package list.show;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.view.View;  
import android.view.ContextMenu.ContextMenuInfo;  


public class ShowListActivity extends Activity {
    /** Called when the activity is first created. */
	
	
	ListView list ;  // ListView控件  
	Button sendBtn;
	Button resBtn;  
	EditText sendText;
	EditText resText;
	
	SimpleAdapter listItemAdapter;  // ListView的适配器  
	SimpleAdapter resAdapter;  // ListView的适配器  
	ArrayList<HashMap<String, Object>> listItem ;  // ListView的数据源，这里是一个HashMap的列表  
	ArrayList<HashMap<String, Object>> resItem ; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        list = (ListView)findViewById(R.id.listView1);
        sendBtn = (Button)findViewById(R.id.sendBtn);
        resBtn = (Button)findViewById(R.id.resBtn);
        sendText = (EditText)findViewById(R.id.sendText);
        resText = (EditText)findViewById(R.id.resText);
        
      //生成动态数组，加入数据  
        listItem = new ArrayList<HashMap<String, Object>>();
        
        //生成适配器的Item和动态数组对应的元素  
        listItemAdapter = new SimpleAdapter(this,listItem,//数据源   
            R.layout.receivelist,//ListItem的XML实现  
            //动态数组与ImageItem对应的子项          
            new String[] {"ItemImage","ItemTitle", "ItemText"},   
            //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
            new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}  
        );  
         
        //添加并且显示  
        list.setAdapter(listItemAdapter);  
          
        //添加点击  
        list.setOnItemClickListener(new OnItemClickListener() {  
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {  
                setTitle("点击第"+arg2+"个项目");  
            }  
        });
        
        //添加长按点击  
        list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {  
              
            @Override  
            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
                menu.setHeaderTitle("长按菜单-ContextMenu");     
                menu.add(0, 0, 0, "弹出长按菜单0");  
                menu.add(0, 1, 0, "弹出长按菜单1");     
            }  
        }); 
        
        resItem = new ArrayList<HashMap<String, Object>>(); 
	      //生成适配器的Item和动态数组对应的元素  
	      resAdapter = new SimpleAdapter(this,resItem,//数据源   
	          R.layout.receivelist,//ListItem的XML实现  
	          //动态数组与ImageItem对应的子项          
	          new String[] {"ItemImage","ItemTitle", "ItemText"},   
	          //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
	          new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}  
      );  
       
      //添加并且显示  
      list.setAdapter(listItemAdapter);  
          
  
        
        sendBtn.setOnClickListener(sendlistener);//设置监听
    }  
      
    //长按菜单响应函数  
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
        setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目");   
        return super.onContextItemSelected(item);  
    }  
    
    private void addItem(String str)  
    {  
	    HashMap<String, Object> map = new HashMap<String, Object>();  
	    map.put("ItemImage", R.drawable.icon);  
	    map.put("ItemTitle", "标题");  
	    map.put("ItemText", str);  
	    listItem.add(map);  
	    Log.d("map",map.get("ItemText").toString());
	    listItemAdapter.notifyDataSetChanged();  
    }  
      
    private void deleteItem()  
    {  
	    int size = listItem.size();  
	    if( size > 0 )  
	    {  
	    	listItem.remove(listItem.size() - 1);  
		    listItemAdapter.notifyDataSetChanged();  
	    }  
    }  
    
    Button.OnClickListener sendlistener = new Button.OnClickListener(){//创建监听对象    
        public void onClick(View v){    
        	String sendStr = sendText.getText().toString();
        	//Log.d("str",sendStr);
        	if (sendStr != null){
        		Log.d("str",sendStr);
        		addItem(sendStr);

        		sendText.setText("");
        	}   
        }    
  
    }; 
    
    public void Btn3OnClick(View view){    
        //String strTmp="点击Button03";  
        //Ev1.setText(strTmp);  
    	String sendStr = sendText.getText().toString();
    	if (sendStr != null){
    		addItem(sendStr);
    	}
    }    
}