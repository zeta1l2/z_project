package com.service;

import java.lang.reflect.InvocationTargetException;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.dao.Dao;
import com.dao.MongoDb;
import com.dao.PasswordEncode;
import com.mongodb.client.model.Filters;

import annotation.maps.ComMap;
import beans.ChatBean;
import beans.UserBean;
@Component
public class ComService {

	@Autowired ComMap cm;
	@Autowired MongoDb md;
	@Autowired Dao dao;
	@Autowired PasswordEncode pe;
	
	String avatar_path="C:\\Users\\HU221\\git\\z_project-beta\\src\\main\\webapp\\static\\com\\images\\avatar";
	
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
		
		//회원가입 후 로그인 서비스
		public void add_user(UserBean ub,HttpSession session,MultipartFile f) throws Exception {
			//이미지 파일 존재 시 추가 후 bean에 파일 이름 삽입
			System.out.println("이미지파일 업로딩");
			if(!f.isEmpty()) {
			Map<String, String> avatar_map= dao.upload_file(f, avatar_path);
			ub.setM_avatar(avatar_map.get("fname"));
			}else {
			ub.setM_avatar("");
			}
			System.out.println("이미지파일 업로딩 종료");
	        PrivateKey privateKey = (PrivateKey) session.getAttribute(pe.getRsa_web_key());
	        System.out.println(ub);
	        // 복호화
	        ub.setM_id(pe.decryptRsa(privateKey, ub.getM_id()));
	        ub.setM_pw(pe.decryptRsa(privateKey, ub.getM_pw()));
	        ub.setM_name(pe.decryptRsa(privateKey, ub.getM_name()));
	        ub.setM_phone(pe.decryptRsa(privateKey, ub.getM_phone()));
	        
	        
	        // 개인키 삭제
	        session.removeAttribute(pe.getRsa_web_key());
	        //암호화 저장
	        ub.setM_pw(pe.encode(ub.getM_pw()));
	        System.out.println(ub);
	        //db push
	        cm.signupDb(ub);
	        //로그인 처리
	        session.setAttribute("userId", ub.getM_id());
			
		}
		
		
}
