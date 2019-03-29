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
	
	@Select("select count(*)count from chatbox where chat_read=1 and CHAT_TO=#{user}")
	public HashMap<String,String> get_Read(String user);
}
