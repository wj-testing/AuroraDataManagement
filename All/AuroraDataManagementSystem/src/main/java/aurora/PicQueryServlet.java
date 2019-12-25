package aurora;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class PicQueryServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	{	
		response.setContentType("image/jpeg");
		ServletOutputStream out=response.getOutputStream();
		
		Mongo mg = new Mongo();
			       
		DB db = mg.getDB("aurora");
		  
		DBCollection dbc = db.getCollection("Aurora.Meta");
		
		DBObject queryCondition = new BasicDBObject();
		
		queryCondition.put("name", request.getParameter("name"));
		       
		DBCursor cur = dbc.find(queryCondition);
//		byte[] data=(byte[]) cur.get("rawpic");
		
//		byte[] data = request.getParameter("pic").getBytes();
		
		DBObject dbo = cur.next();
		byte[] data =(byte[]) dbo.get("Image");
		
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        BufferedImage image = ImageIO.read(in); 
        
        JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(out);
        encoder.encode(image);
        out.close();
		
	}

	
}
