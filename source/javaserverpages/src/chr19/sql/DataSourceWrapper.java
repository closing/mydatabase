package chr19.sql;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Connection;
import javax.sql.DataSource;
import java.util.logging.Logger;

public class DataSourceWrapper implements DataSource {
    private ConnectionPool pool;
    private String driverClassName;
    private String url;
    private String user;
    private String password;
    private int initialConnections;

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setInitialConnections(int initialConnections) {
        this.initialConnections = initialConnections;
    }

    public Connection getConnection() throws SQLException {
        if (pool == null) {
            createConnectionPool();
        }
        return new ConnectionWrapper(pool.getConnection(), this);
    }

    private void createConnectionPool() throws SQLException {

        try {
            pool = new ConnectionPool(driverClassName, url, user, password, initialConnections);
            System.out.println(initialConnections);
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            SQLException sqle = new SQLException(
                    "Error creating pool: " + e.getClass().getName() + " : " + e.getMessage());
            throw sqle;
        }
    }

    public void returnConnection(Connection conn) {
        pool.returnConnection(conn);
    }

    public Connection getConnection(String username, String password) throws SQLException {
        throw new SQLException("Not supported");
    }

    public int getLoginTimeout() throws SQLException {
        throw new SQLException("Not supported");
    }

    public PrintWriter getLogWriter() throws SQLException {
        throw new SQLException("Not supported");
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        throw new SQLException("Not supported");
    }

    public synchronized void setLogWriter(PrintWriter out) throws SQLException {
        throw new SQLException("Not supported");
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {

        throw new SQLFeatureNotSupportedException("Not supported");
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLException("Not supported");
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException("Not supported");
    }
}
