package com.cw.faquir.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.cw.faquir.core.domain.pojo.database.AbstractDataBase;
import com.cw.faquir.core.domain.pojo.database.DataBaseMaster;
import com.cw.faquir.core.domain.pojo.database.DataBaseSlave;
import com.cw.faquir.core.mybatis.ReadWritePlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lao SiCheng
 * @version 0.1
 */
@Configuration
public class MybatisConfig {


    /**
     * 创建主数据源
     *
     * @param dataBaseMaster 数据源信息
     * @return 数据源数组
     */
    @Bean
    @Autowired
    public List<DataSource> masterDataSourceList(DataBaseMaster dataBaseMaster) {
        return dataSourceList(dataBaseMaster);
    }

    /**
     * 创建从数据源
     *
     * @param dataBaseSlave 数据源信息
     * @return 数据源数组
     */
    @Bean
    @Autowired
    public List<DataSource> slaveDataSourceList(DataBaseSlave dataBaseSlave) {
        return dataSourceList(dataBaseSlave);
    }

    /**
     * 创建数据源
     *
     * @param abstractDataBase 数据源信息
     * @return 数据源数组
     */
    private List<DataSource> dataSourceList(AbstractDataBase abstractDataBase) {
        List<DataSource> dataSourceList = new ArrayList<>();
        // 根据配置文件内容, 生成配置文件
        for (AbstractDataBase.DataBase dataBaseInfo : abstractDataBase.getDatabase()) {
            // 创建数据源
            DataSource dataSource = createDataSource(dataBaseInfo);
            // 存放数据源
            dataSourceList.add(dataSource);

        }
        return dataSourceList;
    }

    @Bean
    @Primary
    public AbstractRoutingDataSource createRoutingDataSource(@Qualifier("masterDataSourceList") List<DataSource> masterDataSourceList, @Qualifier("slaveDataSourceList")  List<DataSource> slaveDataSourceList){
        RoutingDataSource routingDataSource = new RoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(8);
//        dataSourceMap.put(RouteDataSourceEnum.MATER, masterDataSourceList);
//        dataSourceMap.put(RouteDataSourceEnum.SLAVE, slaveDataSourceList);
        Map<Object, Object> masterMap = this.listToMap(Constant.MASTER,  masterDataSourceList);
        dataSourceMap.putAll(masterMap);

        Map<Object, Object> slaveMap = this.listToMap(Constant.SLAVE,  slaveDataSourceList);
        dataSourceMap.putAll(slaveMap);
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setLength(masterDataSourceList.size(), slaveDataSourceList.size());
        return routingDataSource;
    }

    @Bean
    public SqlSessionFactory createSqlSessionFactory(@Autowired AbstractRoutingDataSource routingDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(routingDataSource);
        // 配置插件
        Interceptor[] intercepts = {new ReadWritePlugin()};
        sqlSessionFactoryBean.setPlugins(intercepts);
        return sqlSessionFactoryBean.getObject();

    }
    private Map<Object, Object> listToMap(String key, List<DataSource> list){
        Map<Object, Object> map = new HashMap<>(list.size());
        for(int i = 0; i < list.size(); i++){
            map.put(key + "_" + i , list.get(i));
        }
        return map;
    }
    /**
     * 根据配置信息创建数据源
     *
     * @param dataBaseInfo 数据源信心
     * @return 数据源
     */
    private DataSource createDataSource(DataBaseMaster.DataBase dataBaseInfo) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dataBaseInfo.getUrl());
        dataSource.setDriverClassName(dataBaseInfo.getDriver());
        dataSource.setUsername(dataBaseInfo.getUsername());
        dataSource.setPassword(dataBaseInfo.getPassword());
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(@Autowired AbstractRoutingDataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
