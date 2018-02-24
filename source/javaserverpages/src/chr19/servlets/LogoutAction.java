package chr19.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LogoutAction extends Action {

	public ActionForward perform(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		/**
		 * Invalidate the session, if any. Use false to avoid creating
		 * a new one if it has already timed out.
		 */
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		ActionForward nextPage = mapping.findForward("login");
		return nextPage;
	}
}
