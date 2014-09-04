package list.show;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MsgAdapter {

	private List<Message> students; //�󶨵����ݼ�
	private int source; //��Դ�ļ�
	private LayoutInflater inflater; //�����������Android�����÷������ã�ʹ��xml�ļ������ɶ�Ӧ��view����

	public MsgAdapter(Context context,List<Message> students , int source){
		this.students = students;
		this.source = source;
		//�õ�����������
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

	//ȡ�ô�����Ŀ��view����
	/* (non-Javadoc)
	* @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	* arg0 ��ǰ��Ŀ��Ҫ�󶨵���������Ŀ�е�����ֵ
	*
	*/
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		TextView idview = null;
		TextView nameview = null;
		TextView ageview = null;

		// TODO Auto-generated method stub
		//�ж��Ƿ�Ϊ��һҳ
		//�ṩ�������
		if(arg1 == null){
			//Ϊ��Ŀ����View����,������Ŀ�������
			arg1 = inflater.inflate(source, null);
		
			//�õ���ǰ��Ŀ������
			idview = (TextView)arg1.findViewById(R.id.id);
			nameview = (TextView)arg1.findViewById(R.id.name);
		
			ViewCache cache = new ViewCache();
		
			cache.id = idview;
			cache.name = nameview;
			cache.age = ageview;
		
			//����ͼ��ʶ��ʱ��Ż�������
			arg1.setTag(cache);
		}
		else{
			ViewCache cache = (ViewCache)arg1.getTag();
			idview = cache.id;
			nameview = cache.name;
			ageview = cache.age;
		}
	
		//�õ���ǰ��Ŀ����
		Message stu = students.get(arg0);
	
		//Ϊ��ǰ��Ŀ��ֵ
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
