package aurora;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
//改好的
public class queryPic {
	
	
	public static List<DBObject> getMetaList(String from ,String to,String[] band ,String[] type) throws UnknownHostException, MongoException, ParseException{//参数为查询条件，目前没有，所以先加载任意30张
	  Mongo mg = new Mongo();    
      DB db = mg.getDB("aurora");
      DBCollection meta = db.getCollection("Aurora.Meta");
      BasicDBObject dbObject = new BasicDBObject();
      //mongodb实现大于等于小于等于区间的查询，大于是$gte,小于是$lte,时间过滤
      dbObject.put("time", new BasicDBObject("$gte", from).append("$lte",to));
      
      BasicDBList typeList=new BasicDBList();  //type
      for(int i =0; i<type.length;i++)
    	  {
          typeList.add(type[i]);
    	  }
      DBObject typeinObj=new BasicDBObject("$in",typeList); 
      dbObject.put("manualtype",typeinObj);
      
      BasicDBList bandList=new BasicDBList();  //band
      for(int i =0; i<band.length;i++)
	  {
      bandList.add(band[i]);
	  }
      DBObject bandinObj=new BasicDBObject("$in",bandList);  
      dbObject.put("band",bandinObj);
      meta.createIndex(new BasicDBObject("time", 1));
      //将查询结果按时间升序排列
      DBCursor cursor = (DBCursor) meta.find(dbObject).sort(new BasicDBObject("time",1));
   
      List<DBObject> picc=  cursor.toArray();  
         
	  return picc;
	}
	
//	public static List<DBObject> getImageList(List<String> name) throws UnknownHostException, MongoException, ParseException{//参数为查询条件，目前没有，所以先加载任意30张
//
//		  Mongo mg = new Mongo();
//	      DB db = mg.getDB("aurora");
//	      DBCollection image1 = db.getCollection("Aurora.Image");
//	      image1.createIndex(new BasicDBObject("name", 1));
//	      BasicDBObject dbObject = new BasicDBObject();
//	      BasicDBList nameList=new BasicDBList();  //翻译数组对象
//	      int n = name.size();
//	      for(int i =0; i<n;i++)
//		  {
//	      nameList.add(name.get(i));
//		  }
//	      DBObject nameinObj=new BasicDBObject("$in",nameList);  //$in的语法
//	      dbObject.put("name",nameinObj);
//	      DBCursor cursor = image1.find(dbObject).sort(new BasicDBObject("name",1));
//
//	      List<DBObject> picc= cursor.toArray();
//	     return picc;
//	}

	public static byte[][] getImage(List<DBObject> db) throws UnknownHostException, MongoException, ParseException{
		int n = db.size();
	    byte[][] raw = new byte[n][];
    	for (int i = 0 ;i<n;i++)
    	{
    		raw[i] = (byte[]) db.get(i).get("Image");
    	}
    	  
     return raw;
}
	
	public static List<String> getName(List<DBObject> db) throws UnknownHostException, MongoException{
		List<String> name = new ArrayList<String>();
        for(DBObject p:db)
        {
        	String name1= p.get("name").toString();
        	name.add(name1);
        }
		   
	    return name;
	}
		
	public static List<String> getBand(List<DBObject> db) throws UnknownHostException, MongoException{
		List<String> band = new ArrayList<String>();
        for(DBObject p:db)
        {
        	String band1= p.get("band").toString();
        	band.add(band1);
        }
		   
	    return band;
	}
	public static List<String> getType(List<DBObject> db) throws UnknownHostException, MongoException{
		List<String> type = new ArrayList<String>();
        for(DBObject p:db)
        {
        	String type1= p.get("manualtype").toString();
        	type.add(type1);
        }
		   
	    return type;
	}
	public static List<String> getTime(List<DBObject> db) throws UnknownHostException, MongoException{
		List<String> time = new ArrayList<String>();
        for(DBObject p:db)
        {
        	String time1= p.get("time").toString();
        	time.add(time1);
        }
		   
	    return time;
	}
	public static List<ArrayList> getKeogramList(List<DBObject> db){
		List<ArrayList> Keogram = new ArrayList<ArrayList>();
		for(DBObject p:db)
		{
			ArrayList keogram= (ArrayList) p.get("Keogram");
			Keogram.add(keogram);
		}

		return Keogram;

	}
	
//	public static List<DBObject> getKeogramList(List<String> name) throws UnknownHostException, MongoException, ParseException{//参数为查询条件，目前没有，所以先加载任意30张
//
//	  Mongo mg = new Mongo();
//      DB db = mg.getDB("aurora");
//      DBCollection keogram = db.getCollection("Aurora.Keogram");
//      BasicDBObject dbObject = new BasicDBObject();
//      BasicDBList nameList=new BasicDBList();  //翻译数组对象
//      for(int i =0; i<name.size();i++)
//	  {
//      nameList.add(name.get(i));
//	  }
//      DBObject nameinObj=new BasicDBObject("$in",nameList);  //$in的语法
//      dbObject.put("name",nameinObj);
//
//      DBCursor cursor = keogram.find(dbObject).sort(new BasicDBObject("name",1));
//      List<DBObject> picc= cursor.toArray();
//	     return picc;
//    }
	
//	public static BufferedImage getKeogram(List<DBObject> getKeogramList) throws UnknownHostException, MongoException, ParseException {
//
//	    int width = getKeogramList.size();
//
//		int R=220,n=0,m=0;
//		int keo_data[][]=new int[440][width];
//		int keoB[]=new int[2*R];
//
//		 for(int k=0;k<width;k++)
//		{
//			 //Array keogram = (Array) getKeogramList[k].get("keogram");
//			 BasicDBList dbIntList = (BasicDBList)getKeogramList.get(k).get("keogram");
// 		     for(int j=0;j<2*R;j++){
// 		    	keoB[j] = (Integer)dbIntList.get(j)& 0xff;
// 	    	    keo_data[j][k]=keoB[j];
// 		     }
//		}
//		 //System.out.println("done");
//		  BufferedImage bi = new BufferedImage(width,440, BufferedImage.TYPE_INT_RGB);
//		  Graphics2D g2 = (Graphics2D) bi.getGraphics();
//		  g2.setColor(Color.BLUE);
//		  g2.fillRect(0, 0, width,440);
//
//
//		   for(int s =0;s<width;s++) {
//			   int x=221;
//			   BufferedImage bi4 = new BufferedImage(1,440,BufferedImage.TYPE_INT_RGB);
//
//			   Graphics2D g4 = (Graphics2D) bi4.getGraphics();
//			   g4.setColor(Color.white);
//			   g4.fillRect(0, 0, 1,440);
//
//	            for (int y = 0; y < 2*R; y++)
//	            {
//			           // 获取到rgb的组合值
//			           int rgb = keo_data[y][s];
//			           Color color = new Color(rgb);
//			           int r = color.getRed() ;
//			           int g = color.getGreen() ;
//			           int b = color.getBlue() ;
//
//			           if (rgb <= 51)
//			           {
//			               r = 0;
//			               g=rgb*5;
//			               b=255;
//			           } else if (51<rgb &&rgb <= 102)
//			           {
//			               r = 0;
//			               g = 255;
//			               b=255-(rgb-51)*5;
//			           } else if (102<rgb &&rgb <= 153)
//			           {
//			           	r=(rgb-102)*5;
//			           	g =255;
//			           	b = 0;
//			           }else if (153<rgb &&rgb <= 204)
//			           {
//			           	r=255;
//			           	g =(int) Math.round(255-(128.0*(rgb-153)/51.0+0.5));
//			           	b = 0;
//			           }else if (204<rgb &&rgb <= 255)
//			           {
//			           	r=255;
//			           	g =(int) Math.round(127-(127.0*(rgb-204)/51.0+0.5));
//			           	b = 0;
//			           }
//
//			            Color c = new Color(r, g, b);
//			            //bi.setRGB(x, y, color.getRGB());
//		 				g2.setColor(c);   //此处为选则画点的颜色，R=G=B=0为黑色。R=G=B=1为白色
//		 				g4.setColor(c);
//						g2.drawLine(s,y , s, y);
//						g4.drawLine(0,y , 0, y);
//
//	            }
//		   }
//		return bi;
//
//         }
//
//
     public static BufferedImage getKeogram(List<ArrayList> getKeogramList) throws UnknownHostException, MongoException, ParseException {

	    int width = getKeogramList.size();

		int R=220,n=0,m=0;
		int keo_data[][]=new int[440][width];
		int keoB[]=new int[2*R];

		 for(int k=0;k<width;k++)
		{
			 //Array keogram = (Array) getKeogramList[k].get("keogram");
//			 BasicDBList dbIntList = (BasicDBList)getKeogramList.get(k).get("keogram");
 		     for(int j=0;j<2*R;j++){
 		    	keoB[j] = (Integer) getKeogramList.get(k).get(j)& 0xff;
 	    	    keo_data[j][k]=keoB[j];
 		     }
		}
		 //System.out.println("done");
		  BufferedImage bi = new BufferedImage(width,440, BufferedImage.TYPE_INT_RGB);
		  Graphics2D g2 = (Graphics2D) bi.getGraphics();
		  g2.setColor(Color.BLUE);
		  g2.fillRect(0, 0, width,440);


		   for(int s =0;s<width;s++) {
			   int x=221;
			   BufferedImage bi4 = new BufferedImage(1,440,BufferedImage.TYPE_INT_RGB);

			   Graphics2D g4 = (Graphics2D) bi4.getGraphics();
			   g4.setColor(Color.white);
			   g4.fillRect(0, 0, 1,440);

	            for (int y = 0; y < 2*R; y++)
	            {
			           // 获取到rgb的组合值
			           int rgb = keo_data[y][s];
			           Color color = new Color(rgb);
			           int r = color.getRed() ;
			           int g = color.getGreen() ;
			           int b = color.getBlue() ;

			           if (rgb <= 51)
			           {
			               r = 0;
			               g=rgb*5;
			               b=255;
			           } else if (51<rgb &&rgb <= 102)
			           {
			               r = 0;
			               g = 255;
			               b=255-(rgb-51)*5;
			           } else if (102<rgb &&rgb <= 153)
			           {
			           	r=(rgb-102)*5;
			           	g =255;
			           	b = 0;
			           }else if (153<rgb &&rgb <= 204)
			           {
			           	r=255;
			           	g =(int) Math.round(255-(128.0*(rgb-153)/51.0+0.5));
			           	b = 0;
			           }else if (204<rgb &&rgb <= 255)
			           {
			           	r=255;
			           	g =(int) Math.round(127-(127.0*(rgb-204)/51.0+0.5));
			           	b = 0;
			           }

			            Color c = new Color(r, g, b);
			            //bi.setRGB(x, y, color.getRGB());
		 				g2.setColor(c);   //此处为选则画点的颜色，R=G=B=0为黑色。R=G=B=1为白色
		 				g4.setColor(c);
						g2.drawLine(s,y , s, y);
						g4.drawLine(0,y , 0, y);

	            }
		   }
		return bi;

         }
}
