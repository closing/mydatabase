package chr21;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class AbortPageTag extends SimpleTagSupport {
    public void doTag() throws JspException {
	throw new SkipPageException();
    }
}
