import java.util.Locale;
import javax.servlet.ServletOutputStream;
import java.io.PrintWriter;

import java.lang.System;

import java.io.OutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.File;

import javax.servlet.ServletResponse;


public class ResponseFacade {
	private ServletResponse response = null;
	
	public ResponseFacade(Response response) {
		this.response = response;
	}
	
	public void flushBuffer()  throws IOException {
		response.flushBuffer();
	}
	public int getBufferSize() {
		return response.getBufferSize();
	}
	public String getCharacterEncoding() {
		return response.getCharacterEncoding();
	}
	public String getContentType() {
		return getContentType();
	}
	public Locale getLocale() {
		return response.getLocale(); 
	}
	public ServletOutputStream getOutputStream() throws IOException {
		return response.getOutputStream(); 
	}
	public PrintWriter getWriter() throws IOException {
		return response.getWriter(); 
	}
	public boolean isCommitted() {
		return response.isCommitted(); 
	}
	public void reset() {
		response.reset();
	}
	public void resetBuffer() {
		response.resetBuffer();
	}
	public void setBufferSize(int size) {
		response.setBufferSize(size);
	}
	public void setCharacterEncoding(String charset) {
		response.setCharacterEncoding(charset);
	}
	public void setContentLength(int len) {
		response.setContentLength(len);
	
	}
	public void setContentLengthLong(long len) {
		response.setContentLengthLong(len);
	}
	public void setContentType(String type) {
		response.setContentType(type);
	}
	public void setLocale(Locale loc) {
		response.setLocale(loc);
	}

}
