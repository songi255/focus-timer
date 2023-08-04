/**
 * link Model's Setter Method with Storage
 *
 * @author Dave Shin
 */
package com.focustimer.focustimer.config.store;

import com.focustimer.focustimer.model.template.TemplateModel;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Setter
public class PersistenceProvider implements MethodInterceptor {
    private DataManager dataManager;
    private TemplateModel templateModel;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> targetClass = invocation.getThis().getClass();
        System.out.println("intercepted on " + invocation.getMethod().getName());


        return invocation.proceed();
    }
}
