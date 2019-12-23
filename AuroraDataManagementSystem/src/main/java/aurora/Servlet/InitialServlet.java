package aurora.Servlet;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class InitialServlet extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws MongoException, ServletException, IOException
	{
		Mongo mg = new Mongo();

        DB db = mg.getDB("aurora");

        DBCollection metadbc = db.getCollection("Experiment.Meta");
        DBCollection picdbc = db.getCollection("Aurora.Image");

//		/*从Experiment。Meta中提取出最大的实验编号，确定现在的实验编号*/
//
        int experimentSeq=1;
//
//        DBCursor dbmaxseq = metadbc.find().sort(new BasicDBObject("seq",-1)).limit(1);
//
//        String smaxseq =dbmaxseq.next().get("seq").toString();
//
//        if(smaxseq.isEmpty())
//        {
//        	experimentSeq=1;
//        }
//        else
//        {
//        	experimentSeq=Integer.parseInt(smaxseq);
//        	experimentSeq++;
//        }
//
//		/*获取实验元信息*/
//		String starttime1 = request.getParameter("starttime1");
//		String endtime1 = request.getParameter("endtime1");
//		String band = request.getParameter("band");
//        String[] stype = request.getParameterValues("type");
//        char[] ctype = {'0','0','0','0'};
//        for(String t :stype)
//        {
//      	  ctype[Integer.parseInt(t)-1]='1';
//        }
//        String type = ""+ctype[0]+ctype[1]+ctype[2]+ctype[3];
//		String trainratio = request.getParameter("trainratio");
//		String testratio = request.getParameter("testratio");
//		String repeatnum = request.getParameter("repeatnum");
//
//		System.out.println(starttime1);
//		System.out.println(endtime1);
//		System.out.println(band);
//		System.out.println(type);
//		System.out.println(trainratio);
//		System.out.println(testratio);
//		System.out.println(repeatnum);
//
//        /*跟新Experiment。ExperimentSeq。Data*/
        DBCollection datadbc = db.getCollection("Experiment.E"+experimentSeq+".Data");
        int total = 0;

        DBCursor picmeta = picdbc.find();
        while(picmeta.hasNext())
        {
        	DBObject picresult= picmeta.next();
        	String picname = picresult.get("name").toString();

        	BasicDBObject data = new BasicDBObject();

        	data.put("seq", total+1);
        	data.put("name", picname);
        	data.put("type", "0");

        	datadbc.insert(data);

        	total++;
        }

//        /*更新Experiment。Meta*/
        BasicDBObject experimentmeta = new BasicDBObject();

        experimentmeta.put("seq", experimentSeq);
        experimentmeta.put("starttime1", "20170508");
        experimentmeta.put("endtime1", "20170509");
        experimentmeta.put("band", "G");
        experimentmeta.put("type", "0010");
        experimentmeta.put("trainratio", "0.7");
        experimentmeta.put("testratio", "0.3");
        experimentmeta.put("repeatnum", "2");

        experimentmeta.put("total", total);

        metadbc.insert(experimentmeta);

//        /*更新Experiment。ExperimentSeq。SubExperiment*/
        DBCollection subdbc = db.getCollection("Experiment.E"+experimentSeq+".SubExperiment");
        int repeat = Integer.parseInt("2");
        int train = (int) (total*Double.parseDouble("0.7"));
        int start = 0;

        for(int i=0;i<repeat;i++)
        {
        	BasicDBObject sub = new BasicDBObject();

        	sub.put("seq", i+1);
        	sub.put("start", start);
        	sub.put("width", train);

        	subdbc.insert(sub);

        	int move = total/repeat;
        	start = start + move;
        }

        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/extraction.jsp");
        request.setAttribute("seq", "1");   //暂时写成定量
        dispatcher.forward(request, response);


	}
}
