package com.restaurent.order.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
    private static final String DB_DRIVER_CLASS = "db.datasource.driver-class-name";
    private static final String DB_USERNAME = System.getenv("DB_DATASOURCE_USERNAME");
    private static final String DB_PASSWORD = System.getenv("DB_DATASOURCE_PASSWORD");
    private static final String DB_URL = "db.datasource.url";
    private static BasicDataSource dataSource = new BasicDataSource();
    private static Properties properties = null;

    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        dataSource.setDriverClassName(properties.getProperty(DB_DRIVER_CLASS));
        dataSource.setUrl(properties.getProperty(DB_URL));
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    private DBUtil() {
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
