package annotation.maps;

import java.util.ArrayList;
import java.util.HashMap;

import beans.ChatBean;
import beans.UserBean;

public interface DongjeMap {
	
	public ArrayList<HashMap<String,Object>> userList(UserBean ub);
	public ArrayList<HashMap<String, Object>> getTalk(ChatBean cb);
	public void setTalk(ChatBean cb);
}
