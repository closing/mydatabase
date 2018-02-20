package common.servlet;

import java.net.URL;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.util.StringFormat;

public class JSPSourceServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws IOException {
        // Get the pathInfo
        String path = req.getPathInfo(); 
        
        // Check for invalid characters in the path. For files under
	// WEB-INF, only accept tag files
        if (path.indexOf("..") != -1 || 
	    (path.toUpperCase().indexOf("WEB-INF") != -1) && 
	    !path.endsWith(".tag")){
            res.sendError(HttpServletResponse.SC_FORBIDDEN,
                "Illegal characters found in path " + path);
            return;
        }

        sendFile(path, res);
    }

    private void copyFile(BufferedReader in, PrintWriter out)
            throws IOException {
        int chars;
        char[] buf = new char[4096];
        while ((chars = in.read(buf, 0, buf.length)) != -1) {
            out.write(buf, 0, chars);
            out.flush();
        }
    }
    
    private void sendFile(String path, HttpServletResponse res) 
	throws IOException {
        URL file = getServletContext().getResource(path);
        if (file == null) { 
            res.sendError(HttpServletResponse.SC_NOT_FOUND,
                path + " not found.");
            return;
        }
        
        res.setStatus(HttpServletResponse.SC_OK);
        /* 
	 * Use "text/html" and convert HTML characters to
         * character entities, to fool IE as explained above
	 */
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        // Add HTML header
        out.println("<html><head><title>" + path + "</title></head>");
        out.println("<body><pre>");
        
        // Read the file and convert it
        BufferedReader in = 
	    new BufferedReader(new InputStreamReader(file.openStream()));
        StringWriter rawText = new StringWriter();
        copyFile(in, new PrintWriter(rawText));
        in.close();
        out.write(StringFormat.toHTMLString(rawText.toString()));
        
        // Add HTML footer
        out.println("</pre></body></html>");
        out.close();
    }
}
