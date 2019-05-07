package org.me.aop.intercept;

import lombok.Data;
import org.me.aop.aspect.JoinPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
@Data
public class MethodInvocation implements JoinPoint {

    private Object proxy;
    private Method method;
    private Object target;
    private Class<?> targetClass;
    private Object[] arguments;
    private List<Object> interceptorsAndDynamicMethodMatchers;
    private int currentInterceptorIndex = -1;
    public MethodInvocation(Object proxy, Object target, Method method, Object[] arguments, Class targetClass, List<Object> interceptors) {
        this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptors;
    }

    public Object proceed() throws Exception {
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1)
        {
            return this.method.invoke(this.target,this.arguments);
        }
        Object interceptorOrInterceptionAdvice =
                this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        if (interceptorOrInterceptionAdvice instanceof MethodInterceptor) {
            MethodInterceptor mi = (MethodInterceptor) interceptorOrInterceptionAdvice;
            return mi.invoke(this);
        } else {
            return proceed();
        }
    }

    public Object getThis() {
        return this.target;
    }
}
