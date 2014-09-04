package list.show;

public class Message {
	
		private Integer id;
		private String message;

		
		public Message(Integer id, String message) {
			super();
			this.id = id;
			this.message = message;
		}
		
		public Message(String message ){
			super();
			this.message = message;
		}

		public Integer getId() {
			return id;
		}
		
		public void setId(Integer id) {
			this.id = id;
		}

		
		
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return "Student [id=" + id + ", message=" + message +  "]";
		}
		
	
	
}
