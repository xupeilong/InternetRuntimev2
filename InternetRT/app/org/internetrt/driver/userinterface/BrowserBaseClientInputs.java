package org.internetrt.driver.userinterface;

import org.internetrt.CONSTS;
import org.internetrt.SiteUserInterface;

import play.mvc.Controller;
import play.mvc.Result;

public class BrowserBaseClientInputs extends Controller{

	
	public static Result register(){
		String username = request().queryString().get("username")[0];
		String password = request().queryString().get("password")[0];
		return ok(SiteUserInterface.register(username,password));
	}
	public static Result login(){
		String username = request().queryString().get("username")[0];
		String password = request().queryString().get("password")[0];
		
		try{
			String uid = SiteUserInterface.login(username,password);
			session(CONSTS.SESSIONUID(), uid);
			
			System.out.println("UID in login"+session(CONSTS.SESSIONUID()));
			
			return ok(uid);
		}catch(Exception e){
			return ok(e.getMessage());
		}	
	}
	
	public static Result installRootApp(){
		String uid = session().get(CONSTS.SESSIONUID());
		String xml = request().queryString().get("xml")[0];
		
		System.out.println("UID"+uid);
		System.out.println("XML" + xml);
		
		Boolean success =  SiteUserInterface.installRootApp(uid,xml);
		
		return ok(success.toString());
	}
	
}
