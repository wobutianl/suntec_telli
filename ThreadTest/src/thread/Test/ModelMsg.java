package thread.Test;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelMsg implements Parcelable {

	// private static final long serialVersionUID = 1L; // ???

	private int MsgID;
	private String MsgStr;
	
	
	public ModelMsg() {
		// TODO Auto-generated constructor stub
	}

	public int getMsgID() {
		return MsgID;
	}

	public void setMsgID(int msgID) {
		MsgID = msgID;
	}

	public String getMsgStr() {
		return MsgStr;
	}

	public void setMsgStr(String msgStr) {
		MsgStr = msgStr;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(MsgStr);
		out.writeInt(MsgID);
	}

	public static final Parcelable.Creator<ModelMsg> CREATOR = new Creator<ModelMsg>() {
		@Override
		public ModelMsg[] newArray(int size) {
			return new ModelMsg[size];
		}

		@Override
		public ModelMsg createFromParcel(Parcel in) {
			return new ModelMsg(in);
		}
	};

	public ModelMsg(Parcel in) {
		MsgStr = in.readString();
		MsgID = in.readInt();
	}
}
