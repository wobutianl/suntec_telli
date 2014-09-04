package list.show;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MsgAdapter {

	private List<Message> students; //绑定的数据集
	private int source; //资源文件
	private LayoutInflater inflater; //布局填充器，Android的内置服务，作用：使用xml文件来生成对应的view对象

	public MsgAdapter(Context context,List<Message> students , int source){
		this.students = students;
		this.source = source;
		//得到布局填充服务
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return students.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return students.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	//取得代表条目的view对象
	/* (non-Javadoc)
	* @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	* arg0 当前条目所要绑定的数据在条目中的索引值
	*
	*/
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		TextView idview = null;
		TextView nameview = null;
		TextView ageview = null;

		// TODO Auto-generated method stub
		//判断是否为第一页
		//提供缓存机制
		if(arg1 == null){
			//为条目创建View对象,生成条目界面对象
			arg1 = inflater.inflate(source, null);
		
			//得到当前条目的数据
			idview = (TextView)arg1.findViewById(R.id.id);
			nameview = (TextView)arg1.findViewById(R.id.name);
		
			ViewCache cache = new ViewCache();
		
			cache.id = idview;
			cache.name = nameview;
			cache.age = ageview;
		
			//用视图标识临时存放缓存数据
			arg1.setTag(cache);
		}
		else{
			ViewCache cache = (ViewCache)arg1.getTag();
			idview = cache.id;
			nameview = cache.name;
			ageview = cache.age;
		}
	
		//得到当前条目对象
		Message stu = students.get(arg0);
	
		//为当前条目赋值
		idview.setText(stu.getId().toString());
		nameview.setText(stu.getMessage().toString());
		//ageview.setText(stu.getAge().toString());
	
		return arg1;
	}

	private final class ViewCache{
		public TextView id;
		public TextView name;
		public TextView age;
	}
	
	
}
