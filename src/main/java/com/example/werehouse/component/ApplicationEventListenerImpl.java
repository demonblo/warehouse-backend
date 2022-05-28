package com.example.werehouse.component;

import com.example.werehouse.model.ClientDatabase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class ApplicationEventListenerImpl {
    private final DataSource dataSource;

    @EventListener(ApplicationStartedEvent.class)
    public void handleApplicationStarted() {
        if (dataSource instanceof ClientDataSourceRouter) {
            ClientDataSourceRouter router = (ClientDataSourceRouter)dataSource;
            var defaultDataSource = router.getResolvedDataSources().get(ClientDatabase.ASSISTANT);
            router.setDefaultTargetDataSource(defaultDataSource);
        }
    }
}
