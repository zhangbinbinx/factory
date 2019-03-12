package com.me.enumpattern;


public enum EnumPattern {
    AAA,BBB,CCC;
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static EnumPattern getInstance(int number){
        switch (number){
            case 1: return AAA;
            case 2: return BBB;
            case 3: return CCC;
            default:return AAA;
        }
    }
}
