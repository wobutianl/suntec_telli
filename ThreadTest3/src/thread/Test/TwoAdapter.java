package thread.Test;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class TwoAdapter extends BaseAdapter {

	private Context context = null;
	private List<ChatEntity> chatList = null;
	private LayoutInflater inflater = null;
	private int COME_MSG = 0;
	private int TO_MSG = 1;

	public TwoAdapter(Context context, List<ChatEntity> chatList) {
		this.context = context;
		this.chatList = chatList;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return chatList.size();
	}

	@Override
	public Object getItem(int position) {
		return chatList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		// ���}��view�����ͣ���ע}��ͬ�ı�4�ֱ��ʾ���Ե�����
		ChatEntity entity = chatList.get(position);
		if (entity.isComeMsg()) {
			return COME_MSG;
		} else {
			return TO_MSG;
		}
	}

	@Override
	public int getViewTypeCount() {
		// ����Ĭ�Ϸ���1�����ϣ��listview��item����һ��ľͷ���1������������}�ַ�񣬷���2
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatHolder chatHolder = null;
		if (convertView == null) {
			chatHolder = new ChatHolder();
			if (chatList.get(position).isComeMsg()) {
				convertView = inflater.inflate(R.layout.receivelist, null);
			} else {
				convertView = inflater.inflate(R.layout.sendlist, null);
			}
			chatHolder.timeTextView = (TextView) convertView
					.findViewById(R.id.tv_time);
			chatHolder.contentTextView = (TextView) convertView
					.findViewById(R.id.tv_content);
			chatHolder.userImageView = (ImageView) convertView
					.findViewById(R.id.iv_user_image);
			convertView.setTag(chatHolder);
		} else {
			chatHolder = (ChatHolder) convertView.getTag();
		}

		chatHolder.timeTextView.setText(chatList.get(position).getChatTime());
		chatHolder.contentTextView.setText(chatList.get(position).getContent());
		chatHolder.userImageView.setImageResource(chatList.get(position)
				.getUserImage());

		return convertView;
	}

	private class ChatHolder {
		private TextView timeTextView;
		private ImageView userImageView;
		private TextView contentTextView;
	}

}
