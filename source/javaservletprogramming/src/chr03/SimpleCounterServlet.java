package chr03;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleCounterServlet extends HttpServlet {

    private int counter;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=utf-8");
        counter++;

        PrintWriter out = response.getWriter();
        out.println("Since loading, this servlet has been accessed " + counter + " times.");
    }
}
