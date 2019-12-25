package mongodb;

import com.mongodb.util.JSON;
import com.google.gson.internal.$Gson$Types;
import com.mongodb.*;
import jdk.nashorn.internal.ir.Block;
import net.minidev.json.JSONObject;
import org.bson.types.Binary;
import org.codehaus.jettison.json.JSONArray;

import javax.swing.text.Document;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static jdk.nashorn.internal.objects.NativeFunction.function;

public class changeMeta {
    public static void main(String[] args) throws UnknownHostException {
        Mongo mg = new Mongo();
        DB db = mg.getDB("aurora");
        DBCollection meta = db.getCollection("Aurora.Meta");
        DBCursor cursor = meta.find();
        List<DBObject> picc=  cursor.toArray();
        System.out.println(picc);
        System.out.println(picc.get(0).get("name"));
        for(int i=0;i<picc.size();i++){
            String name = (String) picc.get(i).get("name");
            BasicDBObject fea = new BasicDBObject();
            fea.put("name",name);
            DBCollection feature = db.getCollection("Aurora.Image");
            DBCursor cur = feature.find(fea);
            List<DBObject> pi=  cur.toArray();
            byte[] image = (byte[]) pi.get(0).get("rawpic");
            System.out.println(image);

            BasicDBObject dbObject = new BasicDBObject();
            dbObject.put("name",name);

            BasicDBObject db2 = new BasicDBObject();
                db2.put("Image",image);

            BasicDBObject db1 = new BasicDBObject();
            db1.put("$set",db2);

             meta.update(dbObject,db1);
        }


    }
}
