package beans;

public class UserBean {

	private String userId;
	private String userPw;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	@Override
	public String toString() {
		return String.format("UserBean [userId=%s, userPw=%s]",userId,userPw);
	}
	
}
