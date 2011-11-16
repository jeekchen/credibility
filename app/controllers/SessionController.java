package controllers;

import models.account.User;
import play.mvc.Controller;

public class SessionController extends Controller{
	protected static User getLoginUser(){
		String userName= session.get("username");
		return User.getUser(userName);
	}
	
	protected static User getCallinUser(){
		String userName= session.get("callinUserName");		
		return User.getUser(userName);
	}
	
	protected static void renderMsg(String message){
		renderJSON(String.format("{\"message\":\"%s\"}", message));
	}
}
