/****Fetch XML file from server ****/
package thread.pSrc;

import java.net.*;
import java.io.*;

import android.util.Log;

public class CallServer
{
	private String Str_XML;
	
    public CallServer(String StrURL)
    {
    	Str_XML = "Telli";
    	
        try
        {
        	
            URL t_url = new URL(StrURL);
            BufferedReader t_in = new BufferedReader(new InputStreamReader(t_url.openStream(),"utf-8"));
             
            Str_XML = t_in.readLine();
            
            Log.d("call server", Str_XML);
            t_in.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public String GetStrXML()
    {
    	return Str_XML;
    }
}

