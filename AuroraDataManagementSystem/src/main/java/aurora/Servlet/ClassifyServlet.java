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

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class ClassifyServlet extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("helllllo");

		String seq = request.getParameter("seq");
		String algorithm = request.getParameter("algorithm");
		String parameter = request.getParameter("parameter");

		Mongo mg = new Mongo();

		DB db = mg.getDB("aurora");

		DBCollection metadbc = db.getCollection("Experiment.Meta");
		DBCollection featuredbc = db.getCollection("Experiment.E1.Feature");
		DBCollection subdbc = db.getCollection("Experiment.E1.SubExperiment");

		/*��ȡ������*/
		List<float[][]> featureset = new ArrayList<float[][]>();
		DBCursor featurecur = featuredbc.find();
		while(featurecur.hasNext())
		{
			DBObject featuredbo = featurecur.next();

			BasicDBList dbfeature =  (BasicDBList) featuredbo.get("feature");

			float[][] feature = new float[dbfeature.size()][128];

			int i=0;

			for(Object f:dbfeature)
			{
				BasicDBList onefeature = (BasicDBList)f;
				int j=0;
				for(Object ff:onefeature)
				{
					//double d = (double) ff;
					//feature[i][j]=(float)d;
					j++;
				}
				i++;
			}

			featureset.add(feature);

		}

		System.out.println(featureset.size());


		/*ѭ��ʵ��*/
		DBCursor subcur = subdbc.find();

		while(subcur.hasNext())
		{
			DBObject subdbo = subcur.next();

		}

		//ͨ��count�ظ�ʵ��,�˴�Ӧ��ͨ�����ݿ�ȡ��
//		int totalDay = 10;
//		int count = 10;
//		double trainratio=1;
//		double testratio=1;
//		int startDay = 1;
//		double totalAccuracy=0;
//
//		for(int i=0;i<count;i++)
//		{
//
//			//���浥��ʵ�鼯��
//			DBCollection singledbc = db.getCollection("experiment."+seq+".sub");
//
//			double train = (trainratio)/(trainratio+testratio);
//			double trainset = totalDay*train;
//			int span = totalDay/count;
//
//			BasicDBObject single = new BasicDBObject();
//			single.put("seq", seq+i);
//			single.put("starttime", startDay);
//			single.put("trainnumber", trainset);
//			startDay = startDay+span;
//
//			//����kmeans����,subseq
//			String subseq=seq+i;
//			DBCollection kmeansdbc = db.getCollection("experiment."+seq+"."+subseq+".kmeans");
//
//			//�õ�ʵ����������(ÿ�����ݵĻ�����ע���ͺ���ȷ��)
//			int[] result = new int[totalDay];
//			double accuracy = 0.5;
//			single.put("result", result);
//			single.put("accuracy", accuracy);
//
//			totalAccuracy+=accuracy;
//		}
//
//		totalAccuracy=totalAccuracy/10;
//
//		BasicDBObject addattr = new BasicDBObject();
//		addattr.put("classifyalgorithm", algorithm);
//		addattr.put("classifyparameter", parameter);
//		addattr.put("accuracy", totalAccuracy);
//		metadbc.update(new BasicDBObject("seq",seq),new BasicDBObject("$set",addattr));

		ServletContext context = getServletContext();
	    RequestDispatcher dispatcher = context.getRequestDispatcher("/result.jsp");
	    dispatcher.forward(request, response);

	}

}
