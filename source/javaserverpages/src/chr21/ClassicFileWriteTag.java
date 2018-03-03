package chr21;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

public class ClassicFileWriteTag extends BodyTagSupport implements TryCatchFinally {

    private String fileName;
    private PrintWriter pw;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int doStartTag() throws JspException {
        if (fileName != null && !"log".equals(fileName)) {
            try {
                pw = new PrintWriter(new FileWriter(fileName, true));
            } catch (IOException e) {
                throw new JspException("Can not open file " + fileName + " for writing", e);
            }
        }
        return EVAL_BODY_BUFFERED;
    }

    public int doAfterBody() throws JspException {
        String content = bodyContent.getString();
        if (fileName == null) {
            System.out.println(content);
        } else if ("log".equals(fileName)) {
            ServletContext application = pageContext.getServletContext();
            application.log(content);
        } else {
            pw.print(bodyContent.getString());
        }
        return SKIP_BODY;
    }

    public void doCatch(Throwable t) throws Throwable {
        ServletContext application = pageContext.getServletContext();
        application.log("Exception in body of " + this.getClass().getName(), t);
        throw t;
    }

    public void doFinally() {
        if (pw != null) {
            pw.close();
        }
    }
}
