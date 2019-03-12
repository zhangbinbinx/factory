package com.me.model;

import lombok.Data;

@Data
public class Apple implements IFruit {
    private String weight;
    private Apple(){}
    @Override
    public String getName() {
        return "苹果";
    }

    @Override
    public String getWeight() {
        return weight == null ? "0.3千克":weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
}
