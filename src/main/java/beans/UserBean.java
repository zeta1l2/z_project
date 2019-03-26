package beans;

public class UserBean extends UserBeanS{

	private String m_id;
	private String m_name;

	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}


	@Override
	public String toString() {
		return String.format("UserBean [m_num=%s, m_id=%s, m_grade=%s, m_pw=%s, m_name=%s, m_phone=%s]",
				m_num,m_id,m_grade,m_pw,m_name,m_phone);
	}
	
}
