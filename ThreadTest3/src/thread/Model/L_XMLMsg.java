package thread.Model;

import thread.Test.ModelMsg;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class L_XMLMsg  {

	private String did;
	private String sid;
	private String display;
	
	private String tts;
	private String type;
	private String content;
	
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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
