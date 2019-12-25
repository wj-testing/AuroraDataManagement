package aurora;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * 需要对图片进行标注时，时间范围和波段设定后，调用该servlet，将所需要的数据放入session
 */
public class queryMarkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ArrayList<String> band1=new ArrayList<String>();
	    ArrayList<String> type1=new ArrayList<String>();
		
		String start = (String) request.getParameter("starttime");
		String end = (String) request.getParameter("endtime");
		String gband = (String) request.getParameter("Gband");
		String vband = (String) request.getParameter("Vband");
		String rband = (String) request.getParameter("Rband");
		
		if(gband!=null&&gband.equals("on"))
		{
			band1.add("G");
		}
		
		if(vband!=null&&vband.equals("on"))
		{
			band1.add("V");
		}
		
		if(rband!=null&&rband.equals("on"))
		{
			band1.add("R");
		}
		
		start=start.replaceAll("-", "")+"000000";
		end=end.replaceAll("-", "")+"235959";
		
		String[] qband = new String[band1.size()];
		
		for(int i=0;i<band1.size();i++)
		{
			qband[i]=band1.get(i);
		}
		
        List<DBObject> meta;
	      
			try {
				meta = queryPicToMark.getMetaList(start, end,qband);
				List<String> name = queryPic.getName(meta);
				List<String> band = queryPic.getBand(meta);
				List<String> time = queryPic.getTime(meta);

				byte[][] image = queryPic.getImage(meta);
				int n = name.size();
				List<String> title = new ArrayList();
				for(int i =0;i<n;i++){
					String timei = time.get(i);
					  String time1 = timei.substring(0, 4)+"-"+timei.substring(4, 6)+"-"+timei.substring(6, 8)+" "+timei.substring(8, 10)
				    	 +":"+timei.substring(10, 12)+":"+timei.substring(12, 14);
					  title.add(time1 + "      " + band.get(i));
					  System.out.println(title.get(i));
				}
				request.getSession().removeAttribute("name1");
				request.getSession().removeAttribute("title1");
				request.getSession().removeAttribute("image1");
				request.getSession().removeAttribute("metaList1");
				request.getSession().setAttribute("name1",name);
				request.getSession().setAttribute("title1",title);
				request.getSession().setAttribute("image1",image);
				request.getSession().setAttribute("metaList1",meta);
				ServletContext context = getServletContext();
				RequestDispatcher dispatcher = context.getRequestDispatcher("/label.jsp");
				dispatcher.forward(request, response);
				
			} catch (MongoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			  
		
		
    }
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
			this.doPost(request, response);
		
	}

}
