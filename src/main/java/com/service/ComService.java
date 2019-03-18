package com.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.MongoDb;
import com.mongodb.client.model.Filters;

import annotation.maps.ComMap;
import beans.ChatBean;
import beans.UserBean;
@Component
public class ComService {

	@Autowired ComMap cm;
	@Autowired MongoDb md;
	
	public int registerCheck(UserBean ub) {
		System.out.println(cm.registerCheck(ub));
		return 0;
	}
	
	//채팅
	public List<Map<String, Object>> getChatListById(ChatBean cb){
		Bson where= Filters.and(Filters.or(
				Filters.and(Filters.eq("toId",cb.getToId()),Filters.eq("fromId",cb.getFromId())),
				Filters.and(Filters.eq("toId",cb.getFromId()),Filters.eq("fromId",cb.getToId()))
				),Filters.gt("_id", cb.get_id()));
		List<Map<String, Object>> list=md.mongoSelect("chat", "chat", where);
		
		for(Map<String,Object> map: list) {
			map.replace("chatContent", md.changeContent((String) map.get("chatContent")));
			map.replace("chatTime", md.getTime((String)map.get("chatTime")));
		}
		return list;
	}
	//최근채팅 불러오기
		public List<Map<String, Object>> getChatListByRecent(ChatBean cb, int number){
			List<Map<String, Object>> select=md.mongoSelect("chat", "chat");
			double max=md.getMax(select);
			Bson where= Filters.and(Filters.or(
					Filters.and(Filters.eq("toId",cb.getToId()),Filters.eq("fromId",cb.getFromId())),
					Filters.and(Filters.eq("toId",cb.getFromId()),Filters.eq("fromId",cb.getToId()))
					),Filters.gt("_id", max-number));
			List<Map<String, Object>> list=md.mongoSelect("chat", "chat", where);
			
			for(Map<String,Object> map: list) {
				map.replace("chatContent", md.changeContent((String) map.get("chatContent")));
				map.replace("chatTime", md.getTime((String)map.get("chatTime")));
			}
	
			return list;
		}
	//채팅 전송
		public void sendChat(ChatBean cb) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
			//시퀀스 등록
			cb.set_id(md.getSequence("chat_num"));
			//현재 시간 등록
			cb.setChatTime(md.getTime());
			md.mongoInsert("chat", "chat", cb);
		}
}
