/**********Get URL**********/
package thread.pSrc;

import java.net.*;

public class BlockURL 
{
	private String StrURL;

	public BlockURL(URLMsg UMsg)
	{
		StrURL = "http://navicloud.pset.suntec.net:8080/api/dialog-svn/3.0/ch-trunk-autonavi/dialog/";
		
		if("p_start" == UMsg.Msg_Flag && "0" == UMsg.Msg_DID)		//did type uid
		{
			StrURL += "msg?did=0&type=start&uid=test";
		}
		if("p_stop" == UMsg.Msg_Flag)								//did type uid sid
		{
			StrURL += "msg?did=";
			StrURL += UMsg.Msg_DID;
			StrURL += "&type=stop&uid=test&sid=";
			StrURL += UMsg.Msg_SID;
		}
		if("p_vr" == UMsg.Msg_Flag)								//q did type sid
		{
			StrURL += "rsp?q=";
			String qtext = UMsg.Msg_STT;
			try
			{
				qtext = URLEncoder.encode(qtext, "utf-8");
			}
	         catch(Exception e)
	         {
	             System.out.println(e);
	         }
			StrURL += qtext;
			StrURL += "&did=";
			StrURL += UMsg.Msg_DID;
			StrURL += "&type=vr&sid=";
			StrURL += UMsg.Msg_SID;
		}
	}
	public String GetStrURL()
	{
		return StrURL;
	}
}
