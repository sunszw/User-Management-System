package com.ssmsun.management.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Order(1)
@Configuration
@Aspect
public class AOPTransactional {

    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.ssmsun.management.service.impl.*.*(..))";

    @Autowired
    private TransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        //事务管理规则，声明具备事务管理的方法名
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        //当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        //PROPAGATION_REQUIRED:事务隔离性为1，若当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。这是默认值。
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //抛出异常后执行切点回滚
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Error.class)));
        //设置事务失效时间，如果超过5秒，则回滚事务
        requiredTx.setTimeout(5000);
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("regUser", requiredTx);
        txMap.put("userConfirm", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("subUser", requiredTx);

        source.setNameMap(txMap);
        return new TransactionInterceptor(transactionManager,source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        /* 声明切点的面
         * 切面（Aspect）：切面就是通知和切入点的结合。
         * 通知和切入点共同定义了关于切面的全部内容——它的功能、在何时和何地完成其功能。
         * */
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        //声明和设置需要拦截的方法,用切点语言描写
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        //设置切面=切点pointcut+通知TxAdvice
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }


}
