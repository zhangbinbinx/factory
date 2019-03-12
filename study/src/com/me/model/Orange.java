package com.me.model;

import lombok.Data;

@Data
public class Orange implements IFruit {
    private String weight;
    private Orange(){}
    @Override
    public String getName() {
        return "橘子";
    }

    @Override
    public String getWeight() {
        return weight == null ? "0.2千克":weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
