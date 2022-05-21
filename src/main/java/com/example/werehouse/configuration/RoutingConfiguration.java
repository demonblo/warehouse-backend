package com.example.werehouse.configuration;

import com.example.werehouse.component.ClientDataSourceRouter;
import com.example.werehouse.component.RoutingDatabaseProperties;
import com.example.werehouse.model.ClientDatabase;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(RoutingDatabaseProperties.class)
public class RoutingConfiguration {

    private DataSource createDataSource(String url, String username, String password) {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Bean
    @SneakyThrows
    public DataSource dataSource(RoutingDatabaseProperties properties) {
        Map<Object, Object> targetDataSources = new HashMap<>();

//        var repProp = properties.getRepresentative();
//        DataSource repDataSource = createDataSource(properties.getUrl(), repProp.getUsername(), repProp.getPassword());
//        targetDataSources.put(ClientDatabase.REPRESENTATIVE, repDataSource);
//        var workerProp = properties.getWorker();
//        DataSource workerDataSource = createDataSource(properties.getUrl(), workerProp.getUsername(), workerProp.getPassword());
//        targetDataSources.put(ClientDatabase.WORKER, workerDataSource);
        var adminProp = properties.getAdmin();
        DataSource adminDataSource = createDataSource(properties.getUrl(), adminProp.getUsername(), adminProp.getPassword());
        targetDataSources.put(ClientDatabase.ADMIN, adminDataSource);

        ClientDataSourceRouter clientDataSourceRouter = new ClientDataSourceRouter();
        clientDataSourceRouter.setTargetDataSources(targetDataSources);
        clientDataSourceRouter.setDefaultTargetDataSource(adminDataSource);
        return clientDataSourceRouter;
    }
}
