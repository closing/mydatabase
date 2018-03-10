package chr23;

import java.util.Map;
import java.util.Iterator;
import java.lang.reflect.Array;
import javax.servlet.jsp.jstl.core.LoopTagSupport;
import common.util.StringFormat;

public class ForEachOptionTag extends LoopTagSupport {
    private Map options;
    private String[] selections;
    private Iterator iterator;

    public void setOptions(Map options) {
        this.options = options;
    }

    public void setSelections(String[] selections) {
        this.selections = selections;
    }

    protected void prepare() {
        if (options != null) {
            iterator = options.entrySet().iterator();
        }
    }

    protected Object next() {
        Map.Entry me = (Map.Entry) iterator.next();
        String text = (String) me.getKey();
        String value = (String) me.getValue();
        boolean selected = isSelected(value);
        return new OptionBean(text, value, selected);
    }

    protected boolean hasNext() {
        if (iterator == null) {
            return false;
        } else {
            return iterator.hasNext();
        }
    }

    private boolean isSelected(String value) {
        return StringFormat.isValidString(value, selections, false);
    }

    public class OptionBean {
        private String text;
        private String value;
        private boolean selected;

        public OptionBean(String text, String value, boolean selected) {
            this.text = text;
            this.value = value;
            this.selected = selected;
        }

        public String getText() {
            return text;
        }

        public String getValue() {
            return value;
        }

        public boolean isSelected() {
            return selected;
        }
    }
}
