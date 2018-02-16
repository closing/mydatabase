package common.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

public class IfUserInRoleTag extends ConditionalTagSupport {
  private String value;

  public void setValue(String value) {
    this.value = value;
  }

  public boolean condition() throws JspTagException {
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    return request.isUserInRole(value);
  }
}
