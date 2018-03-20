package chr04;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileLocationServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        if (req.getPathInfo() != null) {
            out.println("The file \"" + req.getPathInfo() + "\"");
            out.println("Is stored at \"" + req.getPathTranslated() + "\"");
        } else {
            out.println("Path info is null, no file to lookup");
        }
    }
}
