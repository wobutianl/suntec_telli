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
	
	
	ListView list ;  // ListView�ؼ�  
	Button sendBtn;
	Button resBtn;  
	EditText sendText;
	EditText resText;
	
	SimpleAdapter listItemAdapter;  // ListView��������  
	ArrayList<HashMap<String, Object>> listItem ;  // ListView������Դ��������һ��HashMap���б�  
	 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        list = (ListView)findViewById(R.id.listView1);
        sendBtn = (Button)findViewById(R.id.sendBtn);
        resBtn = (Button)findViewById(R.id.resBtn);
        sendText = (EditText)findViewById(R.id.sendText);
        resText = (EditText)findViewById(R.id.resText);
        
      //���ɶ�̬���飬��������  
        listItem = new ArrayList<HashMap<String, Object>>();  
//        for(int i=0;i<10;i++)  
//        {  
//            HashMap<String, Object> map = new HashMap<String, Object>();  
//            map.put("ItemImage", R.drawable.icon);//ͼ����Դ��ID  
//            map.put("ItemTitle", "Level "+i);  
//            map.put("ItemText", "Finished in 1 Min 54 Secs, 70 Moves! ");  
//            listItem.add(map);  
//        }  
        //������������Item�Ͷ�̬�����Ӧ��Ԫ��  
        listItemAdapter = new SimpleAdapter(this,listItem,//����Դ   
            R.layout.receivelist,//ListItem��XMLʵ��  
            //��̬������ImageItem��Ӧ������          
            new String[] {"ItemImage","ItemTitle", "ItemText"},   
            //ImageItem��XML�ļ������һ��ImageView,����TextView ID  
            new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}  
        );  
         
        //���Ӳ�����ʾ  
        list.setAdapter(listItemAdapter);  
          
        //���ӵ��  
        list.setOnItemClickListener(new OnItemClickListener() {  
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {  
                setTitle("�����"+arg2+"����Ŀ");  
            }  
        });
        
        
          
      //���ӳ������  
        list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {  
              
            @Override  
            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
                menu.setHeaderTitle("�����˵�-ContextMenu");     
                menu.add(0, 0, 0, "���������˵�0");  
                menu.add(0, 1, 0, "���������˵�1");     
            }  
        });   
        
        sendBtn.setOnClickListener(sendlistener);//���ü���
    }  
      
    //�����˵���Ӧ����  
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
        setTitle("����˳����˵�����ĵ�"+item.getItemId()+"����Ŀ");   
        return super.onContextItemSelected(item);  
    }  
    
    private void addItem(String str)  
    {  
	    HashMap<String, Object> map = new HashMap<String, Object>();  
	    map.put("ItemImage", R.drawable.icon);  
	    map.put("ItemTitle", "����");  
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
    
    Button.OnClickListener sendlistener = new Button.OnClickListener(){//������������    
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
        //String strTmp="���Button03";  
        //Ev1.setText(strTmp);  
    	String sendStr = sendText.getText().toString();
    	if (sendStr != null){
    		addItem(sendStr);
    	}
    }    
}