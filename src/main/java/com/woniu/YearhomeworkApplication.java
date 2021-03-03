package com.woniu;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.woniu.mapper")
public class YearhomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(YearhomeworkApplication.class, args);
    }

    @Bean
    public ISqlInjector iSqlInjector(){
        return new LogicSqlInjector();
    }


}
