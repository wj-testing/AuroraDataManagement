package aurora.Servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class showkeogramServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("¿ªÊ¼ÇëÇókeogram");
		response.setHeader("Content-Type","image/jped");

		  byte[][] image=(byte[][])request.getSession().getAttribute("image");
		  List<String> title = (List<String>) request.getSession().getAttribute("title");
		  List<String> name = (List<String>) request.getSession().getAttribute("name");
		  for(int i = 0;i<name.size();i++)
		  {
			  System.out.println( name.get(i) );
		  }

		  byte[] keogram = (byte[]) request.getSession().getAttribute("keogram");
		  System.out.println(Arrays.toString(keogram));
          ServletOutputStream os = response.getOutputStream();
          os.write(keogram);
//        os.flush();
        os.close();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

			this.doPost(request, response);

	}

}
