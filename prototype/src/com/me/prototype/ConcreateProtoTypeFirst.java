package com.me.prototype;

import java.util.List;

public class ConcreateProtoTypeFirst implements IProtoType {
    private String name;
    private List<String> addresss;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAddresss() {
        return addresss;
    }

    public void setAddresss(List<String> addresss) {
        this.addresss = addresss;
    }

    @Override
    public ConcreateProtoTypeFirst clone() {
        ConcreateProtoTypeFirst concreateProtoTypeFirst = new ConcreateProtoTypeFirst();
        concreateProtoTypeFirst.setName(this.name);
        concreateProtoTypeFirst.setAddresss(this.addresss);
        return concreateProtoTypeFirst;
    }
}
