package aurora.Servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aurora.ImageUtil;
import com.mongodb.DBObject;

public class markshowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		boolean ismark = false;
		  byte[][] image=(byte[][])request.getSession().getAttribute("image1");
		  List<String> title = (List<String>) request.getSession().getAttribute("title1");
		  List<String> name = (List<String>) request.getSession().getAttribute("name1");
		  List<DBObject> meta = (List<DBObject>) request.getSession().getAttribute("metaList1");
		  InputStream is=null;
			ImageUtil imageUtil=null;

		  String id1 = request.getParameter("id");
		    int id2 = Integer.valueOf(id1).intValue();

		  is = new ByteArrayInputStream(image[id2]);
		  imageUtil=new ImageUtil();
	      imageUtil.imgHandler(is, response,request);


    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

			this.doPost(request, response);

	}

}
