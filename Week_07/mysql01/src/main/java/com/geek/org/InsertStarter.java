package com.geek.org;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.sql.*;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InsertStarter {
    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/manager?useUnicode=true&characterEncoding=utf8";
    private final static String USER_NAME = "root";
    private final static String PASSWORD = "";
    private static final Random random = new Random();
    private final static int DELTA = 0x9fa5 - 0x4e00 + 1;

    public static Connection hikari() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setUsername(USER_NAME);
        config.setMaximumPoolSize(10);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "300");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource dataSource = new HikariDataSource(config);
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static char getName() {
        return (char) (0x4e00 + random.nextInt(DELTA));
    }

    @Test
    public void insert100w3() throws Exception {
        long start = System.currentTimeMillis();
        String insert = "INSERT INTO t_user (consumer_id,user_name,pass_word,email,card_no,mobile,state,gender,create_time,update_time) VALUES";
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final CountDownLatch down = new CountDownLatch(10);
        for (int l = 0; l < 10; l++) {
            executorService.execute(() -> {
                try {
                    Connection conn = hikari();
                    conn.setAutoCommit(false);
                    PreparedStatement pst = conn.prepareStatement("");
                    for (int z = 0; z < 10; z++) {
                        StringBuilder suffix = new StringBuilder(insert);
                        for (int i = 1; i <= 10000; i++) {
                            suffix.append("('");
                            suffix.append(UUID.randomUUID().toString()).append("','");
                            suffix.append(getName()).append(getName()).append("','");
                            suffix.append(DigestUtils.md5DigestAsHex((i + "").getBytes())).append("','");
                            suffix.append("15873345605@163").append("','");
                            suffix.append("15873345605").append("','");
                            suffix.append("4312811199911111").append("','");
                            suffix.append("T").append("',");
                            suffix.append(0).append(",");
                            suffix.append("now()").append(",");
                            suffix.append("now()");
                            suffix.append("),");
                        }
                        String sql = suffix.substring(0, suffix.length() - 1);
                        pst.addBatch(sql);
                        pst.executeBatch();
                    }
                    conn.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    down.countDown();
                }
            });
        }
        down.await();
        long end = System.currentTimeMillis();
        System.out.println(end - start + ":ms" + "---END---");
    }
}
