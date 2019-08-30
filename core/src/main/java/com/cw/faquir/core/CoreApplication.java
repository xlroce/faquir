package com.cw.faquir.core;

import com.cw.faquir.core.mybatis.ReadWritePlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 系统主入口
 * @author Lao SiCheng
 * @version 0.1
 */
@SpringBootApplication
@MapperScan("com.cw.faquir.core.dao")
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
//    @Bean
//    public Interceptor getInterceptor(){
//        return new ReadWritePlugin();
//    }
}
