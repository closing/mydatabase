package common.tags;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class NoCacheTag extends TagSupport {

    public int doEndTag() throws JspException {
        HttpServletResponse response = 
            (HttpServletResponse) pageContext.getResponse();
        response.addHeader("Pragma", "No-Cache");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.addHeader("Cache-Control", "pre-check=0, post-check=0");
        response.setDateHeader("Expires", 0);
        return EVAL_PAGE;
    }
}
