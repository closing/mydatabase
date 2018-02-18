package chr03;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

public class InitCounterServlet extends HttpServlet {

    private int counter;

    public void init() throws ServletException {
        String initial = getInitParameter("initial");
        try {
            counter = Integer.parseInt(initial);
        } catch (NumberFormatException e) {
            counter = 0;
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=utf-8");

        counter++;
        PrintWriter out = response.getWriter();
        out.println("Since loading (and with a possible initialization");
        out.println("parameter firgured in), this servlet has been accessed ");
        out.println(counter + " times.");
    }
}
