package com.controller;

import java.lang.reflect.InvocationTargetException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dao.PasswordEncode;
import com.service.ComService;

import annotation.maps.ComMap;
import annotation.maps.TestMappable;
import beans.ChatBean;
import beans.UserBean;


@Controller 
@RequestMapping("/")
public class ComController {
	public static String RSA_INSTANCE="rsa";
	@Autowired TestMappable testMappable;
	@Autowired  ComMap cm;
	@Autowired PasswordEncode pe;
	@Autowired ComService cs;
	
	/*테스팅*/
	
	@RequestMapping(value= {"/chatlist"},method=RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> getChatList(ChatBean cb,HttpSession session){
		cb.setFromId((String) session.getAttribute("userId"));
		return cs.getChatListByRecent(cb,5);
	}
	
	//rsa암호화 키 발급
	@RequestMapping(value = {"/log"}, method = RequestMethod.GET)
	public @ResponseBody HashMap<String, String> module(HttpServletRequest request){
		return pe.initRsa(request);
	}
	
	@RequestMapping(value= {"/log"},method=RequestMethod.POST)
	public @ResponseBody int login(UserBean ub,HttpSession session) throws Exception {
		 PrivateKey privateKey = (PrivateKey) session.getAttribute(pe.getRsa_web_key());
		 System.out.println(ub);
		 	// 복호화
	        ub.setM_id(pe.decryptRsa(privateKey, ub.getM_id()));
	        ub.setM_pw(pe.decryptRsa(privateKey, ub.getM_pw()));
	        
	        //db push
	        if(pe.match(ub.getM_pw(),(String) cm.login(ub).get("M_PW"))) {
	        	session.setAttribute("userId", ub.getM_id());
	        	return 1;
	        }
	    return 0;
	}
	
	@RequestMapping(value= {"/logout"},method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "home";
	}
	@RequestMapping(value = {"/chat/{toId}"}, method = RequestMethod.GET)
	public String chatForm(@PathVariable String toId,HttpServletRequest request) {
		request.setAttribute("toId", toId);
		return "chatForm";
	}
	
	@RequestMapping(value = {"/chat"}, method = RequestMethod.POST)
	public @ResponseBody void chat(ChatBean cb,HttpSession session) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		System.out.println((String)session.getAttribute("userId"));
		cb.setFromId((String)session.getAttribute("userId"));
		cs.sendChat(cb);
	}
	
	//회원가입
	@RequestMapping(value = {"/registerCheck"}, method = RequestMethod.POST)
	public @ResponseBody int registerCheck(UserBean ub) {
		return cm.registerCheck(ub);
	}
	@RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
	public String signup(UserBean ub,HttpSession session,MultipartFile avatar_img) throws Exception {
		System.out.println("컨트롤러 도착");
		cs.add_user(ub, session, avatar_img);
		System.out.println("컨트롤러 종료");
		
		return "home";
	}
	/*테스팅*/
	@RequestMapping(value = { "/","/home"}, method = RequestMethod.GET)
	public String homePage(ModelMap m) {
		return "home";
	}
	
}