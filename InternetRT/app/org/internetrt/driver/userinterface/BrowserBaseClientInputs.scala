package org.internetrt.driver.userinterface

import play.api.mvc._
import org.internetrt.SiteUserInterface
import org.internetrt.CONSTS

object BrowserBaseClientInputs extends Controller {
  def register() = Action {
    request =>
      if (request.method == "GET") {
        Ok(views.html.register())
      }else{
	      val username = request.body.asFormUrlEncoded.get("username").head;
	      val password = request.body.asFormUrlEncoded.get("password").head;

	      Ok(SiteUserInterface.register(username, password));
      }
  }

  def login() = Action {
    implicit request =>
      System.out.println("Before Login:"+request.session)
      
      val mainpage = controllers.routes.Application.index().absoluteURL(false)
      if (request.method == "GET") {
        val oldurl = request.queryString.get("oldurl").getOrElse(Seq.empty).headOption.getOrElse(mainpage);
        Ok(views.html.login(oldurl))
      }else{
	      val username = request.body.asFormUrlEncoded.get("username").head;
	      val password = request.body.asFormUrlEncoded.get("password").head;
	      val oldurl = request.body.asFormUrlEncoded.get("oldurl").headOption.getOrElse(mainpage);
	
	      //		try{
	      val uid = SiteUserInterface.login(username, password);
	
	      System.out.println("UID in login" + uid);
	      System.out.println("oldurl:" + oldurl);
	      Redirect(oldurl).withSession(request.session + (CONSTS.SESSIONUID -> uid));
	    //		}catch{
	    //		  case e => {
	    //		    e.printStackTrace();
	    //		    Unauthorized("Login Failed")
	    //		  }
	    //		}
      }
  }
  def confirmRouting() = Action {
    implicit request =>
      
      request.session.get(CONSTS.SESSIONUID) match {
        case Some(uid) => {
          request.body.asFormUrlEncoded.get("xml") match {
            case Seq(xml) => {
              
              val success = SiteUserInterface.confirmRouting(uid, xml);

              Ok(success.toString());
            }
            case _ => InternalServerError
          }
        }
        case None => {
          val thispage = controllers.routes.Application.index().absoluteURL(false)
          Ok(views.html.login(thispage))
        }
      }
  }
  def installRootApp() = Action {
    implicit request =>
      
      System.out.println("Install root app"+request.session)
      
      if (request.method == "GET") Ok(views.html.installRootApp())
      else request.session.get(CONSTS.SESSIONUID) match {
        case Some(uid) => {
          request.body.asFormUrlEncoded.get("xml") match {
            case Seq(xml) => {
              System.out.println("UID" + uid);
              System.out.println("XML" + xml);

              val success = SiteUserInterface.installRootApp(uid, xml);

              Ok(success.toString());
            }
            case _ => InternalServerError
          }
        }
        case None => {
          val thispage = controllers.routes.Application.index().absoluteURL(false)
          Ok(views.html.login(thispage))
        }
      };

  }

}