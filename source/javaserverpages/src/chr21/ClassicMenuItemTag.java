package chr21;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import common.util.StringFormat;

public class ClassicMenuItemTag extends BodyTagSupport {
    private String page;

    public void setPage(String page) {
        this.page = page;
    }

    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String requestURI = request.getServletPath();
        // Convert the specified page URI to a context-relative URI
        String pageURI = StringFormat.toContextRelativeURI(page, requestURI);

        StringBuffer text = null;
        String body = getBodyContent().getString();
        if (requestURI.equals(pageURI)) {
            text = new StringBuffer(body);
        } else {
            // Add the text as an HTML reference if page is not current page
            String contextPath = request.getContextPath();
            String uri = contextPath + pageURI;
            HttpServletResponse res = (HttpServletResponse) pageContext.getResponse();
            text = new StringBuffer("<a href=\"");
            text.append(res.encodeURL(uri)).append("\">").append(body).append("</a>");
        }
        try {
            JspWriter out = getPreviousOut();
            out.print(text);
        } catch (IOException e) {
        }
        return EVAL_PAGE;
    }

    public void release() {
        page = null;
        super.release();
    }
}
