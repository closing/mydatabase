package common.servlet;

import java.io.InterruptedIOException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;
import java.util.Vector;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.Socket;
import java.net.URLConnection;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.Servlet;

public class ServletUtils {

    public static void returnFile(String filename, OutputStream out) throws FileNotFoundException, IOException {
        // A FileInputStream is for bytes
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filename);
            byte[] buf = new byte[4 * 1024]; // 4K buffer
            int bytesRead;
            while ((bytesRead = fis.read(buf)) != -1) {
                out.write(buf, 0, bytesRead);
            }
        } finally {
            if (fis != null)
                fis.close();
        }
    }

    public static void returnURL(URL url, OutputStream out) throws IOException {
        InputStream in = url.openStream();
        byte[] buf = new byte[4 * 1024]; // 4K buffer
        int bytesRead;
        while ((bytesRead = in.read(buf)) != -1) {
            out.write(buf, 0, bytesRead);
        }
    }

    public static void returnURL(URL url, Writer out) throws IOException {
        // Determine the URL's content encoding
        URLConnection con = url.openConnection();
        con.connect();
        String encoding = con.getContentEncoding();

        // Construct a Reader appropriate for that encoding
        BufferedReader in = null;
        if (encoding == null) {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(url.openStream(), encoding));
        }
        char[] buf = new char[4 * 1024]; // 4Kchar buffer
        int charsRead;
        while ((charsRead = in.read(buf)) != -1) {
            out.write(buf, 0, charsRead);
        }
    }

    public static String getStackTraceAsString(Throwable t) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(bytes, true);
        t.printStackTrace(writer);
        return bytes.toString();
    }

    public static Servlet getServlet(String name, ServletRequest req, ServletContext context) {
        try {
            // Try getting the servlet the old fashioned way
            Servlet servlet = context.getServlet(name);
            if (servlet != null)
                return servlet;

            // If getServlet() returned null, we have to load it ourselves.
            // Do this by making an HTTP GET request to the servlet.
            // Use a raw socket connection so we can set a timeout.
            Socket socket = new Socket(req.getServerName(), req.getServerPort());
            socket.setSoTimeout(4000); // wait up to 4 secs for a response
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET /servlet/" + name + " HTTP/1.0"); // the request
            out.println();
            try {
                socket.getInputStream().read(); // Even one byte means its loaded
            } catch (InterruptedIOException e) {
                /* timeout: ignore, hope for best */ }
            out.close();

            // Try getting the servlet again.
            return context.getServlet(name);
        } catch (Exception e) {
            // If there's any problem, return null.
            return null;
        }
    }

    public static String[] split(String str, String delim) {
        // Use a Vector to hold the splittee strings
        Vector v = new Vector();

        // Use a StringTokenizer to do the splitting
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        while (tokenizer.hasMoreTokens()) {
            v.addElement(tokenizer.nextToken());
        }

        String[] ret = new String[v.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (String) v.elementAt(i);
        }

        return ret;
    }

    public static URL getResource(ServletContext context, String resource) throws IOException {
        // Short-circuit if resource is null
        if (resource == null) {
            throw new FileNotFoundException("Requested resource was null (passed in null)");
        }

        if (resource.endsWith("/") || resource.endsWith("\\") || resource.endsWith(".")) {
            throw new MalformedURLException("Path may not end with a slash or dot");
        }

        if (resource.indexOf("..") != -1) {
            throw new MalformedURLException("Path may not contain double dots");
        }

        String upperResource = resource.toUpperCase();
        if (upperResource.startsWith("/WEB-INF") || upperResource.startsWith("/META-INF")) {
            throw new MalformedURLException("Path may not begin with /WEB-INF or /META-INF");
        }

        if (upperResource.endsWith(".JSP")) {
            throw new MalformedURLException("Path may not end with .jsp");
        }

        // Convert the resource to a URL
        URL url = context.getResource(resource);
        if (url == null) {
            throw new FileNotFoundException("Requested resource was null (" + resource + ")");
        }

        return url;
    }
}
