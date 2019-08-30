package com.cw.faquir.core.dao;


import lombok.Data;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Lao SiCheng
 * @version 0.1
 */
public class UserDAO {
    private DataSourceTransactionManager transactionManager;
    private DefaultTransactionDefinition def;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        transactionManager =
                new DataSourceTransactionManager(dataSource);
        // 建立交易的定義
        def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(
                TransactionDefinition.PROPAGATION_REQUIRED);
    }

    public void insert(User user) {
        String name = user.getName();
        int age = user.getAge().intValue();

        TransactionStatus status =
                transactionManager.getTransaction(def);
        try {
//            jdbcTemplate.update("INSERT INTO user (name,age) "
//                    + "VALUES('" + name + "'," + age + ")");
            // 下面的SQL有錯誤，用以測試交易
            jdbcTemplate.update("INSER INTO user (name,age) "
                    + "VALUES('" + name + "'," + age + ")");
        }
        catch(DataAccessException e) {
            transactionManager.rollback(status);
            throw e;
        }
        transactionManager.commit(status);
    }

    public User find(Integer id) {
        List rows = jdbcTemplate.queryForList(
                "SELECT * FROM user WHERE id=" + id.intValue());

        Iterator it = rows.iterator();
        if(it.hasNext()) {
            Map userMap = (Map) it.next();
            Integer i = new Integer(
                    userMap.get("id").toString());
            String name = userMap.get("name").toString();
            Integer age = new Integer(
                    userMap.get("age").toString());

            User user = new User();

            user.setId(i);
            user.setName(name);
            user.setAge(age);

            return user;
        }

        return null;
    }
    @Data
    public class User{
        Integer id;
        String name;
        Integer age;
    }
}
