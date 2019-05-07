package org.me.aop.aspect;

import org.me.aop.intercept.MethodInterceptor;
import org.me.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class AfterReturningAdviceInterceptor extends AbstractAspectAdvice implements MethodInterceptor {
    private JoinPoint joinPoint;

    public AfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }
    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws Throwable {
        super.invokeAdviceMethod(this.joinPoint,retVal,null);
    }
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object returnVal = mi.proceed();
        this.joinPoint = mi;
        this.afterReturning(returnVal,mi.getMethod(),mi.getArguments(),mi.getThis());
        return returnVal;
    }
}
