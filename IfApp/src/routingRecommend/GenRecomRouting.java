package routingRecommend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.internetrt.sdk.util.RoutingGenerator;

import ScriptEditor.InternetRuntime;
import ScriptEditor.TermToJson;

public class GenRecomRouting extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GenRecomRouting() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
		String triggerChannel = null;
		String actionChannel = null;
		HttpSession session = request.getSession();

		String trigger = request.getParameter("signal");
		String receiver = request.getParameter("receiver");
		String sender = request.getParameter("sender");
		String type = request.getParameter("type");
		
		System.out.println("signal: "+trigger);
		System.out.println("receiver: "+receiver);
		System.out.println("sender: "+sender);
		System.out.println("type: "+type);
		
		if(type.equals("receiver"))
		{
			triggerChannel = receiver;
			actionChannel = (String) session.getAttribute("fromApp");
		}
		else {
			triggerChannel = sender;
			actionChannel = (String)session.getAttribute("toApp");
		}
		
		InternetRuntime rt = new InternetRuntime();
		
		
		String accessToken = session.getAttribute("accessToken").toString();
		
		String signalXml = rt.getSignalDefination(trigger);
		String appXml = rt.getAppDetail(actionChannel, accessToken);
		
		RoutingGenerator routingGenerator = new RoutingGenerator(signalXml, appXml);
		String routingXml = routingGenerator.generateRouting(trigger, triggerChannel, actionChannel);
		
		String resultString = TermToJson.stringToJson("routingXml", routingXml).toString();
		
		PrintWriter out = response.getWriter();
		out.write(resultString);
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
