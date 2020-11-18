package io.spring02.runner;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.sql.*;

public class SpringMySqlStarter {
    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://192.168.249.71:3306/manager";
    private final static String USER_NAME = "root";
    private final static String PASSWORD = "000000";

    private static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection hikari() throws Exception {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setUsername(USER_NAME);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(8);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "300");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource.getConnection();
    }

    @Test
    public void insert() throws SQLException {
        String insert = "insert into t_oder(id,data) values(?,?)";
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(insert);
        pstmt.setInt(1, 100);
        pstmt.setString(2, "data1");
        pstmt.execute();
        pstmt.close();
        conn.close();
    }

    @Test
    public void delete() throws SQLException {
        String delete = "delete from t_oder where id=?";
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(delete);
        pstmt.setInt(1, 100);
        pstmt.execute();
        pstmt.close();
        conn.close();
    }

    @Test
    public void update() throws SQLException {
        Connection conn = getConnection();
        String sql = "update t_oder set data=? where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "data2");
        pstmt.setInt(2, 100);
        pstmt.execute();
        pstmt.close();
        conn.close();
    }

    @Test
    public void query() throws SQLException {
        Connection conn = getConnection();
        String sql = "select * from t_oder where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, 100);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            System.out.println("id:" + rs.getInt("id") + ",data:" + rs.getString("data"));
        }
        pstmt.close();
        conn.close();
    }

    @Test
    public void transactions() {
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("insert into t_oder(id,data) values(102,'data1')");
            pstmt.execute();
            pstmt = conn.prepareStatement("insert into t_oder(id,data) values(103,'data2')");
            pstmt.execute();
            conn.commit();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Test
    public void hikariQuery() throws Exception {
        Connection conn = hikari();
        String sql = "select * from t_oder";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            System.out.println("id:" + rs.getInt("id") + ",data:" + rs.getString("data"));
        }
        pstmt.close();
        conn.close();
    }
}
