package chr21;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class IfTag extends SimpleTagSupport {
    private boolean test;

    public void setTest(boolean test) {
	this.test = test;
    }

    public void doTag() throws JspException, IOException {
	if (test && getJspBody() != null) {
	    getJspBody().invoke(null);
	}
    }
}
