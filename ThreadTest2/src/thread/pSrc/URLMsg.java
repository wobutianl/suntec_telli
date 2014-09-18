/****Used as a structure******/
package thread.pSrc;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class URLMsg
{
	public String Msg_DID;
	public String Msg_STT;
	public String Msg_Flag;
	public String Msg_SID;
	
	public URLMsg()
	{
		Msg_DID = "Telli";
		Msg_STT = "Telli";
		Msg_Flag = "Telli";
		Msg_SID = "Telli";
	}
	
	public void ShowMsg()
	{
		System.out.println(Msg_DID);
		System.out.println(Msg_STT);
		System.out.println(Msg_Flag);
		System.out.println(Msg_SID);
	}
	
	public String getMsg_DID() {
		return Msg_DID;
	}

	public void setMsg_DID(String msg_DID) {
		Msg_DID = msg_DID;
	}

	public String getMsg_STT() {
		return Msg_STT;
	}

	public void setMsg_STT(String msg_STT) {
		Msg_STT = msg_STT;
	}

	public String getMsg_Flag() {
		return Msg_Flag;
	}

	public void setMsg_Flag(String msg_Flag) {
		Msg_Flag = msg_Flag;
	}

	public String getMsg_SID() {
		return Msg_SID;
	}

	public void setMsg_SID(String msg_SID) {
		Msg_SID = msg_SID;
	}
}
