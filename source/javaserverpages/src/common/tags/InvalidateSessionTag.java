package common.tags;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;

public class InvalidateSessionTag extends TagSupport {

  public int doEndTag() {
    HttpSession session = pageContext.getSession();
    if (session != null) {
      session.invalidate();
    }
    return EVAL_PAGE;
  }
}
