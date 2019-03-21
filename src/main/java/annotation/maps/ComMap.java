package annotation.maps;

import java.util.HashMap;

import org.apache.ibatis.annotations.Select;

import beans.UserBean;

public interface ComMap {
	public int registerCheck(UserBean ub);
	public void signupDb(UserBean ub);
	
	@Select("SELECT * FROM MEMBER WHERE M_ID= #{m_id}")
	public HashMap<String,Object> login(UserBean ub);
}
