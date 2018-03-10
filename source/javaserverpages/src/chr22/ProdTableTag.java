package chr22;

import java.io.IOException;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;

public class ProdTableTag extends SimpleTagSupport implements DynamicAttributes {

    private List prods;
    private Map dynamicAttrs;

    public void setProds(List prods) {
        this.prods = prods;
    }

    public void setDynamicAttribute(String uri, String localName, Object value) {
        if (dynamicAttrs == null) {
            dynamicAttrs = new HashMap();
        }
        dynamicAttrs.put(localName, value);
    }

    public void doTag() throws JspException, IOException {
        StringBuffer html = new StringBuffer("<table");
        if (dynamicAttrs != null) {
            Iterator i = dynamicAttrs.keySet().iterator();
            while (i.hasNext()) {
                String name = (String) i.next();
                String value = dynamicAttrs.get(name).toString();
                html.append(" ").append(name).append("=\"").append(value).append("\"");
            }
        }
        JspWriter out = getJspContext().getOut();
        out.println(html.toString());
        // Generate rows from product list

        out.println("</table>");
    }
}
