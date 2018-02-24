package chr19.listeners;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;

public class SessionCounterListener implements HttpSessionListener {
	private static final String COUNTER_ATTR = "session_counter";

	public void sessionCreated(HttpSessionEvent hse) {
		int[] counter = getCounter(hse);
		counter[0]++;
	}

	public void sessionDestroyed(HttpSessionEvent hse) {
		int[] counter = getCounter(hse);
		counter[0]--;
	}

	private int[] getCounter(HttpSessionEvent hse) {
		HttpSession session = hse.getSession();
		ServletContext context = session.getServletContext();
		int[] counter = (int[]) context.getAttribute(COUNTER_ATTR);
		if (counter == null) {
			counter = new int[1];
			context.setAttribute(COUNTER_ATTR, counter);
		}
		return counter;
	}
}
