package chr19.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import chr19.beans.EmployeeBean;
import chr19.beans.NewsBean;
import chr19.beans.EmployeeRegistryBean;
import chr19.sql.DataSourceWrapper;

public class ResourceManagerListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();

        String driverClass = application.getInitParameter("driverClass");
        String jdbcURL = application.getInitParameter("jdbcURL");
        String jdbcUser = application.getInitParameter("jdbcUser");
        String jdbcPassword = application.getInitParameter("jdbcPassword");

        DataSourceWrapper ds = null;
        try {
            ds = new DataSourceWrapper();
            ds.setDriverClassName(driverClass);
            ds.setUrl(jdbcURL);
            ds.setUser(jdbcUser);
            ds.setPassword(jdbcPassword);
        } catch (Exception e) {
            application.log("Error creating connection pool: ", e);
        }
        EmployeeRegistryBean empReg = new EmployeeRegistryBean();
        empReg.setDataSource(ds);
        application.setAttribute("empReg", empReg);

        NewsBean news = new NewsBean();
        application.setAttribute("news", news);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        application.removeAttribute("empReg");
        application.removeAttribute("news");
    }
}
