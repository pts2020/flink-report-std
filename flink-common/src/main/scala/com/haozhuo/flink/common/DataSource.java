package com.haozhuo.flink.common;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * http://www.javatips.net/blog/c3p0-connection-pooling-example
 */
public class DataSource implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(DataSource.class);
    private ComboPooledDataSource cpds;

    public DataSource(String url, String user, String password) {
        cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        cpds.setJdbcUrl(url);
        cpds.setUser(user);
        cpds.setPassword(password);

        cpds.setMinPoolSize(1);
        cpds.setAcquireIncrement(1);
        cpds.setMaxPoolSize(6);
        cpds.setMaxStatements(100);
        cpds.setIdleConnectionTestPeriod(18000);
        //cpds.setPreferredTestQuery("select 1");
        logger.info("start connect: "+url);
    }

    public Connection getConnection() throws SQLException {
        return this.cpds.getConnection();
    }

    public static void close(PreparedStatement preparedStmt, Connection conn) {
        if (preparedStmt != null) {
            try {
                preparedStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
