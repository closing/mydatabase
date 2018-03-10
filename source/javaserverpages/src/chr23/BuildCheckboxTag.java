package chr23;

import java.io.IOException;
import java.util.Map;
import java.lang.reflect.Array;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.jstl.core.LoopTag;
import common.util.StringFormat;

public class BuildCheckboxTag extends TagSupport {
    private String name;
    private String[] selections;

    public void setName(String name) {
        this.name = name;
    }

    public void setSelections(String[] selections) {
        this.selections = selections;
    }

    public int doEndTag() throws JspException {
        LoopTag parent = (LoopTag) findAncestorWithClass(this, LoopTag.class);
        if (parent == null) {
            throw new JspTagException("buildCheckbox: invalid parent");
        }

        Map.Entry current = (Map.Entry) parent.getCurrent();
        String text = (String) current.getKey();
        String value = (String) current.getValue();
        JspWriter out = pageContext.getOut();
        StringBuffer checkbox = new StringBuffer("<input type=\"checkbox\"");
        checkbox.append(" name=\"").append(name).append("\"").append(" value=\"").append(value).append("\"");
        if (isSelected(value, selections)) {
            checkbox.append(" checked");
        }
        checkbox.append(">").append(text);
        try {
            out.write(checkbox.toString());
        } catch (IOException e) {
        }
        return EVAL_PAGE;
    }

    private boolean isSelected(String value, String[] selections) {
        return StringFormat.isValidString(value, selections, false);
    }
}
