/**
 * @file l_xmlMsg
 * 
 * @brief a message module parsed from xml 
 * 
 * an data module which parsed from xml
 */

package thread.Model;
/**
 *  
 * @brief a message module parsed from xml 
 * 
 * @author linbing
 */
public class L_XMLMsg  {

	private String did;   ///< an did 
	private String sid;		///< an ID for each dialog 
	private String display;	///< string for UI display
	
	private String tts;	///< string for TTS 
	private String type;	///< start or vr or stop 
	
	private String appName;	///< the name of App Name
	
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

}
