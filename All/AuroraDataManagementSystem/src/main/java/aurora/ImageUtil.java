package aurora;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageUtil {
	   
	public void imgHandler(InputStream is,HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		System.err.println("imgHandler±»Ö´ÐÐ");
		
		
		BufferedInputStream bis=new BufferedInputStream(is);
		BufferedOutputStream bos=new BufferedOutputStream(response.getOutputStream());
		
		response.setHeader("Content-Type","image/jpeg");
		
		byte b[]=new byte[1024];
		int read;
		
		try {
			
			while((read=bis.read(b))!=-1){
				bos.write(b, 0, read);
			}
	} catch (Exception e) {
			// TODO: handle exception
		} finally{
			if(bos!=null){
				bos.close();
			}
			if(bis!=null){
				bis.close();
			}
			
		}
		
	}
}
