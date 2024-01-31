package org.example.dao;

import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private static final HikariDataSource ds = new HikariDataSource();

    static {
        try {
            Properties props = new Properties();
            InputStream input = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("jdbc.properties");
            props.load(input);
            ds.setJdbcUrl(props.getProperty("jdbc.url"));
            ds.setUsername(props.getProperty("jdbc.username"));
            ds.setPassword(props.getProperty("jdbc.password"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
