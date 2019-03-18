package annotation.maps;

import java.util.HashMap;

import org.apache.ibatis.annotations.Select;

import beans.UserBean;

public interface ComMap {
	public int registerCheck(UserBean ub);
	public void signupDb(UserBean ub);
	
	
	@Select("SELECT * FROM USERCHAT WHERE USERID= #{userId}")
	public HashMap<String,Object> login(UserBean ub);
}
