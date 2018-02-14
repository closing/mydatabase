package common.tags;

import javax.servlet.ServletContext;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.IOException;

public class FileWriteTag extends SimpleTagSupport {

  private String fileName;

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void doTag() throws JspException {
    JspFragment body = getJspBody();
    if (body == null) {
      throw new JspTagException("'fileWrite' used without a body");
    }

    PrintWriter pw = null;
    if (fileName != null && !"log".equals(fileName)) {
      try {
        pw = new PrintWriter(new FileWriter(fileName, true));
      } catch (IOException e) {
        throw new JspTagException("Can not open file " + fileName + " for writing", e);
      }
    }

    ServletContext application = ((PageContext) getJspContext()).getServletContext();
    StringWriter evalResult = new StringWriter();
    try {
      body.invoke(evalResult);
      if (fileName == null) {
        System.out.println(evalResult);
      } else if ("log".equals(fileName)) {
        application.log(evalResult.toString());
      } else {
        pw.print(evalResult);
      }
    } catch (Throwable t) {
      String msg = "Exception in body of " + this.getClass().getName();
      application.log(msg, t);
      throw new JspTagException(msg, t);
    } finally {
      if (pw != null) {
        pw.close();
      }
    }
  }
}
