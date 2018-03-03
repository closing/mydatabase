package chr21;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SimpleLoopTag extends SimpleTagSupport {
    private Collection items;
    private String var;
    
    public void setItems(Collection items) {
        this.items = items;
    }
    
    public void setVar(String var) {
        this.var = var;
    }

    public void doTag() throws JspException, IOException {
	JspFragment body = getJspBody();
	if (body != null) {
	    Iterator i = items.iterator();
	    while (i.hasNext()) {
		Object currValue = i.next();
		getJspContext().setAttribute(var, currValue);
		body.invoke(null);
	    }
	}
    }
}
