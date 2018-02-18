package chr03;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;

public class SingleThreadConnectionServlet extends HttpServlet implements SingleThreadModel {

    private Connection conn = null;

    public void init() throws ServletException {
        try {
            establishConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
    }

    public void destroy() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignore) {
            }
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=utf-8");

        PrintWriter out = response.getWriter();
        try {
            Statement stmt = conn.createStatement();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ignore) {
            }
        }
    }

    private void establishConnection() throws SQLException {

    }
}
