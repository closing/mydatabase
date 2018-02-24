package chr19.servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import chr19.beans.EmployeeBean;
import chr19.beans.EmployeeRegistryBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class UpdateProfileAction extends Action {

    public ActionForward perform(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        if (request.getMethod().equals("POST")) {
            String[] projects = request.getParameterValues("projects");
            if (projects == null) {
                projects = new String[0];
            }
            HttpSession session = request.getSession();
            EmployeeBean emp = (EmployeeBean) session.getAttribute("validUser");
            emp.setProjects(projects);
            EmployeeRegistryBean empReg = (EmployeeRegistryBean) getServlet().getServletContext()
                    .getAttribute("empReg");
            try {
                empReg.saveEmployee(emp);
            } catch (SQLException e) {
                throw new ServletException("Database error", e);
            }
        }
        ActionForward nextPage = mapping.findForward("main");
        return nextPage;
    }
}
