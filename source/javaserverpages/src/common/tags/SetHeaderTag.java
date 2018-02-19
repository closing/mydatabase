package common.tags;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspException;

public class SetHeaderTag extends SimpleTagSupport {
    private String name;
    private String value;

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void doTag() throws JspException {
        if (value.length() == 0 || name.length() == 0) {
            throw new JspTagException("setHeader: " +
                 "one of the attributes is not set");
        }
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletResponse response = 
            (HttpServletResponse) pageContext.getResponse();
        response.setHeader(name, value);
    }
}
