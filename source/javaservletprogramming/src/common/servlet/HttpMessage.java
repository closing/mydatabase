package common.servlet;

import java.io.*;
import java.net.*;
import java.util.*;

public class HttpMessage {

    URL servlet = null;
    Hashtable headers = null;

    /**
     * Constructs a new HttpMessage that can be used to communicate with the 
     * servlet at the specified URL.
     *
     * @param servlet the server resource (typically a servlet) with which 
     * to communicate
     */
    public HttpMessage(URL servlet) {
        this.servlet = servlet;
    }

    public InputStream sendGetMessage() throws IOException {
        return sendGetMessage(null);
    }

    public InputStream sendGetMessage(Properties args) throws IOException {
        String argString = ""; // default

        if (args != null) {
            argString = "?" + toEncodedString(args);
        }
        URL url = new URL(servlet.toExternalForm() + argString);

        // Turn off caching
        URLConnection con = url.openConnection();
        con.setUseCaches(false);

        // Send headers
        sendHeaders(con);

        return con.getInputStream();
    }

    public InputStream sendPostMessage() throws IOException {
        return sendPostMessage(null);
    }

    public InputStream sendPostMessage(Properties args) throws IOException {
        String argString = ""; // default
        if (args != null) {
            argString = toEncodedString(args); // notice no "?"
        }

        URLConnection con = servlet.openConnection();

        // Prepare for both input and output
        con.setDoInput(true);
        con.setDoOutput(true);

        // Turn off caching
        con.setUseCaches(false);

        // Work around a Netscape bug
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Send headers
        sendHeaders(con);

        // Write the arguments as post data
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(argString);
        out.flush();
        out.close();

        return con.getInputStream();
    }

    public InputStream sendPostMessage(Serializable obj) throws IOException {
        URLConnection con = servlet.openConnection();

        // Prepare for both input and output
        con.setDoInput(true);
        con.setDoOutput(true);

        // Turn off caching
        con.setUseCaches(false);

        // Set the content type to be application/x-java-serialized-object
        con.setRequestProperty("Content-Type", "application/x-java-serialized-object");

        // Send headers
        sendHeaders(con);

        // Write the serialized object as post data
        ObjectOutputStream out = new ObjectOutputStream(con.getOutputStream());
        out.writeObject(obj);
        out.flush();
        out.close();

        return con.getInputStream();
    }

    public void setHeader(String name, String value) {
        if (headers == null) {
            headers = new Hashtable();
        }
        headers.put(name, value);
    }

    private void sendHeaders(URLConnection con) {
        if (headers != null) {
            Enumeration enumm = headers.keys();
            while (enumm.hasMoreElements()) {
                String name = (String) enumm.nextElement();
                String value = (String) headers.get(name);
                con.setRequestProperty(name, value);
            }
        }
    }

    public void setCookie(String name, String value) {
        if (headers == null) {
            headers = new Hashtable();
        }
        String existingCookies = (String) headers.get("Cookie");
        if (existingCookies == null) {
            setHeader("Cookie", name + "=" + value);
        } else {
            setHeader("Cookie", existingCookies + "; " + name + "=" + value);
        }
    }

    public void setAuthorization(String name, String password) {
        String authorization = Base64Encoder.encode(name + ":" + password);
        setHeader("Authorization", "Basic " + authorization);
    }

    private String toEncodedString(Properties args) {
        StringBuffer buf = new StringBuffer();
        Enumeration names = args.propertyNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = args.getProperty(name);
            buf.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value));
            if (names.hasMoreElements())
                buf.append("&");
        }
        return buf.toString();
    }
}
