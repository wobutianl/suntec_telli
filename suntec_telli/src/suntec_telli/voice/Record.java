package suntec_telli.voice;



public class Record {

	
	/*
	 * Begin to Record 
	 * event by MainActivity
	 */
	public void BeginRecord(){
		
	}
	
	/*
	 * end the Record and return waveType
	 */
	public String EndRecord(){
		return "wave";
	}
	
	/*
	 * phrase wave to TXT 
	 */
	public String PhraseWave(String wave) {
		return "Txt";
	}
	
	/*
	 * get Voice Txt from Data
	 * 
	 */
	public String GetTxtFromData(){
		return "Voice Txt";
	}
	
	/*
	 * phrase txt to TTS
	 */
	public String PhraseTxt(String txt){
		return "TTS";
	}
	
	/*
	 * play TTS
	 */
	public void PlayTTS(String TTS){
		
	}
	
	/*
	 * finish play TTS 
	 */
	public void FinishTTS(String TTS){
		
	}
	
	/*
	 * Do task e.g:open app
	 */
	public void DoTask(String Task){
		
	}
	
    public static void main(String[] args){
       

    }

    // wave wma, or mp3
    private String wave;
    // made by wave
    private String txt;
    // record status
    private double record_status;
}
