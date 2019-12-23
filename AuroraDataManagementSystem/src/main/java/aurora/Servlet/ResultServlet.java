package aurora.Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResultServlet extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{



		 ServletContext context = getServletContext();
	     RequestDispatcher dispatcher = context.getRequestDispatcher("/analysis.jsp");
	     dispatcher.forward(request, response);
	}

}
