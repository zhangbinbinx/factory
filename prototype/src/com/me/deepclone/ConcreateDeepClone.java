package com.me.deepclone;

import com.me.prototype.ConcreateProtoTypeFirst;
import com.me.prototype.IProtoType;

import java.io.*;
import java.util.List;

public class ConcreateDeepClone implements IProtoType,Serializable {
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
    public ConcreateDeepClone clone() {

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);

            ConcreateDeepClone copy = (ConcreateDeepClone)ois.readObject();
            return copy;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
