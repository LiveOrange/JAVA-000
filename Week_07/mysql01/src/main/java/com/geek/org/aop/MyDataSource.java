package com.geek.org.aop;

import com.alibaba.druid.pool.ha.HighAvailableDataSource;
import com.alibaba.druid.pool.ha.selector.DataSourceSelector;
import com.alibaba.druid.pool.ha.selector.NamedDataSourceSelector;
import com.alibaba.druid.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyDataSource {
    @Pointcut("@annotation(com.geek.org.aop.DataSource)")
    private void pointCut() {
    }

    @Autowired
    @Qualifier("highAvailableDataSource")
    private HighAvailableDataSource highAvailableDataSource;

    @Around("pointCut() && @annotation(dataSource)")
    public Object Around(ProceedingJoinPoint joinPoint, DataSource dataSource) throws Throwable {
        String value = dataSource.value();
        if (StringUtils.isEmpty(value)) {
            return joinPoint.proceed();
        } else {
            DataSourceSelector dataSourceSelector = highAvailableDataSource.getDataSourceSelector();
            try {
                highAvailableDataSource.setTargetDataSource(value);
                return joinPoint.proceed();
            } finally {
                if (dataSourceSelector instanceof NamedDataSourceSelector) {
                    NamedDataSourceSelector sourceSelector = (NamedDataSourceSelector) dataSourceSelector;
                    sourceSelector.resetDataSourceName();
                }
            }
        }
    }

}
