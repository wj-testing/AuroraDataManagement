package aurora;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class ExtractionServlet extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws MongoException, ServletException, IOException
	{
		String seq = request.getParameter("seq");
		String algorithm = request.getParameter("algorithm");
		String parameter = request.getParameter("parameter");
		
		Mongo mg = new Mongo();
		   
		DB db = mg.getDB("aurora");
		
		DBCollection metadbc = db.getCollection("Experiment.Meta");
		DBCollection picdbc = db.getCollection("Aurora.Image");
		DBCollection featuredbc = db.getCollection("Experiment.E"+seq+".Feature");
		
		/*从InitialServlet读取所有数据的名称*/
		
		/*根据数据名称，从数据库中读取图像字节序列，并生产特征保存到数据库中*/
		DBObject queryCondition = new BasicDBObject();
		//queryCondition.put("name", "");
		DBCursor piccur = picdbc.find();	
		int count=1;
//		while(piccur.hasNext())
//		{
//			DBObject picdbo = piccur.next();
//			
//			byte[] data =(byte[]) picdbo.get("rawpic");
//			
//			
//			 float f[][] = SIFT.extractSift(data);
//			 
//			 BasicDBObject feature = new BasicDBObject();  
//			 feature.put("seq", count);
//			 feature.put("feature", f);
//			 featuredbc.insert(feature);
//			 count++;
//		}
		
		/*更新Experiment。Meta 和 Experiment。Feature*/
		BasicDBObject addattr = new BasicDBObject();
		addattr.put("extractionalgorithm", algorithm);
		addattr.put("extractionparameter", parameter);
		metadbc.update(new BasicDBObject("seq",seq),new BasicDBObject("$set",addattr));
		 
		/*请求转发*/	 		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/mlearn.jsp");
		request.setAttribute("seq", seq);
		dispatcher.forward(request, response);
	}
}
