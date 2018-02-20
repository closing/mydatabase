package common.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;
import java.util.Locale;
import java.util.Hashtable;
import java.util.Collection;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.Cookie;

public abstract class CacheHttpServlet extends HttpServlet {

  CacheHttpServletResponse cacheResponse;
  long cacheLastMod = -1;
  String cacheQueryString = null;
  String cachePathInfo = null;
  String cacheServletPath = null;
  Object lock = new Object();

  protected void service(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    // Only do caching for GET requests
    String method = req.getMethod();
    if (!method.equals("GET")) {
      super.service(req, res);
      return;
    }

    // Check the last modified time for this servlet
    long servletLastMod = getLastModified(req);

    // A last modified of -1 means we shouldn't use any cache logic
    if (servletLastMod == -1) {
      super.service(req, res);
      return;
    }

    // If the client sent an If-Modified-Since header equal or after the 
    // servlet's last modified time, send a short "Not Modified" status code
    // Round down to the nearest second since client headers are in seconds
    if ((servletLastMod / 1000 * 1000) <= 
             req.getDateHeader("If-Modified-Since")) {
      res.setStatus(res.SC_NOT_MODIFIED);
      return;
    }

    // Use the existing cache if it's current and valid
    CacheHttpServletResponse localResponseCopy = null;
    synchronized (lock) {
      if (servletLastMod <= cacheLastMod && 
               cacheResponse.isValid() &&
               equal(cacheQueryString, req.getQueryString()) &&
               equal(cachePathInfo, req.getPathInfo()) &&
               equal(cacheServletPath, req.getServletPath())) {
        localResponseCopy = cacheResponse;
      }
    }
    if (localResponseCopy != null) {
      localResponseCopy.writeTo(res);
      return;
    }

    // Otherwise make a new cache to capture the response
    localResponseCopy = new CacheHttpServletResponse(res);
    super.service(req, localResponseCopy);
    synchronized (lock) {
      cacheResponse = localResponseCopy;
      cacheLastMod = servletLastMod;
      cacheQueryString = req.getQueryString();
      cachePathInfo = req.getPathInfo();
      cacheServletPath = req.getServletPath();
    }
  }

  private boolean equal(String s1, String s2) {
    if (s1 == null && s2 == null) {
      return true;
    }
    else if (s1 == null || s2 == null) {
      return false;
    }
    else {
      return s1.equals(s2);
    }
  }
}

class CacheHttpServletResponse implements HttpServletResponse {
  // Store key response variables so they can be set later
  private int status;
  private Hashtable headers;
  private int contentLength;
  private String contentType;
  private String encoding;
  private Locale locale;
  private Vector cookies;
  private boolean didError;
  private boolean didRedirect;
  private boolean gotStream;
  private boolean gotWriter;

  private HttpServletResponse delegate;
  private CacheServletOutputStream out;
  private PrintWriter writer;

  CacheHttpServletResponse(HttpServletResponse res) {
    delegate = res;
    try {
      out = new CacheServletOutputStream(res.getOutputStream());
    }
    catch (IOException e) {
      System.out.println(
        "Got IOException constructing cached response: " + e.getMessage());
    }
    internalReset();
  }

  private void internalReset() {
    status = 200;
    headers = new Hashtable();
    contentLength = -1;
    contentType = null;
    encoding = null;
    locale = null;
    cookies = new Vector();
    didError = false;
    didRedirect = false;
    gotStream = false;
    gotWriter = false;
    out.getBuffer().reset();
  }
  public Collection<String> getHeaders(String name){
      return delegate.getHeaders(name);
  }
  public boolean isValid() {
    // We don't cache error pages or redirects
    return didError != true && didRedirect != true;
  }
  
  private void internalSetHeader(String name, Object value) {
    Vector v = new Vector();
    v.addElement(value);
    headers.put(name, v);
  }

  private void internalAddHeader(String name, Object value) {
    Vector v = (Vector) headers.get(name);
    if (v == null) {
      v = new Vector();
    }
    v.addElement(value);
    headers.put(name, v);
  }

  public void writeTo(HttpServletResponse res) {
    // Write status code
    res.setStatus(status);
    // Write convenience headers
    if (contentType != null) res.setContentType(contentType);
    if (encoding != null) res.setCharacterEncoding(encoding);
    if (locale != null) res.setLocale(locale);
    // Write cookies
    Enumeration enu = cookies.elements();
    while (enu.hasMoreElements()) {
      Cookie c = (Cookie) enu.nextElement();
      res.addCookie(c);
    }
    // Write standard headers
    enu = headers.keys();
    while (enu.hasMoreElements()) {
      String name = (String) enu.nextElement();
      Vector values = (Vector) headers.get(name); // may have multiple values
      Enumeration enu2 = values.elements();
      while (enu2.hasMoreElements()) {
        Object value = enu2.nextElement();
        if (value instanceof String) {
          res.setHeader(name, (String)value);
        }
        if (value instanceof Integer) {
          res.setIntHeader(name, ((Integer)value).intValue());
        }
        if (value instanceof Long) {
          res.setDateHeader(name, ((Long)value).longValue());
        }
      }
    }
    // Write content length
    res.setContentLength(out.getBuffer().size());
    // Write body
    try {
      out.getBuffer().writeTo(res.getOutputStream());
    }
    catch (IOException e) {
      System.out.println(
        "Got IOException writing cached response: " + e.getMessage());
    }
  }

  public ServletOutputStream getOutputStream() throws IOException {
    if (gotWriter) {
      throw new IllegalStateException(
        "Cannot get output stream after getting writer");
    }
    gotStream = true;
    return out;
  }

  public PrintWriter getWriter() throws UnsupportedEncodingException {
    if (gotStream) {
      throw new IllegalStateException(
        "Cannot get writer after getting output stream");
    }
    gotWriter = true;
    if (writer == null) {
      OutputStreamWriter w =
        new OutputStreamWriter(out, getCharacterEncoding());
      writer = new PrintWriter(w, true);  // autoflush is necessary
    }
    return writer;
  }

  public void setContentLength(int len) {
    delegate.setContentLength(len);
    // No need to save the length; we can calculate it later
  }

  public void setContentType(String type) {
    delegate.setContentType(type);
    contentType = type;
  }

  public void setCharacterEncoding(String encoding) {
    delegate.setCharacterEncoding(encoding);
    this.encoding = encoding;
  }

  public String getCharacterEncoding() {
    return delegate.getCharacterEncoding();
  }

  public void setBufferSize(int size) throws IllegalStateException {
    delegate.setBufferSize(size);
  }

  public int getBufferSize() {
    return delegate.getBufferSize();
  }

  public void reset() throws IllegalStateException {
    delegate.reset();
    internalReset();
  }

  public void resetBuffer() throws IllegalStateException {
    delegate.resetBuffer();
    contentLength = -1;
    out.getBuffer().reset();
  }

  public boolean isCommitted() { 
    return delegate.isCommitted();
  }

  public void flushBuffer() throws IOException { 
    delegate.flushBuffer();
  }

  public void setLocale(Locale loc) { 
    delegate.setLocale(loc);
    locale = loc;
  }

  public Locale getLocale() { 
    return delegate.getLocale();
  }

  public void addCookie(Cookie cookie) { 
    delegate.addCookie(cookie);
    cookies.addElement(cookie);
  }

  public boolean containsHeader(String name) { 
    return delegate.containsHeader(name);
  }

  public String getContentType() { 
    return delegate.getContentType();
  }

  /** @deprecated */
  public void setStatus(int sc, String sm) { 
    delegate.setStatus(sc, sm);
    status = sc;
  }

  public void setStatus(int sc) { 
    delegate.setStatus(sc);
    status = sc;
  }

  public void setHeader(String name, String value) { 
    delegate.setHeader(name, value);
    internalSetHeader(name, value);
  }

  public void setIntHeader(String name, int value) { 
    delegate.setIntHeader(name, value);
    internalSetHeader(name, new Integer(value));
  }

  public void setDateHeader(String name, long date) { 
    delegate.setDateHeader(name, date);
    internalSetHeader(name, new Long(date));
  }

  public void sendError(int sc, String msg) throws IOException { 
    delegate.sendError(sc, msg);
    didError = true;
  }

  public void sendError(int sc) throws IOException { 
    delegate.sendError(sc);
    didError = true;
  }

  public void sendRedirect(String location) throws IOException { 
    delegate.sendRedirect(location);
    didRedirect = true;
  }

  public String encodeURL(String url) { 
    return delegate.encodeURL(url);
  }

  public String encodeRedirectURL(String url) { 
    return delegate.encodeRedirectURL(url);
  }

  public void addHeader(String name, String value) { 
    internalAddHeader(name, value);
  }

  public void addIntHeader(String name, int value) { 
    internalAddHeader(name, new Integer(value));
  }

  public void addDateHeader(String name, long value) { 
    internalAddHeader(name, new Long(value));
  }

  /** @deprecated */
  public String encodeUrl(String url) { 
    return this.encodeURL(url);
  }

  /** @deprecated */
  public String encodeRedirectUrl(String url) { 
    return this.encodeRedirectURL(url);
  }
  public Collection<String> getHeaderNames() {
      return delegate.getHeaderNames();
  }
  public String getHeader(String name){
      return delegate.getHeader(name);
  }
  public int getStatus(){
      return delegate.getStatus();
  }
  public void setContentLengthLong(long len){
      delegate.setContentLengthLong(len);
  }
}

class CacheServletOutputStream extends ServletOutputStream {

  ServletOutputStream delegate;
  ByteArrayOutputStream cache;

  CacheServletOutputStream(ServletOutputStream out) {
    delegate = out;
    cache = new ByteArrayOutputStream(4096);
  }

  public ByteArrayOutputStream getBuffer() {
    return cache;
  }

  public void write(int b) throws IOException {
    delegate.write(b);
    cache.write(b);
  }

  public void write(byte b[]) throws IOException {
    delegate.write(b);
    cache.write(b);
  }

  public void write(byte buf[], int offset, int len) throws IOException {
    delegate.write(buf, offset, len);
    cache.write(buf, offset, len);
  }
  public void setWriteListener(WriteListener listener){
      delegate.setWriteListener(listener);
  }
  public boolean isReady(){
      return delegate.isReady();
  }
}
