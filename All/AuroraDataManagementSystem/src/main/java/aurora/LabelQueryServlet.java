package aurora;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

public class LabelQueryServlet extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Jedis jedis = new Jedis("127.0.0.1",6379);  
		
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String band = request.getParameter("band");
		
		System.out.println(startTime);
		System.out.println(endTime);
		System.out.println(band);
		
		Set result = jedis.zrangeByScore("address",startTime, endTime);
		
		ArrayList<String> path = new ArrayList<String>();
		
		for(Object r:result)
		{
			System.out.println(r.toString().substring(3, 28));
			path.add(r.toString().substring(2, 28));
		}
		
		request.setAttribute("path", path);
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/label.jsp");
		dispatcher.forward(request, response);
	}
	
}
