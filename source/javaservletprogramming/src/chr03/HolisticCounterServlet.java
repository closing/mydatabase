package chr03;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

public class HolisticCounterServlet extends HttpServlet {

    static int classCount = 0;
    private int counter;
    static Hashtable instances = new Hashtable();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=utf-8");

        counter++;
        PrintWriter out = response.getWriter();
        out.println("Since loading, this servlet instance has been accessed " + counter + " times.");

        instances.put(this, this);
        out.println("There are currently " + instances.size() + " instances.");

        classCount++;
        out.println("Across all instances, this servlet class has been accessed " + classCount + " times.");
    }
}
