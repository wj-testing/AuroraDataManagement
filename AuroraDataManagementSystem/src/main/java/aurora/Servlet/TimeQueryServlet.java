package aurora.Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import redis.clients.jedis.Jedis;

public class TimeQueryServlet extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Mongo mg = new Mongo();

        DB db = mg.getDB("aurora");

        DBCollection dbc = db.getCollection("pic.meta");

        DBCursor cur = dbc.find();
        List<DBObject> picc= cur.toArray();
        List<String> pic = new ArrayList<String>();
        for(DBObject p:picc)
        {
        	String data= p.get("name").toString();
        	pic.add(data);
        }

        request.setAttribute("name", pic);

		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);



		//redis ≤È—Ø
//		Jedis jedis = new Jedis("127.0.0.1",6379);
//
//		String startTime = request.getParameter("startTime");
//		String endTime = request.getParameter("endTime");
//		String band = request.getParameter("band");
//
//		System.out.println(startTime);
//		System.out.println(endTime);
//		System.out.println(band);
//
//		Set result = jedis.zrangeByScore("address",startTime, endTime);
//
//		ArrayList<String> path = new ArrayList<String>();
//
//		for(Object r:result)
//		{
//			System.out.println(r.toString().substring(3, 28));
//			path.add(r.toString().substring(2, 28));
//		}
//
//		request.setAttribute("path", path);
//
//		ServletContext context = getServletContext();
//		RequestDispatcher dispatcher = context.getRequestDispatcher("/index.jsp");
//		dispatcher.forward(request, response);

	}

}
