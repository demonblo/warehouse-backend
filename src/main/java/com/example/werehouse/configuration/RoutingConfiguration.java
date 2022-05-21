package com.example.werehouse.configuration;

import com.example.werehouse.component.ClientDataSourceRouter;
import com.example.werehouse.model.ClientDatabase;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RoutingConfiguration {

    private DataSource createDataSource(String username, String password) {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url("jdbc:postgresql://localhost:5433/warehouse-test2")
                .username(username)
                .password(password)
                .build();
    }

    @Bean
    @SneakyThrows
    public DataSource dataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
//        DataSource userDataSource = createDataSource();
//        targetDataSources.put(ClientDatabase.WORKER, userDataSource);
//        DataSource repDataSource = createDataSource("user", "chmo");
//        targetDataSources.put(ClientDatabase.REPRESENTATIVE, repDataSource);
        DataSource adminDataSource = createDataSource("postgres", "Dem513454");
        targetDataSources.put(ClientDatabase.ADMIN, adminDataSource);

        ClientDataSourceRouter clientDataSourceRouter = new ClientDataSourceRouter();
        clientDataSourceRouter.setTargetDataSources(targetDataSources);
        clientDataSourceRouter.setDefaultTargetDataSource(adminDataSource);
        return clientDataSourceRouter;
    }
}
