package com.controller;

import java.lang.reflect.InvocationTargetException;
import java.security.PrivateKey;
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
	
	@RequestMapping(value= {"/log"},method=RequestMethod.POST)
	public String login(UserBean ub,HttpSession session) throws Exception {
		 PrivateKey privateKey = (PrivateKey) session.getAttribute(pe.getRsa_web_key());
	        // 복호화
	        ub.setUserId(pe.decryptRsa(privateKey, ub.getUserId()));
	        ub.setUserPw(pe.decryptRsa(privateKey, ub.getUserPw()));
	        // 개인키 삭제
	        session.removeAttribute(pe.getRsa_web_key());
	        //db push
	        if(pe.match(ub.getUserPw(),(String) cm.login(ub).get("USERPW"))) {
	        	session.setAttribute("userId", ub.getUserId());	        	
	        }
	        //로그인 처리
		return "home";
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
	
	@RequestMapping(value = {"/registerCheck"}, method = RequestMethod.POST)
	public @ResponseBody int registerCheck(UserBean ub) {
		return cm.registerCheck(ub);
	}
	//회원가입폼 이동
	@RequestMapping(value = {"/module"}, method = RequestMethod.GET)
	public @ResponseBody void module(HttpServletRequest request){
		pe.initRsa(request);
	}
	//회원가입
	@RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
	public String signup(UserBean ub,HttpSession session) throws Exception {
		System.out.println(ub);
        PrivateKey privateKey = (PrivateKey) session.getAttribute(pe.getRsa_web_key());
        
        // 복호화
        ub.setUserId(pe.decryptRsa(privateKey, ub.getUserId()));
        ub.setUserPw(pe.decryptRsa(privateKey, ub.getUserPw()));
        System.out.println(ub);
        // 개인키 삭제
        session.removeAttribute(pe.getRsa_web_key());
        session.removeAttribute("RSAModulus");
        session.removeAttribute("RSAExponent");
        //암호화 저장
        ub.setUserPw(pe.encode(ub.getUserPw()));
        System.out.println(ub);
        //db push
        cm.signupDb(ub);
        //로그인 처리
        session.setAttribute("userId", ub.getUserId());
 
		return "home";
	}
	/*테스팅*/
	@RequestMapping(value = { "/","/home"}, method = RequestMethod.GET)
	public String homePage(ModelMap m) {
		return "home";
	}
	
	@RequestMapping(value = { "/k-drive"}, method = RequestMethod.GET)
	public String homePage(ModelMap m) {
		return "k-drive";
	}	
}