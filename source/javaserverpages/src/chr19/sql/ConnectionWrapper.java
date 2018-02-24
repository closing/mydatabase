package chr19.sql;

import java.sql.Connection;
import java.util.Properties;
import java.util.Map;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.NClob;
import java.sql.SQLClientInfoException;
import java.sql.Struct;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.util.concurrent.Executor;

public class ConnectionWrapper implements Connection {
    private Connection realConn;
    private DataSourceWrapper dsw;
    private boolean isClosed = false;

    public ConnectionWrapper(Connection realConn, DataSourceWrapper dsw) {
        this.realConn = realConn;
        this.dsw = dsw;
    }

    public void close() throws SQLException {
        isClosed = true;
        dsw.returnConnection(realConn);
    }

    public boolean isClosed() throws SQLException {
        return isClosed;
    }

    public void clearWarnings() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.clearWarnings();
    }

    public void commit() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.commit();
    }

    public Statement createStatement() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.createStatement();
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.createStatement(resultSetType, resultSetConcurrency);
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    public boolean getAutoCommit() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getAutoCommit();
    }

    public String getCatalog() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getCatalog();
    }

    public int getHoldability() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getHoldability();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getMetaData();
    }

    public int getTransactionIsolation() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getTransactionIsolation();
    }

    public java.util.Map<String, Class<?>> getTypeMap() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getTypeMap();
    }

    public SQLWarning getWarnings() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getWarnings();
    }

    public boolean isReadOnly() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.isReadOnly();
    }

    public String nativeSQL(String sql) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.nativeSQL(sql);
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareCall(sql);
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareStatement(sql);
    }

    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareStatement(sql, autoGeneratedKeys);
    }

    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareStatement(sql, columnIndexes);
    }

    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareStatement(sql, columnNames);
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.releaseSavepoint(savepoint);
    }

    public void rollback() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.rollback();
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.rollback(savepoint);
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.setAutoCommit(autoCommit);
    }

    public void setCatalog(String catalog) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.setCatalog(catalog);
    }

    public void setHoldability(int holdability) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.setHoldability(holdability);
    }

    public void setReadOnly(boolean readOnly) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.setReadOnly(readOnly);
    }

    public Savepoint setSavepoint() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.setSavepoint();
    }

    public Savepoint setSavepoint(String name) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.setSavepoint(name);
    }

    public void setTransactionIsolation(int level) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.setTransactionIsolation(level);
    }

    public void setTypeMap(Map map) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.setTypeMap(map);
    }

    public int getNetworkTimeout() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getNetworkTimeout();
    }

    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.setNetworkTimeout(executor, milliseconds);
    }

    public void abort(Executor executor) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.abort(executor);
    }

    public String getSchema() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getSchema();
    }

    public void setSchema(String schema) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        realConn.setSchema(schema);
    }

    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.createStruct(typeName, attributes);
    }

    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.createArrayOf(typeName, elements);
    }

    public Properties getClientInfo() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getClientInfo();
    }

    public String getClientInfo(String name) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.getClientInfo(name);
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        realConn.setClientInfo(properties);
    }

    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        realConn.setClientInfo(name, value);
    }

    public boolean isValid(int timeout) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.isValid(timeout);
    }

    public SQLXML createSQLXML() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.createSQLXML();
    }

    public NClob createNClob() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.createNClob();
    }

    public Clob createClob() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.createClob();
    }

    public Blob createBlob() throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.createBlob();
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.isWrapperFor(iface);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (isClosed) {
            throw new SQLException("Pooled connection is closed");
        }
        return realConn.unwrap(iface);
    }
}