package com.example.demo21032501.batch;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfig  {
    @Autowired
    private Environment env;
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getRequiredProperty("spring.datasource.url"));
        dataSource.setUsername(env.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));
        return dataSource;
    }
@Bean
public DataSourceInitializer databasePopulator() {
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.setContinueOnError(true);
    populator.setIgnoreFailedDrops(true);
    populator.addScript(new ClassPathResource("org/springframework/batch/core/schema-mysql.sql"));

    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource());
    initializer.setDatabasePopulator(populator);

    return initializer;
}


//    @Configuration
//    @Profile("env.development")
//    static class conditionalBeanForDevelopmentProfile{
//
//        @Autowired
//        private Environment env;
//
//        @Bean
//        public DataSource dataSource() {
//            DriverManagerDataSource dataSource = new DriverManagerDataSource();
//            dataSource.setUrl(env.getRequiredProperty("spring.datasource.url"));
//            dataSource.setUsername(env.getRequiredProperty("spring.datasource.username"));
//            dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));
//            return dataSource;
//        }
//
//        @Bean
//        public DataSourceInitializer databasePopulator() {
//            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//            populator.setContinueOnError(true);
//            populator.setIgnoreFailedDrops(true);
//            populator.addScript(new ClassPathResource("org/springframework/batch/core/schema-mysql.sql"));
//
//            DataSourceInitializer initializer = new DataSourceInitializer();
//            initializer.setDataSource(dataSource());
//            initializer.setDatabasePopulator(populator);
//            return initializer;
//        }
//
//    }
//
//
//    @Configuration
//    @Profile("env.production")
//    static class conditionalBeanForProductionProfile{
//
//        @Autowired
//        private Environment env;
//
//        @Bean
//        public DataSource dataSource() {
//            JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
//            DataSource dataSource = dataSourceLookup.getDataSource(env.getRequiredProperty("spring.datasource.jndi-name"));
//            return dataSource;
//        }
//
//        @Bean
//        public DataSourceInitializer databasePopulator() {
//            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//            populator.setContinueOnError(true);
//            populator.setIgnoreFailedDrops(true);
//            populator.addScript(new ClassPathResource("org/springframework/batch/core/schema-mysql.sql"));
//
//            DataSourceInitializer initializer = new DataSourceInitializer();
//            initializer.setDataSource(dataSource());
//            initializer.setDatabasePopulator(populator);
//            return initializer;
//        }
//
//    }










}
