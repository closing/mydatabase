package chr19.beans;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

public class SQLCommandBean {
    private Connection conn;
    private String sqlValue;
    private List values;

    public void setConnection(Connection conn) {
        this.conn = conn;
    }
  
    public void setSqlValue(String sqlValue) {
        this.sqlValue = sqlValue;
    }
    
    public void setValues(List values) {
        this.values = values;
    }
  
    public Result executeQuery() throws SQLException {
        Result result = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        try {
            if (values != null && values.size() > 0) {
                // Use a PreparedStatement and set all values
                pstmt = conn.prepareStatement(sqlValue);
                setValues(pstmt, values);
                rs = pstmt.executeQuery();
            }
            else {
                // Use a regular Statement
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sqlValue);
            }
            result = ResultSupport.toResult(rs);
        }
        finally {
            if (rs != null) {
                try {rs.close();} catch (SQLException e) {}
            }
            if (stmt != null) {
                try {stmt.close();} catch (SQLException e) {}
            }
            if (pstmt != null) {
                try {pstmt.close();} catch (SQLException e) {}
            }
        }
        return result;
    }

    public int executeUpdate() throws SQLException {
        int noOfRows = 0;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        try {
            if (values != null && values.size() > 0) {
                // Use a PreparedStatement and set all values
                pstmt = conn.prepareStatement(sqlValue);
                setValues(pstmt, values);
                noOfRows = pstmt.executeUpdate();
            }
            else {
                // Use a regular Statement
                stmt = conn.createStatement();
                noOfRows = stmt.executeUpdate(sqlValue);
            }
        }
        finally {
            if (rs != null) {
                try {rs.close();} catch (SQLException e) {}
            }
            if (stmt != null) {
                try {stmt.close();} catch (SQLException e) {}
            }
            if (pstmt != null) {
                try {pstmt.close();} catch (SQLException e) {}
            }
        }
        return noOfRows;
    }

    private void setValues(PreparedStatement pstmt, List values)
        throws SQLException {
        for (int i = 0; i < values.size(); i++) {
	    Object v = values.get(i);
	    // Set the value using the method corresponding to the type.
	    // Note! Set methods are indexed from 1, so we add 1 to i
	    pstmt.setObject(i + 1, v);
        }
    }
}
