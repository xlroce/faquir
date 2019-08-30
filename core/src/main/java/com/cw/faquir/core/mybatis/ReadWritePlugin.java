package com.cw.faquir.core.mybatis;

import com.cw.faquir.core.config.Constant;
import com.cw.faquir.core.config.RoutingDataSource;
import com.cw.faquir.core.domain.pojo.database.AbstractDataBase;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Properties;

/**
 * @author Lao SiCheng
 * @version 0.1
 */
@Intercepts({
        @Signature(type = Executor.class, method = ReadWritePlugin.UPDATE, args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = ReadWritePlugin.QUERY, args= {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class ReadWritePlugin implements Interceptor {
    public final static String QUERY ="query";
    public final static String UPDATE ="update";
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        boolean synchronizationActive  = TransactionSynchronizationManager.isSynchronizationActive();
        if(ReadWritePlugin.QUERY.equals(invocation.getMethod().getName())){
            RoutingDataSource.setLookupKey(Constant.SLAVE);
        }else{
            RoutingDataSource.setLookupKey(Constant.MASTER);
        }
        Object objectResult = invocation.proceed();
        return objectResult;
    }

    @Override
    public Object plugin(Object target) {

        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


}
