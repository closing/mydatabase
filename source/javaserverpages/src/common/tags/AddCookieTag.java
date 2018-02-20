package common.tags;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;
import javax.servlet.http.HttpServletResponse;
import common.util.CookieUtils;

public class AddCookieTag extends SimpleTagSupport {
  private String name;
  private String value;
  private String maxAgeString;

  public void setName(String name) {
    this.name = name;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setMaxAge(String maxAgeString) {
    this.maxAgeString = maxAgeString;
  }

  public void doTag() throws JspException {
    int maxAge = -1;
    if (maxAgeString != null) {
      try {
        maxAge = Integer.valueOf(maxAgeString).intValue();
      } catch (NumberFormatException e) {
        throw new JspTagException("Invalid maxAge", e);
      }
    }
    PageContext pageContext = (PageContext) getJspContext();
    HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
    CookieUtils.sendCookie(name, value, maxAge, response);
  }
}
