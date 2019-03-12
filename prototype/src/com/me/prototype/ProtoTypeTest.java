package com.me.prototype;

import java.util.ArrayList;
import java.util.List;

public class ProtoTypeTest {
    public static void main(String[] args) {
        ConcreateProtoTypeFirst concreateProtoTypeFirst = new ConcreateProtoTypeFirst();
        concreateProtoTypeFirst.setName("浅克隆");
        List<String> addressList = new ArrayList<String>();
        addressList.add("上海市浦东新区");
        concreateProtoTypeFirst.setAddresss(addressList);
        ConcreateProtoTypeFirst temp = concreateProtoTypeFirst.clone();
        System.out.println(temp.getAddresss().get(0));
        addressList.set(0,"上海市松江区");
        System.out.println(temp.getAddresss().get(0));

    }
}
