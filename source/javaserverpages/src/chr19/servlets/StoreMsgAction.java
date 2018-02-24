package chr19.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import chr19.beans.NewsBean;
import chr19.beans.NewsItemBean;
import chr19.beans.EmployeeBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
public class StoreMsgAction extends Action {
    public ActionForward perform(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        if (request.getMethod().equals("POST")) {
            String category = request.getParameter("category");
            String msg = request.getParameter("msg");
            if (category == null || msg == null) {
                throw new ServletException("Missing message info");
            }
            HttpSession session = request.getSession();
            EmployeeBean emp = (EmployeeBean) session.getAttribute("validUser");
            NewsItemBean item = new NewsItemBean();
            item.setCategory(category);
            item.setMsg(msg);
            item.setPostedBy(emp.getFirstName() + " " + emp.getLastName());
            NewsBean news = (NewsBean) servlet.getServletContext().getAttribute("news");
            news.setNewsItem(item);
        }
        ActionForward nextPage = mapping.findForward("main");
        return nextPage;
    }
}
