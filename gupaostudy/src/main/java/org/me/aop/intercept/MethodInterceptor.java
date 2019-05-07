package org.me.aop.intercept;

public interface MethodInterceptor {
    Object invoke(MethodInvocation methodInvocation) throws Throwable;
}
