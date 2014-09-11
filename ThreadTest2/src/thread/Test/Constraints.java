package thread.Test;

import android.util.Log;

public class Constraints {

		public static final int dataMsg = 0;
		public static final int ConServerMsg = 1;
		public static final int ServerMsg = 2;
		public static final int ReceServerMsg = 3;
		public static final int VoiceMsg = 4;
		public static final int UIMsg = 5;
		
		private static String testString = "please add sth : ";

		public static String getTestString() {
			return testString;
		}

		public static void setTestString(String testString) {
			Constraints.testString = Constraints.testString + testString;
			Log.d("String", testString);
		}
		
		
}
