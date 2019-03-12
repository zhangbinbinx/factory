package com.me.hungry;

public class HungryTest {
    public static void main(String[] args) {
        HungrySimplePattern hungrySimplePattern = HungrySimplePattern.getInstance();
        hungrySimplePattern.setName("AAA");
        System.out.println(hungrySimplePattern.getName());
    }
}
