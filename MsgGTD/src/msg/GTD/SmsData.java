package msg.GTD;

public class SmsData {

		private String startTime = "0";
		private String endTime = "0";
		private int smsID;
		private String place = "home";
		private String content ;
		private String gtdStatus = "todo";
		private String gtdType = "home";
		private long write_time;
		
		public long getWrite_time() {
			return write_time;
		}
		public void setWrite_time(long write_time) {
			this.write_time = write_time;
		}
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		public int getSmsID() {
			return smsID;
		}
		public void setSmsID(int smsID) {
			this.smsID = smsID;
		}
		public String getPlace() {
			return place;
		}
		public void setPlace(String place) {
			this.place = place;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getGtdStatus() {
			return gtdStatus;
		}
		public void setGtdStatus(String gtdStatus) {
			this.gtdStatus = gtdStatus;
		}
		public String getGtdType() {
			return gtdType;
		}
		public void setGtdType(String gtdType) {
			this.gtdType = gtdType;
		}
		
		
}
