package com.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.ChatService;

import annotation.maps.DongjeMap;
import beans.UserBean;



@Controller 
@RequestMapping("/chat")
public class ChatController {
	@Autowired  DongjeMap dm;
	@Autowired ChatService cs;
	
	@RequestMapping(value = {""}, method = RequestMethod.GET)
	public String chatForm() {
		return "chatForm";
	}
	
	/*채팅*/
	@RequestMapping(value = {"/box"}, method = RequestMethod.GET)
	public @ResponseBody ArrayList<HashMap<String, Object>> chat(UserBean ub){
		System.out.println(ub);
		System.out.println(cs.getUser(ub));
		return cs.getUser(ub);
	}
	/*채팅*/
	
	
}