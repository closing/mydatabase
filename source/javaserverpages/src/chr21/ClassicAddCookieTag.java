package chr21;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import common.util.CookieUtils;

public class ClassicAddCookieTag extends TagSupport {
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

    public int doEndTag() throws JspException {
        int maxAge = -1;
        if (maxAgeString != null) {
            try {
                maxAge = Integer.valueOf(maxAgeString).intValue();
            } catch (NumberFormatException e) {
                throw new JspTagException("Invalid maxAge", e);
            }
        }
        CookieUtils.sendCookie(name, value, maxAge, (HttpServletResponse) pageContext.getResponse());
        return EVAL_PAGE;
    }

    public void release() {
        name = null;
        value = null;
        maxAgeString = null;
        super.release();
    }
}