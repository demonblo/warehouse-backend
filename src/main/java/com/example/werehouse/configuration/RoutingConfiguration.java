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
import java.util.Map;

@Configuration
@EnableConfigurationProperties(RoutingDatabaseProperties.class)
public class RoutingConfiguration {

    private DataSource createDataSource(String url, RoutingDatabaseProperties.RoutingDatabaseCredentials credentials) {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url(url)
                .username(credentials.getUsername())
                .password(credentials.getPassword())
                .build();
    }

    @Bean
    @SneakyThrows
    public DataSource dataSource(RoutingDatabaseProperties properties) {
        Map<Object, Object> targetDataSources = Map.of(
                ClientDatabase.REPRESENTATIVE,
                createDataSource(properties.getUrl(), properties.getRepresentative()),
                ClientDatabase.WORKER,
                createDataSource(properties.getUrl(), properties.getWorker()),
                ClientDatabase.ADMIN,
                createDataSource(properties.getUrl(), properties.getAdmin()),
                ClientDatabase.OWNER,
                createDataSource(properties.getUrl(), properties.getOwner()),
                ClientDatabase.ASSISTANT,
                createDataSource(properties.getUrl(), properties.getAssistant())
        );
        ClientDataSourceRouter clientDataSourceRouter = new ClientDataSourceRouter();
        clientDataSourceRouter.setTargetDataSources(targetDataSources);
        clientDataSourceRouter.setDefaultTargetDataSource(targetDataSources.get(ClientDatabase.OWNER));
        return clientDataSourceRouter;
    }
}
