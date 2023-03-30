package com.spring.geo.common.config;

import javax.sql.DataSource;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Aspect
@Configuration
public class TransactionConfig {

    //온라인 sync 타임아웃 30초
    private static final int TX_METHOD_TIMEOUT = 30;
    //batch 타임아웃 8시간
    private static final int TX_METHOD_BATCH_TIMEOUT = 28800;
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* *..*Service.*(..))";

    @Bean 
    public TransactionManager primaryTransactionManager() {
        return new DataSourceTransactionManager(datasource());
    }

    @Bean 
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource datasource() {
        return DataSourceBuilder
            .create()
            .build();
    }

    @Bean 
    public TransactionInterceptor txAdvice() {
        DefaultTransactionAttribute txAttrRequired = new DefaultTransactionAttribute();
        txAttrRequired.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttrRequired.setTimeout(TX_METHOD_TIMEOUT);

        DefaultTransactionAttribute txAttrReadOnly = new DefaultTransactionAttribute();
        txAttrReadOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttrReadOnly.setReadOnly(true);
        txAttrReadOnly.setTimeout(TX_METHOD_TIMEOUT);

        DefaultTransactionAttribute txAttrBatchRequired = new DefaultTransactionAttribute();
        txAttrBatchRequired.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttrBatchRequired.setTimeout(TX_METHOD_BATCH_TIMEOUT);

        DefaultTransactionAttribute txAttrBatchReadOnly = new DefaultTransactionAttribute();
        txAttrBatchReadOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttrBatchReadOnly.setReadOnly(true);
        txAttrBatchReadOnly.setTimeout(TX_METHOD_BATCH_TIMEOUT);
        
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        // 30초 타임아웃으로 제한적 허용(온라인 업무처리)
        source.addTransactionalMethod("delete*", txAttrRequired);
        source.addTransactionalMethod("update*", txAttrRequired);
        source.addTransactionalMethod("insert*", txAttrRequired);
        source.addTransactionalMethod("select*", txAttrReadOnly);

        //배치 처리용 개발자 사용대상 (온라인 업무 사용 금지)
        source.addTransactionalMethod("batchInsert*", txAttrBatchRequired);
        source.addTransactionalMethod("batchDelete*", txAttrBatchRequired);
        source.addTransactionalMethod("batchUpdate*", txAttrBatchRequired);
        source.addTransactionalMethod("batchSelect*", txAttrBatchReadOnly);

        return new TransactionInterceptor(primaryTransactionManager(), source);
    }

    /**
     * @Description 특정 서비스에 대한 pointcut 설정
     * @param
     * @return Advisor
     * @throws URISyntaxException
     */
    @Bean public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }

}