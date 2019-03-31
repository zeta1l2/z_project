package annotation.maps;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Select;

import beans.ChatBean;
import beans.UserBean;

public interface DongjeMap {
	
	public ArrayList<HashMap<String,Object>> userList(UserBean ub);
	public ArrayList<HashMap<String, Object>> getTalk(ChatBean cb);
	public void setTalk(ChatBean cb);
	public ArrayList<HashMap<String,Object>>getNewTalk(String user);
	
	@Select("{CALL p_read(#{chat_to},#{chat_from})}")
	public void upt_Read(ChatBean cb);
}
