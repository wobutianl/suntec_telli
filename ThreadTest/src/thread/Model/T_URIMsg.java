package thread.Model;

public class T_URIMsg {

	public String Msg_DID;
	public String Msg_STT;
	public String Msg_Flag;
	public String Msg_SID;
	
	public T_URIMsg()
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
	
}
