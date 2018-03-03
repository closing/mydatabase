package chr21;

import java.util.Iterator;
import java.util.Collection;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ClassicSimpleLoopTag extends TagSupport {
    private Iterator iterator;
    private Collection items;
    private String var;

    public void setItems(Collection items) {
        this.items = items;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public int doStartTag() throws JspException {
        iterator = items.iterator();
        if (iterator.hasNext()) {
            pageContext.setAttribute(var, iterator.next());
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    public int doAfterBody() throws JspException {
        if (iterator.hasNext()) {
            pageContext.setAttribute(var, iterator.next());
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }

    public void release() {
        iterator = null;
        items = null;
        var = null;
        super.release();
    }
}
