package annotation.maps;

import org.apache.ibatis.annotations.Select;

import beans.UserBean;

public interface ComMap {
	public int registerCheck(UserBean ub);
	public void signupDb(UserBean ub);
	
	@Select("select * from get_inf WHERE M_ID= #{m_id}")
	public UserBean login(UserBean ub);
}
