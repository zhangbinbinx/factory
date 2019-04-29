package org.me.beans;

import lombok.Data;

@Data
public class BeanWrapper {
    private Object wrappedInstance;
    private Class<?> wrappedClass;
    public BeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

}
