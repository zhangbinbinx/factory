package com.me.strategy;

public class StrategyTest {
    public static void main(String[] args) {
        BookSite bookSite = new BookSiteFactory().searchBook(BookSiteFactory.QI_DIAN_SITE);
        bookSite.bookRead(BookSiteFactory.QI_DIAN_SITE);
    }
}
