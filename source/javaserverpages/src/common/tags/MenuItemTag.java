package common.tags;

import java.io.StringWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import util.StringFormat;

public class MenuItemTag extends SimpleTagSupport {
    private String page;

    public void setPage(String page) {
        this.page = page;
    }
    
    public void doTag() throws JspException, IOException {
        JspFragment body = getJspBody();
        if (body == null) {
	    throw new JspTagException("'menuItem' used without a body");
	}
	
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = 
            (HttpServletRequest) pageContext.getRequest();
        String requestURI = request.getServletPath();
        // Convert the specified page URI to a context-relative URI
        String pageURI = StringFormat.toContextRelativeURI(page, requestURI);

        if (requestURI.equals(pageURI)) {
            // Add the body as-is
            body.invoke(null);
        }
        else {
            // Add the body as the text of an HTML link to page
            String uri = request.getContextPath() + pageURI;
            HttpServletResponse response = 
                (HttpServletResponse) pageContext.getResponse();

            StringWriter evalResult = new StringWriter();
            StringBuffer buff = evalResult.getBuffer();
            buff.append("<a href=\"").append(response.encodeURL(uri)).
                append("\">");
            body.invoke(evalResult);
            buff.append("</a>");
            getJspContext().getOut().print(buff);
        }
    }
}
