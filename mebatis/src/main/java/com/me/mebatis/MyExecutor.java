package com.me.mebatis;

import java.lang.reflect.InvocationHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyExecutor {

    public <T> T query(String sql, Object[] paramter) {
        Connection conn = null;
        Statement stmt = null;
        Blog blog = new Blog();

        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 打开连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis", "root", "root");

            // 执行查询
            stmt = conn.createStatement();
            //String sql = "SELECT bid, name, author_id FROM blog";
            ResultSet rs = stmt.executeQuery(String.format(sql, paramter[0]));

            // 获取结果集
            while (rs.next()) {
                Integer bid = rs.getInt("bid");
                String name = rs.getString("name");
                Integer authorId = rs.getInt("author_id");
                blog.setAuthorId(authorId);
                blog.setBid(bid);
                blog.setName(name);
            }
            System.out.println(blog);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return (T)blog;
    }
}
