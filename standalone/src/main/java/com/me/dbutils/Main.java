package com.me.dbutils;

import com.me.dbutils.dao.BlogDao;

public class Main {
    public static void main(String[] args) throws Exception {
        HikariUtil.init();
        BlogDao.selectBlog(1);
        BlogDao.selectList();
    }
}
