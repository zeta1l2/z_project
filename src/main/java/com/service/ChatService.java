package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.Dao;
import com.dao.MongoDb;
import com.mongodb.client.model.Filters;

import annotation.maps.ComMap;
import annotation.maps.DongjeMap;
import beans.ChatBean;
import beans.UserBean;

@Component
public class ChatService {

	@Autowired ComMap cm;
	@Autowired DongjeMap dm;
	@Autowired MongoDb md;
	@Autowired Dao dao;
	
	//친구 불러오기
	public List<Map<String, Object>> getUser(UserBean ub, HttpSession session){
		System.out.println("친구목록 불러오기 서비스 실행");
		String loginUser=(String)session.getAttribute("userId");
		System.out.println(loginUser);
		Bson where= Filters.and(Filters.regex("m_id", ub.getM_id()),Filters.ne("m_id", loginUser));
		List<Map<String, Object>> list=md.mongoSelect("z_project-beta", "user",where);
		System.out.println(list);
		System.out.println("친구목록 불러오기 서비스 종료");
		return list;
	}
	/*채팅*/
	//채팅 내용 불러오기
	public ArrayList<HashMap<String, Object>> getTalk(ChatBean cb){
		System.out.println("채팅 내용 불러오기 서비스 실행");
		ArrayList<HashMap<String, Object>> result =new ArrayList<HashMap<String,Object>>();
		//날짜 변환
		try {
		for(HashMap<String, Object> date : dm.getTalk(cb)) {
			String d=dao.date_change(date.get("CHAT_DATE"));
			d=dao.date_change(d);
			date.put("CHAT_TIME", d.substring(14, d.length()-3));
			date.replace("CHAT_DATE", d.substring(0, 13));
			result.add(date);
		}
		}catch(Exception e) {
			System.out.println("불러올 내용 없음 서비스 종료");
			return null;
		}
		System.out.println("채팅 내용 불러오기 서비스 종료");
		return result;
	}
	//채팅 내용 전송하기
	public void setTalk(ChatBean cb) {
		System.out.println("채팅 내용 전송하기 서비스 실행");
		cb.setChat_content(dao.changeContent(cb.getChat_content()));
		System.out.println(cb);
		dm.setTalk(cb);
		System.out.println("채팅 내용 전송하기 서비스 종료");
	}
	//신규 메시지 숫자
	public HashMap<String, String> get_read(String user) {
		System.out.println("신규 채팅 알람 서비스 시작");
		HashMap<String, String> map;
		try {
		map=dm.get_Read(user);
		}catch(NullPointerException e) {
			return null;
		}
		System.out.println("신규 채팅 알람 서비스 종료");
		return map;
	}
	/*채팅*/
	
	/* 몽고 db 채팅 구현 테스팅
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
	*/
}
