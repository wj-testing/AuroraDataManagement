package aurora;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mongodb.DBObject;

/**
 *从前端获取要更改的图片（第几张图片）以及要设置的类型，对数据库进行操作
 */
public class markServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("gb2312");
		response.setContentType("text/html");
		  List<DBObject> meta = (List<DBObject>) request.getSession().getAttribute("metaList1");
		  
		    String type = request.getParameter("setType") ;
		    String getindex = request.getParameter("index");
		    int index = Integer.parseInt(getindex);
			queryPicToMark.markType(meta,index,type,request);
			System.out.println(type);
			System.out.println(getindex);
    }
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
			this.doPost(request, response);
		
	}

}
