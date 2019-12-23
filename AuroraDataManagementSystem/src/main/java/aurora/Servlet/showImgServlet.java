package aurora.Servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import aurora.queryPic;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class showImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.getSession().removeAttribute("name");
		request.getSession().removeAttribute("title");
		request.getSession().removeAttribute("image");
		request.getSession().removeAttribute("keogram");

//		String start = "20031231000000";
//		String end = "20031231235959";
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

		if(request.getParameter("One")==null&&request.getParameter("Two")==null&&request.getParameter("Three")==null&&request.getParameter("Four")==null)
		{
			type1.add("0");
			type1.add("1");
			type1.add("2");
			type1.add("3");
			type1.add("4");
		}

		if(request.getParameter("One")!=null&&request.getParameter("One").equals("on"))
		{
			type1.add("1");
		}

		if(request.getParameter("Two")!=null&&request.getParameter("Two").equals("on"))
		{
			type1.add("2");
		}

		if(request.getParameter("Three")!=null&&request.getParameter("Three").equals("on"))
		{
			type1.add("3");
		}

		if(request.getParameter("Four")!=null&&request.getParameter("Four").equals("on"))
		{
			type1.add("4");
		}

		String[] qband = new String[band1.size()];

		for(int i=0;i<band1.size();i++)
		{
			qband[i]=band1.get(i);
		}

		String[] qtype = new String[type1.size()];

		for(int i=0;i<type1.size();i++)
		{
			qtype[i]=type1.get(i);
		}

        List<DBObject> meta;
        try {
			meta = queryPic.getMetaList(start, end, qband, qtype);
			List<String> name = queryPic.getName(meta);
			List<String> band = queryPic.getBand(meta);
			List<String> type = queryPic.getType(meta);
			List<String> time = queryPic.getTime(meta);
			List<DBObject> imagelist = queryPic.getImageList(name);
			List<DBObject> keogramlist = queryPic.getKeogramList(name);
			byte[][] image = queryPic.getImage(imagelist);
			BufferedImage keogramList = queryPic.getKeogram(keogramlist);
			ByteArrayOutputStream keo=new ByteArrayOutputStream();
			ImageIO.write(keogramList, "jpg",keo);
			byte[] keogrambyte = keo.toByteArray();
			System.out.println(Arrays.toString(keogrambyte));
			int n = name.size();
			List<String> title = new ArrayList();
			for(int i =0;i<n;i++){
				String timei = time.get(i);
				  String time1 = timei.substring(0, 4)+"-"+timei.substring(4, 6)+"-"+timei.substring(6, 8)+" "+timei.substring(8, 10)
			    	 +":"+timei.substring(10, 12)+":"+timei.substring(12, 14);
				  title.add(time1 + "      " + type.get(i));
				  System.out.println(title.get(i));
			}

			request.getSession().setAttribute("name",name);
			request.getSession().setAttribute("title",title);
			request.getSession().setAttribute("image",image);
			request.getSession().setAttribute("keogram",keogrambyte);
			ServletContext context = getServletContext();
			RequestDispatcher dispatcher = context.getRequestDispatcher("/index.jsp");
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
