package com.me.mebatis;

public class MebatisTest {
    public static void main(String[] args) {
        SqlSession sqlSession = new SqlSession(new MyConfiguration(),new MyExecutor());
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        blogMapper.selectBlogById(1);
    }
}
