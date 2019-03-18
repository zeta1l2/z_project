package beans;

public class ChatBean {
	double _id;
	String fromId;
	String toId;
	String chatContent;
	String chatTime;
	
	
	
	public double get_id() {
		return _id;
	}
	public void set_id(double _id) {
		this._id = _id;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public String getChatContent() {
		return chatContent;
	}
	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}
	public String getChatTime() {
		return chatTime;
	}
	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}
	@Override
	public String toString() {
		return String.format("ChatBean [_id=%s, fromId=%s, toId=%s, chatContent=%s, chatTime=%s]"
				,_id,fromId,toId,chatContent,chatTime);
	}
}
