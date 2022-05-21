package com.example.werehouse.component;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ClientDataSourceRouter extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return ClientDatabaseContextHolder.getClientDatabase();
    }
}
