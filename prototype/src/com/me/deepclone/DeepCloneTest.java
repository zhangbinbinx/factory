package com.me.deepclone;

import com.me.prototype.ConcreateProtoTypeFirst;

import java.util.ArrayList;
import java.util.List;

public class DeepCloneTest {
    public static void main(String[] args) {
        ConcreateDeepClone concreateDeepClone = new ConcreateDeepClone();
        concreateDeepClone.setName("深度克隆");
        List<String> addressList = new ArrayList<String>();
        addressList.add("上海市浦东新区");
        concreateDeepClone.setAddresss(addressList);
        ConcreateDeepClone temp = concreateDeepClone.clone();
        System.out.println(temp.getAddresss().get(0));
        addressList.set(0,"上海市松江区");
        System.out.println(temp.getAddresss().get(0));
    }
}
