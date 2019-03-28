package beans;

import java.util.Date;

public class ChatBean {
	private Integer chat_code;
	private String chat_from;
	private String chat_to;
	private String chat_content;
	private Date chat_date;
	private String chat_read;
	
	public Integer getChat_code() {
		return chat_code;
	}
	public void setChat_code(Integer chat_code) {
		this.chat_code = chat_code;
	}
	public String getChat_from() {
		return chat_from;
	}
	public void setChat_from(String chat_from) {
		this.chat_from = chat_from;
	}
	public String getChat_to() {
		return chat_to;
	}
	public void setChat_to(String chat_to) {
		this.chat_to = chat_to;
	}
	public String getChat_content() {
		return chat_content;
	}
	public void setChat_content(String chat_content) {
		this.chat_content = chat_content;
	}
	public Date getChat_date() {
		return chat_date;
	}
	public void setChat_date(Date chat_date) {
		this.chat_date = chat_date;
	}
	public String getChat_read() {
		return chat_read;
	}
	public void setChat_read(String chat_read) {
		this.chat_read = chat_read;
	}

	@Override
	public String toString() {
		return String.format("ChatBean [chat_code=%s, chat_from=%s, chat_to=%s, chat_content=%s, chat_date=%s, chat_read=%s]"
				,chat_code,chat_from,chat_to,chat_content,chat_date,chat_read);
	}
}
