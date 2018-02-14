package common.tags;

import common.beans.DebugBean;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class DebugTag extends TagSupport {
  static private final String LINE_FEED = System.getProperty("line.separator");
  static private final String RESP_TYPE = "resp";
  static private final String STDOUT_TYPE = "stdout";
  static private final String LOG_TYPE = "log";
  private static final String BEAN_ATTR = "common.beans.DebugBean";
  private String type;

  public void setType(String type) {
    this.type = type;
  }

  public int doEndTag() throws JspException {
    // Get or create the DebugBean
    DebugBean bean = (DebugBean) pageContext.getAttribute(BEAN_ATTR);
    if (bean == null) {
      bean = new DebugBean();
      bean.setPageContext(pageContext);
      pageContext.setAttribute(BEAN_ATTR, bean);
    }

    List debugTypes = getDebugType();
    if (debugTypes != null) {
      Map info = getInfo(type, bean);
      log(type, info, debugTypes);
      if (debugTypes.contains(RESP_TYPE)) {
        try {
          pageContext.getOut().write(toHTMLTable(type, info));
        } catch (IOException e) {
        } // Ignore
      }
    }
    return EVAL_PAGE;
  }

  private List getDebugType() {
    String debug = pageContext.getRequest().getParameter("debug");
    if (debug == null) {
      return null;
    }
    List debugTypes = new ArrayList();
    if (debug.indexOf(RESP_TYPE) != -1) {
      debugTypes.add(RESP_TYPE);
    }
    if (debug.indexOf(STDOUT_TYPE) != -1) {
      debugTypes.add(STDOUT_TYPE);
    }
    if (debug.indexOf(LOG_TYPE) != -1) {
      debugTypes.add(LOG_TYPE);
    }
    return debugTypes;
  }

  private Map getInfo(String type, DebugBean bean) throws JspTagException {
    Map info = null;
    if ("requestInfo".equals(type)) {
      info = bean.getRequestInfo();
    } else if ("headers".equals(type)) {
      info = bean.getHeaders();
    } else if ("cookies".equals(type)) {
      info = bean.getCookies();
    } else if ("params".equals(type)) {
      info = bean.getParams();
    } else if ("pageScope".equals(type)) {
      info = bean.getPageScope();
    } else if ("requestScope".equals(type)) {
      info = bean.getRequestScope();
    } else if ("sessionScope".equals(type)) {
      info = bean.getSessionScope();
    } else if ("applicationScope".equals(type)) {
      info = bean.getApplicationScope();
    } else {
      throw new JspTagException("ora:debug action: Invalid type: " + type);
    }
    return info;
  }

  private void log(String propName, String msg, List debugTypes) {
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    msg = "[DebugBean] " + request.getRequestURI() + " : " + propName + " : " + msg;
    if (debugTypes.contains(STDOUT_TYPE)) {
      System.out.println(msg);
    }
    if (debugTypes.contains(LOG_TYPE)) {
      pageContext.getServletContext().log(msg);
    }
  }

  private void log(String propName, Map values, List debugTypes) {
    log(propName, toTabbedTable(values), debugTypes);
  }

  private String toHTMLTable(String propName, Map values) {
    StringBuffer tableSB = new StringBuffer("<table border=\"1\">");
    tableSB.append("<caption align=\"top\"><b>").append(propName).append("</b></caption>");
    Iterator keys = values.keySet().iterator();
    while (keys.hasNext()) {
      String key = (String) keys.next();
      tableSB.append("<tr><td>").append(key).append("</td><td>").append(values.get(key)).append("</td></tr>");
    }
    tableSB.append("</table>");
    return tableSB.toString();
  }

  private String toTabbedTable(Map values) {
    StringBuffer tableSB = new StringBuffer();
    Iterator keys = values.keySet().iterator();
    while (keys.hasNext()) {
      String key = (String) keys.next();
      tableSB.append(LINE_FEED).append(key).append("\t\t").append(values.get(key));
    }
    return tableSB.toString();
  }
}
