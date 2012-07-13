package routingRecommend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.internetrt.sdk.util.Application;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import ScriptEditor.InternetRuntime;
import ScriptEditor.TermToJson;

public class ToGetAccessToken extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ToGetAccessToken() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		String code = null;
		String appID = "6939c74b-9228-4a04-b97c-e8e8f3368595";
		String appSecret = "e39fc5f8-051f-48db-b697-0bda92dbbc10";
		
		System.out.println("FIRST");
		
		if(request.getParameter("code") == null){
			response.sendRedirect("http://localhost:9000/oauth/authorize?appID=6939c74b-9228-4a04-b97c-e8e8f3368595&redirect_uri=http://localhost:8080/IfApp/servlet/ToGetAccessToken");
			System.out.println("TWO");
		}
		else {
			code = request.getParameter("code");
			System.out.println("CODE"+code);
			InternetRuntime rt = new InternetRuntime();
			String accessToken = rt.getAccessToken(code, appID, appSecret);
			HttpSession session = request.getSession();
			session.setAttribute("accessToken", accessToken);
			getServletContext().getRequestDispatcher("/recomRouting.html").forward(request, response);
		}
		
		
		
//		for(Application application : applications){
//			String appJsonString = TermToJson.ApplicationToJson(application).toString();
//			System.out.println(appJsonString);
//		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}