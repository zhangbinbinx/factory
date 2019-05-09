package org.me.aop.aspect;

import lombok.Data;
import org.me.aop.intercept.MethodInterceptor;
import org.me.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;
@Data
public class AfterThrowingAdviceInterceptor extends AbstractAspectAdvice implements MethodInterceptor {
    private String throwingName;
    public AfterThrowingAdviceInterceptor(Method method, Object newInstance) {
        super(method,newInstance);
    }

    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        }catch (Throwable e){
            invokeAdviceMethod(mi,null,e.getCause());
            throw e;
        }
    }
}
