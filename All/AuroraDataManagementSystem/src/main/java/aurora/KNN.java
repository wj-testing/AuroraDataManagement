package aurora;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class KNN {
	private List<KNNNode> knnNodes=new ArrayList<KNNNode>();
	private int k;
	
	public KNN(){
		
	}
	
	public KNN(List<KNNNode> knnNodes){
		this.knnNodes=knnNodes;
	}
	
	public int getNearestCategory(List<KNNNode> knnNodes, KNNNode testNode, int k){
		KDTree kdTree=new KDTree();
		List<KNNNode> kNearestNode=kdTree.getKNearstNodes(knnNodes, testNode, k);
		Map<Integer,Integer> cat=new HashMap<Integer, Integer>();
		for(KNNNode node:kNearestNode){
			if(cat.containsKey(node.getCategory()))
				cat.put(node.getCategory(), cat.get(node.getCategory())+1);
			else
				cat.put(node.getCategory(), 1);
		}
		Integer[] key=new Integer[cat.size()];
		cat.keySet().toArray(key);
		Integer[] value=new Integer[cat.size()];
		cat.values().toArray(value);
		int maxIndex=0;
		//查找出现次数最多的类的标号（如果出现次数相同，则只取一个）
		for(int i=0; i<value.length;i++){
			if(value[i]>value[0])
				maxIndex=i;
		}
		int category=key[maxIndex];
		return category;
		
	}
	

	public List<KNNNode> getKnnnodes() {
		return knnNodes;
	}

	public void setKnnnodes(List<KNNNode> knnnodes) {
		this.knnNodes = knnnodes;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		
		BufferedImage image=ImageIO.read(new File("D:\\38044\\N20031221G045211.jpg"));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();      //字节输出流
		ImageIO.write(image, "bmp", bos);      //将虚拟图片信息写入到字节输出流中
        byte[] b = bos.toByteArray();//generate byte[]     //输出流将字节信息输出到byte数组b中
        float[][] sift = SIFT.extractSift(b);  //提取出sift特征
        List<trainset> trains=trains(); 
        int lable=labelAurora(trains,sift,3);	//对某张图片进行标记
        System.out.println(lable);
		
	}
	
	
	//对某张图片进行标记，k为找最近的K张图片
	public static int labelAurora(List<trainset> train , float[][] test , int k)
	{
		List<trainset> trains = train;
		List<KNNNode> knnNodesList=new ArrayList<KNNNode>();

		for(int i = 0; i < trains.size();i++)
		{
			trainset trainimg;
			trainimg = trains.get(i);
			float[][] trainimg1=trainimg.getSift();
			List<Float> trainimage1 = SIFT.siftchangeto(trainimg1);
			KNNNode kn1=new KNNNode(i,trainimage1,trainimg.getType());//分别为序号、sift值、类别
			knnNodesList.add(kn1);  
		}
		//测试图片转换格式
		float[][] testimg=test;
		List<Float> testimage = SIFT.siftchangeto(testimg);
		
		
		int lable = 0;
        KNNNode testKnn=new KNNNode(testimage);
 		KNN knn=new KNN();
			
	    lable=knn.getNearestCategory(knnNodesList, testKnn, k);
			
		
	
		return lable;
	}
	
	   //生成训练集
		public static List<trainset> trains()
		{
			List<trainset> trains = new ArrayList<trainset>();
			
			try {       
				String f= "D:\\38044\\11\\";
		         File file = new File( f); 
		         
		         if(file.exists()) 
			        { 
			            File[] filelist = file.listFiles();
			     
			            for(int i = 0;i<filelist.length;i++) 
			            { 
			                String filename = filelist[i].getName(); 
							BufferedImage image=ImageIO.read(new File(f+filename));
							 ByteArrayOutputStream bos = new ByteArrayOutputStream();      //字节输出流
						        ImageIO.write(image, "bmp", bos);      //将虚拟图片信息写入到字节输出流中
						        byte[] b = bos.toByteArray();//generate byte[]     //输出流将字节信息输出到byte数组b中
						        float[][] sift = SIFT.extractSift(b);
						        
						        trainset train = new trainset(sift,1);
						        trains.add(i, train);
								
			            }
	    	        
			         }
		         String f3= "D:\\38044\\33\\";
		         File file3 = new File( f3); 
		         
		         if(file3.exists()) 
			        { 
			            File[] filelist = file3.listFiles();
			     
			            for(int i = 0;i<filelist.length;i++) 
			            { 
			                String filename = filelist[i].getName(); 
							BufferedImage image=ImageIO.read(new File(f3+filename));
							ByteArrayOutputStream bos = new ByteArrayOutputStream();      //字节输出流
					        ImageIO.write(image, "bmp", bos);      //将虚拟图片信息写入到字节输出流中
					        byte[] b = bos.toByteArray();//generate byte[]     //输出流将字节信息输出到byte数组b中
					        float[][] sift = SIFT.extractSift(b);
					        List<Float> siftchangeto=SIFT.siftchangeto(sift);
					        trainset train = new trainset(sift,1);
					        trains.add(i+3, train);  		
			            }	
			         }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return trains;
			
		}
		

}
