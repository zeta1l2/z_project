package beans;

public class UserBean {

	private Integer m_num;
	private String m_id;
	private Integer m_grade;
	private String m_pw;
	private String m_name;
	private String m_phone;
	private String m_avatar;
	
	public Integer getM_num() {
		return m_num;
	}

	public void setM_num(Integer m_num) {
		this.m_num = m_num;
	}

	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public Integer getM_grade() {
		return m_grade;
	}

	public void setM_grade(Integer m_grade) {
		this.m_grade = m_grade;
	}

	public String getM_pw() {
		return m_pw;
	}

	public void setM_pw(String m_pw) {
		this.m_pw = m_pw;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_phone() {
		return m_phone;
	}

	public void setM_phone(String m_phone) {
		this.m_phone = m_phone;
	}

	public String getM_avatar() {
		return m_avatar;
	}

	public void setM_avatar(String m_avatar) {
		this.m_avatar = m_avatar;
	}

	@Override
	public String toString() {
		return String.format("UserBean [m_num=%s, m_id=%s, m_grade=%s, m_pw=%s, m_name=%s, m_phone=%s, m_avatar=%s]",
				m_num,m_id,m_grade,m_pw,m_name,m_phone,m_avatar);
	}
	
}
