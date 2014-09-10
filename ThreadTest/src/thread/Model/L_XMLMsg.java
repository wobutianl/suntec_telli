package thread.Model;

import thread.Test.ModelMsg;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class L_XMLMsg implements Parcelable {

	private String did;
	private String sid;
	private String display;
	
	private String tts;
	private String type;
	private String flag;
	
	private String appName;
	
	public L_XMLMsg() {
		// TODO Auto-generated constructor stub
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getTts() {
		return tts;
	}

	public void setTts(String tts) {
		this.tts = tts;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(did);
		out.writeString(sid);
		out.writeString(display);
		
		out.writeString(tts);
		out.writeString(flag);
		out.writeString(type);
		
		out.writeString(appName);
	}

	public static final Parcelable.Creator<L_XMLMsg> CREATOR = new Creator<L_XMLMsg>() {
		@Override
		public L_XMLMsg[] newArray(int size) {
			return new L_XMLMsg[size];
		}

		@Override
		public L_XMLMsg createFromParcel(Parcel in) {
			return new L_XMLMsg(in);
		}
	};

	public L_XMLMsg(Parcel in) {
		did = in.readString();
		sid = in.readString();
		display = in.readString();
		
		tts = in.readString();
		flag = in.readString();
		type = in.readString();
		
		appName = in.readString();
	}
}
