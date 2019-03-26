package annotation.maps;

import java.util.ArrayList;
import java.util.HashMap;

import beans.UserBean;

public interface DongjeMap {
	
	public ArrayList<HashMap<String,Object>> userList(UserBean ub);
	
}
