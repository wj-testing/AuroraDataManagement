package aurora.Servlet;

import aurora.SimilarImageSearch;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.hadoop.io.BytesWritable;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.List;

public class ImportServlet extends HttpServlet{

	 private static final long serialVersionUID = 1L;

     // �ϴ��ļ��洢Ŀ¼
     private static final String UPLOAD_DIRECTORY = "upload";

     // �ϴ�����
     private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
     private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
     private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

     public static int getByte(byte a,byte b)
 	{
 		return (int) ((b & 0xFF)*256+ (a & 0xFF));
 	}

 	public static void createDir(String path)
     {
 		File dir=new File(path);
         if(!dir.exists())
         	dir.mkdir();
     }


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		 // ����Ƿ�Ϊ��ý���ϴ�
        if (!ServletFileUpload.isMultipartContent(request)) {
            // ���������ֹͣ
            PrintWriter writer = response.getWriter();
            writer.println("Error: ��������� enctype=multipart/form-data");
            writer.flush();
            return;
        }

        // �����ϴ�����
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // ������ʱ�洢Ŀ¼
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // ��������ļ��ϴ�ֵ
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // �����������ֵ (�����ļ��ͱ�����)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // ���Ĵ���
        upload.setHeaderEncoding("UTF-8");

        // ������ʱ·�����洢�ϴ����ļ�
        // ���·����Ե�ǰӦ�õ�Ŀ¼
        String uploadPath = getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;


        // ���Ŀ¼�������򴴽�
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            // ���������������ȡ�ļ�����
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                // ����������
                for (FileItem item : formItems) {
                    // �����ڱ��е��ֶ�
                    if (!item.isFormField()) {
                        String ufileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + ufileName;
                        File storeFile = new File(filePath);
                        // �ڿ���̨����ļ����ϴ�·��
                        System.out.println(filePath);
                        // �����ļ���Ӳ��
                        item.write(storeFile);
//	                    BufferedImage image = ImageIO.read(new FileInputStream(filePath)); //��ImageIO������ͼƬ�ļ�ת��������ͼƬ��Ϣ
//	                    ByteArrayOutputStream bos = new ByteArrayOutputStream();      //�ֽ������
//	                    ImageIO.write(image, "bmp", bos);      //������ͼƬ��Ϣд�뵽�ֽ��������
//	                    byte[] b = bos.toByteArray();//generate byte[]     //��������ֽ���Ϣ�����byte����b��

                        byte[] b = null;
                        FileInputStream fis = new FileInputStream(filePath);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] tmpb = new byte[1024];
                        int n;
                        while((n = fis.read(tmpb)) != -1)
                        {
                        	bos.write(tmpb, 0, n);
                        }
                        fis.close();
                        bos.close();
                        b = bos.toByteArray();

	                    //*************************************Ԥ����**************************************
	                    BytesWritable value = new BytesWritable(b);
	            		String date="";
	               	    String time="";
	               	    String hour="";
	               	    String waveband="";

	               	    try {
	                	    DataInputStream dis=new DataInputStream(new ByteArrayInputStream(b));
	            	    	InputStreamReader rea=new InputStreamReader(dis);
	            	    	BufferedReader br = new BufferedReader(rea);

	             	        String read = br.readLine();


	             	        br.skip(3*read.length());
	             	        int index_comment=read.indexOf("[Comment]");

	             	        int index = read.indexOf("Date");
	             	        date = read.substring(index+6,index+10)+read.substring(index+11,index+13)+read.substring(index+14,index+16);

	             	        if(index_comment==-1)
	             	        {
	             	        	int index1 = read.indexOf("Time");
	             	            time = read.substring(index1+6,index1+8)+read.substring(index1+9,index1+11)+read.substring(index1+12,index1+14);
	             	            hour = read.substring(index1+6,index1+8);
	                        }
	                        else
	                        {
	                            time=read.substring(index_comment+34,index_comment+36)+read.substring(index_comment+37,index_comment+39)+ read.substring(index_comment+40,index_comment+42);
	                 	        hour= read.substring(index_comment+34,index_comment+36);
	                        }
	                       br.close();
	             	    }
	             		catch (IOException ie){
	             	       	  System.err.println(ie.getMessage());
	             	    }

	               	    //------------------------------------------------------------------------------------------------
	               	    int noiseR=1137,noiseG=546,noiseV=594,LimRayleighR=4000,LimRayleighG=4000,LimRayleighV=4000,Rx0=256,Ry0=257,Gx0=261,Gy0=257,Vx0=257,Vy0=255;
	                    float RK= (float) 0.5159,GK=(float) 1.0909,VK=(float) 1.5280,rotatedegree=(float) -61.1;
	                    String savetype=".jpg";

	                    float I,K=0.0f;
	                    String fileName;
	            		int noise=0,LimRayleigh=1,x0=0,y0=0;
	            		int r=220;

	                    BufferedImage bi = new BufferedImage(512, 512,BufferedImage.TYPE_INT_RGB);   //���ڴ�������512x512��ͼ�񻺳�����TYPE_INT_RGB��ʾһ��ͼ�񣬸�ͼ������������ص� 8 λ RGB��ɫ

	            		Graphics g = bi.getGraphics();

	            		fileName=ufileName;    //��ȡ�ļ���
	            		fileName=fileName.substring(fileName.lastIndexOf("/")+1);

	            		g.clearRect(0, 0, 2*r, 2*r);   //�����һ��440x440�ľ�������

	            		if(fileName.charAt(0) == 'E' && fileName.charAt(1) == 'N' )
	            		{
	            			if (fileName.charAt(8) == 'R')
	            			{   //�ж��ļ�������һ������

	            				noise = noiseR;
	            				LimRayleigh = LimRayleighR;
	            				x0=Rx0;
	            				y0=Ry0;
	            				K = RK;
	            			}
	            			else if (fileName.charAt(8) == 'G')
	            			{
	            				noise = noiseG;
	            				LimRayleigh = LimRayleighG;
	            				x0=Gx0;
	            				y0=Gy0;
	            				K = GK;
	            			}
	            			else if (fileName.charAt(8) == 'V')
	            			{
	            				noise = noiseV;
	            				LimRayleigh =LimRayleighV;
	            				x0=Vx0;
	            				y0=Vy0;
	            				K = VK;
	            			}
	            		}
	            	    else{
	            			if (fileName.charAt(7) == 'R')
	            			{   //�ж��ļ�������һ������
	            				noise = noiseR;
	            				LimRayleigh = LimRayleighR;
	            				x0=Rx0;
	            				y0=Ry0;
	            				K = RK;
	            			}
	            			else if (fileName.charAt(7) == 'G')
	            			{
	            				noise = noiseG;
	            				LimRayleigh = LimRayleighG;
	            				x0=Gx0;
	            				y0=Gy0;
	            				K = GK;
	            			}
	            			else if (fileName.charAt(7) == 'V')
	            			{
	            				noise = noiseV;
	            				LimRayleigh =LimRayleighV;
	            				x0=Vx0;
	            				y0=Vy0;
	            				K = VK;
	            			}
	            		}

	            	    try
	            	    {
	            				int result[][];

	            				result = new int[512][512];
	            				int point =value.getLength()-524289;

	            			    for(int i=0;i<512;i++)
	            			    	for(int j=0;j<512;j++)
	            			    	{
	            			    		result[i][j]=getByte(b[point+1],b[point+2]);
	            			    		point=point+2;
	            			    	}

	            				for (int i= 0; i <= 511; i++){

	            					for (int j = 0; j <= 511; j++)
	            					{
	            						int p =result[j][i];  //���ж����ļ��ֽڵ�ת��
	            						I =(p-noise)*K;
	            						I = (I/LimRayleigh);
	            						if(Math.pow((j-y0),2)+Math.pow((i-x0),2)>=Math.pow(r,2)) //�뾶rԲ�����������������ɫ
	            						I=0;
	            						else
	            						{if(I<0)
	            							I=0;
	            						if(I>1)
	            							I=1;
	            						}
	            						g.setColor(new Color(I, I, I));   //�˴�Ϊѡ�򻭵����ɫ��R=G=B=0Ϊ��ɫ��R=G=B=1Ϊ��ɫ
	            						g.drawLine(i,j , i, j);    //�ڣ�i,j�������������ɫ����

	            			        }
	            			   }
	            		}
	            	    catch (Exception e1)
	            	    {
	            				// TODO Auto-generated catch block
	            				e1.printStackTrace();
	            		}

	                    BufferedImage img =new BufferedImage(512 ,512,BufferedImage.TYPE_INT_RGB); //��ͼ�����-61.1�ȵ���ʱ����ת
	            		BufferedImage img1 =new BufferedImage(440 ,440,BufferedImage.TYPE_INT_RGB);
	            		AffineTransform transform = new AffineTransform ();
	            	    transform.rotate(rotatedegree*Math.PI/180,x0,y0);

	            	    AffineTransformOp op = new AffineTransformOp(transform,null);
	            	    op.filter(bi, img);
	            	    img1=img.getSubimage( x0-r,  y0-r,  440,  440);

	            	    bi.flush();
	            	    img.flush();

	            	    ImageIO.write(img1, "jpg", new File("D:\\pretest.jpg"));
	                    //*******************************************************************************

	            	    //*************************************��������ͼ***********************************
	            	    BufferedImage target = null;
	        		    int targetW=75;
	        		    int targetH=75;
	        		    BufferedImage source=img1;
	        		    int type = source.getType();
	        		    double sx = (double) targetW / source.getWidth();
	        	        double sy = (double) targetH / source.getHeight();
	        	        if(sx>sy)
	        	        {
	        	          sx = sy;
	        	          targetW = (int)(sx * source.getWidth());
	        	        }
	        	        else
	        	        {
	        	          sy = sx;
	        	          targetH = (int)(sy * source.getHeight());
	        	        }
	        	        if (type == BufferedImage.TYPE_CUSTOM) { //handmade
	        	             ColorModel cm = source.getColorModel();
	        	             WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
	        	             boolean alphaPremultiplied = cm.isAlphaPremultiplied();
	        	             target = new BufferedImage(cm, raster, alphaPremultiplied, null);
	        	        }
	        	        else
	        	           target = new BufferedImage(targetW, targetH, type);

	        	        Graphics2D gg = target.createGraphics();

	        	        //smoother than exlax:
	        	        gg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
	        	        gg.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
	        	        gg.dispose();
	        	        File saveFile = new File("D:/mintest.jpg");
	        	        ImageIO.write(target,"jpg",saveFile);
	            	    //******************************************************************************

	        	        //*******************************��ȡkeogram����***********************************
	        	        int keoB[] = new int[440];
	        	        for(int i=0;i<440;i++)
	        	        {
	        	        	keoB[i] = img1.getRGB(221, i);
	        	        }
	        	        //******************************************************************************

	        	        //**************************************�������ݿ���Ҫ�������Ϣ*************************
	        	        //name:ufilename,time:time,band:band
	        	        //original
	        	        //thumb
	        	        //keogram:keoB[i]
	        	        //feature:��

	        			Mongo mg = new Mongo();

	        	        DB db = mg.getDB("aurora");

	        	        DBCollection meta = db.getCollection("Aurora.Meta");
	        	        DBCollection original = db.getCollection("Aurora.Image");
	        	        DBCollection thumb=db.getCollection("Aurora.Thumb");
	        	        DBCollection keogram=db.getCollection("Aurora.Keogram");
	        	        DBCollection feature=db.getCollection("Aurora.Feature");

	        	        ufileName = ufileName.substring(0,13);
	        	        time = date+time;
	        	        waveband = ""+ufileName.charAt(7);

	               	    BasicDBObject metadbo = new BasicDBObject();
	                    metadbo.put("name",ufileName);
//	                    metadbo.put("rawpath", request.getParameter("uploadFile"));  ���ǵ�Redis���汣����·��������Ҫ
	                    metadbo.put("time", time);
	                    metadbo.put("band",waveband);
	                    metadbo.put("manualtype", "0");
	                    meta.insert(metadbo);

	                    BasicDBObject picdbo = new BasicDBObject();
	                    ByteArrayOutputStream oribos = new ByteArrayOutputStream();      //�ֽ������
	                    ImageIO.write(img1, "bmp", oribos);      //������ͼƬ��Ϣд�뵽�ֽ��������
	                    byte[] orib = oribos.toByteArray();//generate byte[]     //��������ֽ���Ϣ�����byte����b��
	                    picdbo.put("name",ufileName);
	                    picdbo.put("rawpic",orib);
	                    original.insert(picdbo);

	                    BasicDBObject thumbdbo = new BasicDBObject();
	                    ByteArrayOutputStream thumbbos = new ByteArrayOutputStream();      //�ֽ������
	                    ImageIO.write(target, "bmp", thumbbos);      //������ͼƬ��Ϣд�뵽�ֽ��������
	                    byte[] thumbb = thumbbos.toByteArray();//generate byte[]     //��������ֽ���Ϣ�����byte����b��
	                    thumbdbo.put("name",ufileName);
	                    thumbdbo.put("thumb",thumbb);
	                    thumb.insert(thumbdbo);

	                    BasicDBObject keogramdbo = new BasicDBObject();
	                    keogramdbo.put("name",ufileName);
	                    keogramdbo.put("keogram",keoB);
	                    keogram.insert(keogramdbo);

	                    BasicDBObject featuredbo = new BasicDBObject();
	                    featuredbo.put("name",ufileName);
	                    featuredbo.put("feature", SimilarImageSearch.produceFingerPrint(orib));
	                    feature.insert(featuredbo);

	        	        //******************************************************************************

                        request.setAttribute("message",
                            "�ļ��ϴ��ɹ�!");
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message",
                    "������Ϣ: " + ex.getMessage());
        }
        // ��ת�� message.jsp
        getServletContext().getRequestDispatcher("/message.jsp").forward(
                request, response);



//***********************************************************************************************************
//		Date startdate = new Date(request.getParameter("startdate"));
//		Date enddate = new Date(request.getParameter("startdate"));

		/*try {
			downFile("192.168.1.106",21,"NAS","NAS");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

//******************************��import***********************************************************************
//		//DB��ʼ��
//		Mongo mg = new Mongo();
//
//        DB db = mg.getDB("aurora");
//
//        DBCollection meta = db.getCollection("Aurora.Meta");
//        DBCollection original = db.getCollection("Aurora.Image");
//
//        //ԭʼ��Ϣ��ȡ
//
//        File root = new File("D:\\data\\");
//        File[] files = root.listFiles();
//
//        for(File file:files)
//        {
//        	//����Aurora��Meta
//        	 BasicDBObject metadbo = new BasicDBObject();
//             BasicDBObject picdbo = new BasicDBObject();
//
//             String filename=file.getName();
//             metadbo.put("name",filename.substring(0,16));
//             metadbo.put("rawpath", "D:\\data\\"+filename);
//             metadbo.put("time", filename.substring(1,9)+filename.substring(10,16));
//             metadbo.put("band",filename.substring(9,10));
//             metadbo.put("manualtype", "0");
//
//             meta.insert(metadbo);
//
//             //����Aurora.Image
//             BufferedImage image = ImageIO.read(new FileInputStream(file)); //��ImageIO������ͼƬ�ļ�ת��������ͼƬ��Ϣ
//             ByteArrayOutputStream bos = new ByteArrayOutputStream();      //�ֽ������
//             ImageIO.write(image, "bmp", bos);      //������ͼƬ��Ϣд�뵽�ֽ��������
//             byte[] b = bos.toByteArray();//generate byte[]     //��������ֽ���Ϣ�����byte����b��
//             picdbo.put("name",filename.substring(0,16));
//             picdbo.put("rawpic",b);
//             original.insert(picdbo);
//
//        }






//--------------------------------------------ͼƬת��Ϊ�ֽ�-----------------------------------------------------
//      DBCursor cur = dbc.find();
//      List<DBObject> picc= cur.toArray();
//	    for(DBObject p:picc)
//	    {
//	    	byte[] data=(byte[]) p.get("rawpic");
//	    	  FileImageOutputStream imageOutput = new FileImageOutputStream(new File("D:\\test.jpg"));
//	    	    imageOutput.write(data, 0, data.length);
//	    	    imageOutput.close();
//	    }

//---------------------------------------------Redis---------------------------------------------------------
//        Jedis jedis = new Jedis("127.0.0.1",6379);
//
//        DBCursor cur = dbc.find();
//        List<DBObject> pic= cur.toArray();
//	    for(DBObject p:pic)
//	    {
//	    	System.out.println(p.get("time"));
//	    	System.out.println(p.get("path"));
//	    	//jedis.set(p.get("time").toString(), p.get("path").toString());
//	    	//String value = jedis.get("myKey");
//		    //System.out.println(value);
//	    	String time=p.get("time").toString();
//	    	String path=p.get("path").toString();
//	    	String rawTime=time.substring(1, 9)+time.substring(10,16);
//	    	System.out.println(time);
//	    	System.out.println(path);
//	    	System.out.println(Long.parseLong(rawTime));
//	    	jedis.zadd("address",Long.parseLong(rawTime),path);
//	    }
	}

	/*private void downFile(String url,int port,String username,String password) throws SocketException, IOException, URISyntaxException
	{
		FtpClient ftpClient = (FtpClient) FtpClient.create(url);

		ftpClient.login(username, password.toCharArray());



		for(FTPFile ff:files){
			if(!ff.isDirectory())
			{
				Text fileText = new Text(ff.getName());
				System.out.println(fileText.toString());
				InputStream in = null;

				String remoteAbsoluteFile = ff.getName();
				remoteAbsoluteFile = new String(remoteAbsoluteFile.getBytes("UTF-8"), "ISO-8859-1");
				in = ftpClient.retrieveFileStream(remoteAbsoluteFile);

				byte[] bytes = input2byte(in);

				BytesWritable value = new BytesWritable(bytes);
				writer.append(fileText, value);
			}
			else
			{

			}
		}

	}*/

	 public static byte[] input2byte(InputStream inStream)
		        throws IOException
	 {
		 ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		 byte[] buff = new byte[100];
		 int rc = 0;
		 while ((rc = inStream.read(buff, 0, 100)) > 0)
		 {
	        swapStream.write(buff, 0, rc);
		 }
		 byte[] in2b = swapStream.toByteArray();

		 swapStream.close();

		 return in2b;
	}

}
