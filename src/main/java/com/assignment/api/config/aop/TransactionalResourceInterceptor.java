package com.assignment.api.config.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TransactionalResourceInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
        //System.out.println("MethodInvocation:" + SessionFactoryFactory.factory.openSession().hashCode());
        /*boolean externalTransaction = false;
        final Session session = sessionProvider.get();
        Transaction tx = session.getTransaction();
        if (tx == null || !tx.isActive()) {
            externalTransaction = false;
            tx = session.beginTransaction();
        } else {
            externalTransaction = true;
        }
*/
        try {
            // Invoke JAX-RS resource method.
            final Object result = methodInvocation.proceed();

            // Commit the transaction.
            //if (!externalTransaction)
            //    tx.commit();

            return result;
        } catch (final RuntimeException re) {
            // Something bad happened, rollback;
            //if(tx != null && tx.isActive())
            //    tx.rollback();

            // Rethrow the Exception.
            throw re;
        }
    }
}