package chr07;

import java.io.IOException;
import chr06.MixedMessageBean;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MixedMessageTag extends SimpleTagSupport {
    private MixedMessageBean mmb = new MixedMessageBean();

    private String category;

    public void setCategory(String category) {
        this.category = category;
    }

    public void doTag() throws IOException {
    	mmb.setCategory(category);
    	JspWriter out = getJspContext().getOut();
    	out.println(mmb.getMessage());
    }
}
