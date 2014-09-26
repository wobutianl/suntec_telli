package msg.GTD;

import java.util.Calendar;
import java.util.regex.Pattern;

public class SmsParse {
	private static String sms = "今天，10点到12点，淮海中路，逛街";
	private static String noDaysms = "10点到12点，淮海中路，逛街";
	
	static String day;
	static String start;
	static String end;
	static String place;
	static String content;
	
	public static void splitSms(String str){
		Pattern pattern = Pattern.compile("[,，到]");
		String[] strs = pattern.split(str);
		for (int i = 0; i < strs.length; i++) {
			day = strs[0];

			start = strs[1];
			end = strs[2];
			place = strs[3];
			content = strs[4];
			System.out.println(strs[i]);

		}
	}

	private static int[] getDay() {
		int[] date = new int[2];
		int y, m, d, h, mi, s;
		Calendar cal = Calendar.getInstance();
//		y = cal.get(Calendar.YEAR);
		m = cal.get(Calendar.MONTH);
		d = cal.get(Calendar.DATE);
//		h = cal.get(Calendar.HOUR_OF_DAY);
//		mi = cal.get(Calendar.MINUTE);
//		s = cal.get(Calendar.SECOND);
		date[0] = m;
		date[1] = d;
		return date;
	}
}
