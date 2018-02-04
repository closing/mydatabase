package mypack;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class OnlineCounterListener  implements HttpSessionListener{
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        ServletContext ctx = session.getServletContext();
        Integer counter = (Integer)ctx.getAttribute("counter");
        if (counter == null) {
            counter = new Integer(1);
        }
        else {
            counter = new Integer(counter + 1);
        }
        ctx.setAttribute("counter",counter);

        session.setMaxInactiveInterval(60);
        System.out.println("A new session is created.");
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession  session = event.getSession();
        ServletContext ctx = session.getServletContext();
        Integer counter = (Integer)ctx.getAttribute("counter");
        if (counter == null) {
            return;
        }
        else {
            counter = new Integer(counter-1);
        }
        ctx.setAttribute("counter",counter);
        System.out.println("A new session is to be destroyed.");
    }
}