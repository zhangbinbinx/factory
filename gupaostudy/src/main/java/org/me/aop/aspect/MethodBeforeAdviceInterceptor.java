package org.me.aop.aspect;

import org.me.aop.intercept.MethodInterceptor;
import org.me.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class MethodBeforeAdviceInterceptor extends AbstractAspectAdvice implements MethodInterceptor {
   private JoinPoint joinPoint;

    public MethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void before(Method method,Object [] args,Object target) throws Throwable{
        super.invokeAdviceMethod(this.joinPoint,null,null);
    }

    public Object invoke(MethodInvocation mi) throws Throwable {
       this.joinPoint = mi;
       before(mi.getMethod(),mi.getArguments(),mi.getTarget());
        return mi.proceed();
    }
}
