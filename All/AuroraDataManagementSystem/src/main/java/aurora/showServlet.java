package aurora;

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

//改好的
public class showServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("开始请求后台!!!!");
		
		  
		  byte[][] image=(byte[][])request.getSession().getAttribute("image");
		  List<String> title = (List<String>) request.getSession().getAttribute("title");
		  List<String> name = (List<String>) request.getSession().getAttribute("name");
			  
		  InputStream is=null;
			ImageUtil imageUtil=null;
		  String id1 = request.getParameter("id");
		    int id2 = Integer.valueOf(id1).intValue(); 
		    System.out.println(id2);
		
		  is = new ByteArrayInputStream(image[id2]); 
			for(int i =0;i<name.size();i++){
				
				  System.out.println(name.get(i));
			}
		  imageUtil=new ImageUtil();
	      imageUtil.imgHandler(is, response,request);
		  System.out.println(id2);  
		
    }
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
			this.doPost(request, response);
		
	}

}
