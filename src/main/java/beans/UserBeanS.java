package beans;

public class UserBeanS {

	protected Integer m_num;
	protected String m_grade;
	protected String m_pw;
	protected String m_phone;

	public Integer getM_num() {
		return m_num;
	}

	public void setM_num(Integer m_num) {
		this.m_num = m_num;
	}

	public String getM_grade() {
		return m_grade;
	}

	public void setM_grade(String m_grade) {
		this.m_grade = m_grade;
	}

	public String getM_pw() {
		return m_pw;
	}

	public void setM_pw(String m_pw) {
		this.m_pw = m_pw;
	}

	public String getM_phone() {
		return m_phone;
	}

	public void setM_phone(String m_phone) {
		this.m_phone = m_phone;
	}

	@Override
	public String toString() {
		return String.format("UserBean [m_num=%s, m_grade=%s, m_pw=%s, m_phone=%s]",
				m_num,m_grade,m_pw,m_phone);
	}
	
}
