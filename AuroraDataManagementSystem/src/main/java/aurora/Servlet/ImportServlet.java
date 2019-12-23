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

     // 上传文件存储目录
     private static final String UPLOAD_DIRECTORY = "upload";

     // 上传配置
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
		 // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        String uploadPath = getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;


        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        String ufileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + ufileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
//	                    BufferedImage image = ImageIO.read(new FileInputStream(filePath)); //用ImageIO将本地图片文件转换成虚拟图片信息
//	                    ByteArrayOutputStream bos = new ByteArrayOutputStream();      //字节输出流
//	                    ImageIO.write(image, "bmp", bos);      //将虚拟图片信息写入到字节输出流中
//	                    byte[] b = bos.toByteArray();//generate byte[]     //输出流将字节信息输出到byte数组b中

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

	                    //*************************************预处理**************************************
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

	                    BufferedImage bi = new BufferedImage(512, 512,BufferedImage.TYPE_INT_RGB);   //在内存中生成512x512的图像缓冲区，TYPE_INT_RGB表示一个图像，该图像具有整数像素的 8 位 RGB颜色

	            		Graphics g = bi.getGraphics();

	            		fileName=ufileName;    //获取文件名
	            		fileName=fileName.substring(fileName.lastIndexOf("/")+1);

	            		g.clearRect(0, 0, 2*r, 2*r);   //清除出一个440x440的矩形区域

	            		if(fileName.charAt(0) == 'E' && fileName.charAt(1) == 'N' )
	            		{
	            			if (fileName.charAt(8) == 'R')
	            			{   //判断文件属于哪一个波段

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
	            			{   //判断文件属于哪一个波段
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
	            						int p =result[j][i];  //进行读出文件字节的转秩
	            						I =(p-noise)*K;
	            						I = (I/LimRayleigh);
	            						if(Math.pow((j-y0),2)+Math.pow((i-x0),2)>=Math.pow(r,2)) //半径r圆形区域外的区域填充黑色
	            						I=0;
	            						else
	            						{if(I<0)
	            							I=0;
	            						if(I>1)
	            							I=1;
	            						}
	            						g.setColor(new Color(I, I, I));   //此处为选则画点的颜色，R=G=B=0为黑色。R=G=B=1为白色
	            						g.drawLine(i,j , i, j);    //在（i,j）处以上面的颜色画点

	            			        }
	            			   }
	            		}
	            	    catch (Exception e1)
	            	    {
	            				// TODO Auto-generated catch block
	            				e1.printStackTrace();
	            		}

	                    BufferedImage img =new BufferedImage(512 ,512,BufferedImage.TYPE_INT_RGB); //对图像进行-61.1度的逆时针旋转
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

	            	    //*************************************生成缩略图***********************************
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

	        	        //*******************************提取keogram条列***********************************
	        	        int keoB[] = new int[440];
	        	        for(int i=0;i<440;i++)
	        	        {
	        	        	keoB[i] = img1.getRGB(221, i);
	        	        }
	        	        //******************************************************************************

	        	        //**************************************生成数据库需要保存的信息*************************
	        	        //name:ufilename,time:time,band:band
	        	        //original
	        	        //thumb
	        	        //keogram:keoB[i]
	        	        //feature:无

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
//	                    metadbo.put("rawpath", request.getParameter("uploadFile"));  考虑到Redis里面保存了路径，不需要
	                    metadbo.put("time", time);
	                    metadbo.put("band",waveband);
	                    metadbo.put("manualtype", "0");
	                    meta.insert(metadbo);

	                    BasicDBObject picdbo = new BasicDBObject();
	                    ByteArrayOutputStream oribos = new ByteArrayOutputStream();      //字节输出流
	                    ImageIO.write(img1, "bmp", oribos);      //将虚拟图片信息写入到字节输出流中
	                    byte[] orib = oribos.toByteArray();//generate byte[]     //输出流将字节信息输出到byte数组b中
	                    picdbo.put("name",ufileName);
	                    picdbo.put("rawpic",orib);
	                    original.insert(picdbo);

	                    BasicDBObject thumbdbo = new BasicDBObject();
	                    ByteArrayOutputStream thumbbos = new ByteArrayOutputStream();      //字节输出流
	                    ImageIO.write(target, "bmp", thumbbos);      //将虚拟图片信息写入到字节输出流中
	                    byte[] thumbb = thumbbos.toByteArray();//generate byte[]     //输出流将字节信息输出到byte数组b中
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
                            "文件上传成功!");
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message",
                    "错误信息: " + ex.getMessage());
        }
        // 跳转到 message.jsp
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

//******************************旧import***********************************************************************
//		//DB初始化
//		Mongo mg = new Mongo();
//
//        DB db = mg.getDB("aurora");
//
//        DBCollection meta = db.getCollection("Aurora.Meta");
//        DBCollection original = db.getCollection("Aurora.Image");
//
//        //原始信息提取
//
//        File root = new File("D:\\data\\");
//        File[] files = root.listFiles();
//
//        for(File file:files)
//        {
//        	//更新Aurora。Meta
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
//             //更新Aurora.Image
//             BufferedImage image = ImageIO.read(new FileInputStream(file)); //用ImageIO将本地图片文件转换成虚拟图片信息
//             ByteArrayOutputStream bos = new ByteArrayOutputStream();      //字节输出流
//             ImageIO.write(image, "bmp", bos);      //将虚拟图片信息写入到字节输出流中
//             byte[] b = bos.toByteArray();//generate byte[]     //输出流将字节信息输出到byte数组b中
//             picdbo.put("name",filename.substring(0,16));
//             picdbo.put("rawpic",b);
//             original.insert(picdbo);
//
//        }






//--------------------------------------------图片转换为字节-----------------------------------------------------
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
