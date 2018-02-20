package common.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import common.util.StringFormat;

public class IfValidEmailAddrTag extends ConditionalTagSupport {
  private String value;

  public void setValue(String value) {
    this.value = value;
  }

  public boolean condition() throws JspTagException {
    return StringFormat.isValidEmailAddr(value);
  }
}
