package com.assignment.api.config.aop;

import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.jvnet.hk2.annotations.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

@Service
public class HK2InterceptionService implements InterceptionService {

    private final static MethodInterceptor METHOD_INTERCEPTOR = new TransactionalResourceInterceptor();


    private final static List<MethodInterceptor> METHOD_LIST = Collections.singletonList(METHOD_INTERCEPTOR);

    public Filter getDescriptorFilter() {
        return BuilderHelper.allFilter();
    }

    public List<MethodInterceptor> getMethodInterceptors(Method method) {
        if (method.isAnnotationPresent(Transactional.class)) {
            return METHOD_LIST;
        }

        return null;
    }

    public List<ConstructorInterceptor> getConstructorInterceptors(
            Constructor<?> constructor) {
        return null;
    }
}