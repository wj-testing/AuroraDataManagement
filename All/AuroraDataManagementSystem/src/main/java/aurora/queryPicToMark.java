package aurora;

//��Ҫ��ͼƬ���б�עʱ�������ݿ���н�������
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
//import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

public class queryPicToMark {
	public static void main(String args[]) throws MongoException, ParseException, IOException
	{
		String start = "20031231221650";
		String end = "20031231221750";
        List<DBObject> meta;
        String[] band1 = {"G"};
		
			meta = queryPicToMark.getMetaList(start, end,band1);
			List<String> name = queryPicToMark.getName(meta);
			List<String> band = queryPicToMark.getBand(meta);
			List<String> type = queryPicToMark.getType(meta);
			
			List<DBObject> imagelist = queryPicToMark.getImageList(name);
			byte[][] image = queryPicToMark.getImage(imagelist);
			int n = name.size();
			int index = 1;
		
			//markType(meta,index,"Mularc");
			System.out.println("done");
			
	}
	
	
	
	public static List<DBObject> getMetaList(String from ,String to,String[] band ) throws UnknownHostException, MongoException, ParseException{//����Ϊ��ѯ������Ŀǰû�У������ȼ�������30��
	  Mongo mg = new Mongo();    
      DB db = mg.getDB("aurora");
      DBCollection meta = db.getCollection("Aurora.Meta");
      BasicDBObject dbObject = new BasicDBObject();
      //mongodbʵ�ִ��ڵ���С�ڵ�������Ĳ�ѯ��������$gte,С����$lte,ʱ�����
      dbObject.put("time", new BasicDBObject("$gte", from).append("$lte",to));
      
      BasicDBList bandList=new BasicDBList();  //band
      for(int i =0; i<band.length;i++)
	  {
      bandList.add(band[i]);
	  }
      DBObject bandinObj=new BasicDBObject("$in",bandList);  
      dbObject.put("band",bandinObj);
      
      meta.createIndex(new BasicDBObject("time", 1));
      
      //dbObject.put("manualtype","0");//������Ϊ"0"����δ��ע��ͼƬ���ҳ���
      
      //����ѯ�����ʱ����������
      DBCursor cursor = (DBCursor) meta.find(dbObject).sort(new BasicDBObject("time",1));
   
      List<DBObject> picc=  cursor.toArray();  
         
	  return picc;
	}
	
	public static void  markType(List<DBObject> getMetaList, int index,String type,HttpServletRequest request) throws UnknownHostException, MongoException{
		boolean ismark = false;
		 Mongo mg = new Mongo();    
	      DB db = mg.getDB("aurora");
	      DBCollection meta = db.getCollection("Aurora.Meta");
		DBObject choosed = getMetaList.get(index);
		
		/* 
	     * DBCollection�����update�������� 
	     * update(query,obj)-->��ʾupdate(query,obj,false,false) 
	     * update(query,obj,true,true) 
	     * ��һ��������ʾҪ���滻�Ķ��󣬵ڶ���������ʾ�滻�Ķ��󣬵�����������false��ʾ���û�в�ѯ����ִ�и��²����� 
	     * ���ĸ�������ʾ�Ƿ�ִ����������(��Ϊ��������query����������кܶ�) 
	     */  
		DBObject typeValue=new BasicDBObject("manualtype",type);  
        
        DBObject allCondition=new BasicDBObject();  
        allCondition.put("$set", typeValue); 
        meta.update(choosed, allCondition); 
        choosed.removeField("manualtype");
        choosed.put("manualtype",type);
        getMetaList.remove(index);
        getMetaList.add(index,choosed);
        request.getSession().setAttribute("metaList1",getMetaList);
//        List<DBObject> tmpmeta = (List<DBObject>) request.getSession().getAttribute("metaList1");
//        DBObject modifytypeValue=new BasicDBObject("manualtype",type); 
//        tmpmeta.set(index, new DBObject());
	  }
       

	  	
	  	public static List<DBObject> getImageList(List<String> name) throws UnknownHostException, MongoException, ParseException{//����Ϊ��ѯ������Ŀǰû�У������ȼ�������30��
	  		
	  		  Mongo mg = new Mongo(); 
	  	      DB db = mg.getDB("aurora");
	  	      DBCollection image1 = db.getCollection("Aurora.Image");
	  	      image1.createIndex(new BasicDBObject("name", 1));
	  	      BasicDBObject dbObject = new BasicDBObject();
	  	      BasicDBList nameList=new BasicDBList();  //�����������
	  	      int n = name.size();
	  	      for(int i =0; i<n;i++)
	  		  {
	  	      nameList.add(name.get(i));
	  		  }
	  	      DBObject nameinObj=new BasicDBObject("$in",nameList);  //$in���﷨
	  	      dbObject.put("name",nameinObj);
	  	      DBCursor cursor = image1.find(dbObject).sort(new BasicDBObject("name",1)); 
	  	      
	  	      List<DBObject> picc= cursor.toArray();
	  	     return picc;
	  	}
	  	
	  	public static byte[][] getImage(List<DBObject> getImageList) throws UnknownHostException, MongoException, ParseException{
	  		int n = getImageList.size();	    
	  	    byte[][] raw = new byte[n][];
	      	for (int i = 0 ;i<n;i++)
	      	{
	      		raw[i] = (byte[]) getImageList.get(i).get("rawpic");
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
	
}