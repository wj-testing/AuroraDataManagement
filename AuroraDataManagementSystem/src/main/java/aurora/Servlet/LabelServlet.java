package aurora.Servlet;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class LabelServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws UnknownHostException
	{
		String type = request.getParameter("type");
		String time = request.getParameter("time");
		System.out.println(type);
        System.out.println(time.substring(0,time.length()-1)+".jpg");

		Mongo mg = new Mongo();

        DB db = mg.getDB("aurora");

        DBCollection dbc = db.getCollection("pic");

        DBObject updateCondition=new BasicDBObject();

        //where name='fox'
        updateCondition.put("time", time.substring(0,time.length()-1)+".jpg");

        DBObject updatedValue=new BasicDBObject();
        updatedValue.put("manualtype", type);

        DBObject updateSetValue=new BasicDBObject("$set",updatedValue);
        /**
         * update insert_test set headers=3 and legs=4 where name='fox'
         * updateCondition:更新条件
         * updateSetValue:设置的新值
         */
        dbc.update(updateCondition, updateSetValue);

//        DBObject queryCondition=new BasicDBObject();
//
//        //where name='sam',此条件在更新前不是成立的
//        queryCondition.put("name", "sam");
//
//        DBObject setValue=new BasicDBObject();
//        setValue.put("headers", 1);
//        setValue.put("legs", 1);
//
//        DBObject upsertValue=new BasicDBObject("$set",setValue);
//        /**
//         * 后面两个参数含义分别是：
//         * 若所更新的数据没有，则插入
//         * ，同时更新多个符合条件的文档(collection)
//         */
//        collection.update(queryCondition, upsertValue, true, true);
//        //set headers=headers+2
//        DBObject incValue=new BasicDBObject("headers",2);
//        //set legs=4
//        DBObject legsValue=new BasicDBObject("legs",4);
//
//        DBObject allCondition=new BasicDBObject();
//        allCondition.put("$inc", incValue);
//        allCondition.put("$set", legsValue);
//
//        collection.update(queryCondition, allCondition);
	}

}
