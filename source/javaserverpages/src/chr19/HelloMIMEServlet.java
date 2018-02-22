package chr19;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloMIMEServlet extends HttpServlet {
    private static final int TEXT_TYPE = 0;
    private static final int IMAGE_TYPE = 1;

    public void doGet(HttpServletRequest request, 
		      HttpServletResponse response) 
        throws ServletException, IOException {

        String greeting = "Hello World!";
        int majorType = TEXT_TYPE;
        String type = request.getParameter("type");
        if ("plain".equals(type)) {
            response.setContentType("text/plain");
        }
        else if ("html".equals(type)) {
            response.setContentType("text/html");
            greeting = "<html><body><h1>" + greeting +
                "</h1></body></html>";
        }
        else if ("image".equals(type)) {
            response.setContentType("image/gif");
            majorType = IMAGE_TYPE;
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
			       "Please specify a valid response type");
            return;
        }

        if (majorType == TEXT_TYPE) {
            PrintWriter out = response.getWriter();
            out.println(greeting);
        }
        else {
            OutputStream os = response.getOutputStream();
            ServletContext application = getServletContext();
            InputStream is = 
                application.getResourceAsStream("/ora.gif");
            copyStream(is, os);
        }
    }

    private void copyStream(InputStream in, OutputStream out) 
	throws IOException {
        int bytes;
        byte[] b = new byte[4096];
        
        while ((bytes = in.read(b, 0, b.length)) != -1) {
            out.write(b, 0, bytes);
            out.flush();
        }
    }
}
