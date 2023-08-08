/**
 * link Model's Setter Method with Storage
 *
 * @author Dave Shin
 */
package com.focustimer.focustimer.config.store;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class PersistenceProvider implements MethodInterceptor {
    private final boolean saveWithTemplate;
    private DataManager dataManager;
    private TemplateModel templateModel;

    public PersistenceProvider(boolean saveWithTemplate) {
        this.saveWithTemplate = saveWithTemplate;
    }

    @Inject
    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Inject
    public void setTemplateModel(TemplateModel templateModel) {
        this.templateModel = templateModel;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("setter " + invocation.getMethod().getName() + " intercepted by Persistence Provider");

        Class<?> targetClass = invocation.getThis().getClass();
        String className = targetClass.getSimpleName().split("[$]")[0];
        String methodName = invocation.getMethod().getName();
        String fieldName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);

        String key = className + "." + fieldName;
        if (saveWithTemplate){
            key = DataManager.generateKey(templateModel.getTemplateNum(), key);
        }
        dataManager.setData(key, String.valueOf(invocation.getArguments()[0]));

        return invocation.proceed();
    }
}
