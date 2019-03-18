package com.me.strategy;

import java.util.HashMap;
import java.util.Map;

public class BookSiteFactory{
    public static final String QI_DIAN_SITE = "QI_DIAN_SITE";
    public static final String SITE_2345_SITE = "SITE_2345_SITE";
    public static final String SHU_QI_SITE = "SHU_QI_SITE";
    public static final String DEFAULT_SITE = QI_DIAN_SITE;
    private static Map<String,Object> siteMap = new HashMap<String, Object>();
    static {
        siteMap.put("QI_DIAN_SITE",new QiDianSite());
        siteMap.put("SITE_2345_SITE",new O2345Site());
        siteMap.put("SHU_QI_SITE",new ShuQiSite());
    }

    public BookSite searchBook(String bookSite) {
        if(siteMap.containsKey(bookSite)){
            return (BookSite)siteMap.get(bookSite);
        }else{
            return (BookSite)siteMap.get(DEFAULT_SITE);
        }
    }
}
