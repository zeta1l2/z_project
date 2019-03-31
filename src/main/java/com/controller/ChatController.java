package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.ChatService;

import annotation.maps.DongjeMap;
import beans.ChatBean;
import beans.UserBean;



@Controller 
@RequestMapping("/chat")
public class ChatController {
	@Autowired  DongjeMap dm;
	@Autowired ChatService cs;
	
	@RequestMapping(value = {"","/"}, method = RequestMethod.GET)
	public String chatForm() {
		return "chatForm";
	}
	//안읽은 최신 메시지 불러오기
	@RequestMapping(value = {"","/"}, method = RequestMethod.POST)
	public @ResponseBody ArrayList<HashMap<String,Object>> get_new_talk(HttpSession session) {
		System.out.println("안읽은 최신 메시지 컨트롤러 실행");
		String user=(String)session.getAttribute("userId");
		ArrayList<HashMap<String, Object>> result=cs.get_new_talk(user);
		System.out.println(result);
		System.out.println("안읽은 최신 메시지 컨트롤러 종료");
		return result;
	}
	
	/*채팅*/
	@RequestMapping(value = {"/{from_id}"}, method = RequestMethod.GET)
	public String chatForm(@PathVariable String from_id, Model m) {
		System.out.println("다이렉트 채팅 내용 불러오기 시작");
		System.out.println(from_id);
		m.addAttribute("from_id",from_id);
		System.out.println("다이렉트 채팅 내용 불러오기 종료");
		return "chatForm";
	}
	@RequestMapping(value = {"/box"}, method = RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> chat(UserBean ub,HttpSession session){
		System.out.println("채팅 유저 불러오기 컨트롤러");
		return cs.getUser(ub,session);
	}
	@RequestMapping(value = {"/box"}, method = RequestMethod.GET)
	public String box(){
		return "login_error";
	}
	@RequestMapping(value = {"/talk"}, method = RequestMethod.GET)
	public @ResponseBody ArrayList<HashMap<String,Object>> talk(ChatBean cb,HttpSession session){
		System.out.println("개인채팅 시작 컨트롤러");
		cb.setChat_from((String)session.getAttribute("userId"));
		ArrayList<HashMap<String, Object>> result=cs.getTalk(cb);
		System.out.println(result);
		System.out.println("개인채팅 시작 컨트롤러 종료");
		return result;
	}
	@RequestMapping(value = {"/talk"}, method = RequestMethod.POST)
	public @ResponseBody void send_talk(ChatBean cb,HttpSession session){
		System.out.println("개인채팅 입력 컨트롤러");
		cb.setChat_from((String)session.getAttribute("userId"));
		cs.setTalk(cb);
		System.out.println("개인채팅 입력 컨트롤러 종료");
	}
	@RequestMapping(value = {"/read"}, method = RequestMethod.GET)
	public @ResponseBody ArrayList<HashMap<String,Object>> get_read(HttpSession session){
		System.out.println("신규 메시지 알람 컨트롤러");
		String user=(String)session.getAttribute("userId");
		ArrayList<HashMap<String, Object>> result=cs.get_new_talk(user);
		System.out.println(result);
		System.out.println("신규 메시지 알람 컨트롤러 종료");
		return result;
	}
	@RequestMapping(value = {"/read"}, method = RequestMethod.POST)
	public @ResponseBody void get_read(HttpSession session,ChatBean cb){
		System.out.println("메시지 읽음처리 컨트롤러");
		cb.setChat_from((String)session.getAttribute("userId"));
		System.out.println(cb);
		cs.get_read(cb);
		System.out.println("메시지 읽음처리 컨트롤러 종료");
	}
	/*채팅*/
	
	
}