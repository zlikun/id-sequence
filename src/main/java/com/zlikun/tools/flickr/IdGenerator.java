package com.zlikun.tools.flickr;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import java.sql.*;
import java.util.Properties;

/**
 * 使用Flickr方案实现的主键生成器
 *
 * 这里为简化逻辑，实际只连了一台MySQL实现，生产环境可以通过一个前端代理自动分发到后端多台MySQL上(可以故障转移)，也可以自行通过轮询的方式来轮流生成ID (无法故障转移)
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-02-02 15:28
 */
public class IdGenerator {

    private static BasicDataSource dataSource ;

    static {
//        // 加载MySQL驱动
//        try {
//            Class.forName(com.mysql.jdbc.Driver.class.getName());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        // 配置连接池
        try {
            Properties props = new Properties();
            props.load(IdGenerator.class.getResourceAsStream("/jdbc.properties"));
            dataSource = BasicDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }

    }

    private static final Connection createConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private static final void close(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * 获取生成的主键
     * @return
     */
    public static final Integer get() {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = createConnection();
            statement = conn.prepareStatement("REPLACE INTO test.ID_USER (stub) VALUES ('x')"
                    , Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
