package chr21;

import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class HelloTag extends SimpleTagSupport {
    private String name = "World";
    public void setName(String value){
        this.name=value;
    }
    public void doTag () throws IOException {
        getJspContext().getOut().println("Hello " + name);        
    }
}