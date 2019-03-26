package com.service;

import java.security.PrivateKey;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.dao.Dao;
import com.dao.MongoDb;
import com.dao.PasswordEncode;

import annotation.maps.ComMap;
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
	
		//회원가입 후 로그인 서비스
		public void add_user(UserBean ub,HttpSession session,MultipartFile f) throws Exception {
			
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
	      //이미지 파일 존재 시 추가 후 bean에 파일 이름 삽입
			System.out.println("이미지파일 업로딩");
			if(!f.isEmpty()) {
			dao.upload_file(ub.getM_id(),f, avatar_path);
			}
			System.out.println("이미지파일 업로딩 종료");
	        //db push
	        cm.signupDb(ub);
	        //로그인 처리
	        login_session(session,ub);
	        /*몽고db에 검색용 데이터 저장*/
	        
	        md.mongoInsert("z_project-beta", "user", ub);
		}
		//로그인 처리 메서드
		public void login_session(HttpSession session,UserBean ub) {
			session.setAttribute("userId", ub.getM_id());
			session.setAttribute("userGrade", ub.getM_grade());
		}
			
}
