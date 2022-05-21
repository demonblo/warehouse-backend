package com.example.werehouse.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app.routing-database")
public class RoutingDatabaseProperties {
    private String url;
    private RoutingDatabaseCredentials representative;
    private RoutingDatabaseCredentials worker;
    private RoutingDatabaseCredentials admin;

    @Data
    public static class RoutingDatabaseCredentials {
        private String username;
        private String password;
    }
}
