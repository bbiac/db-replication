package ac.bbi.dbreplication.db;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DataSourceConfiguration {

    private static final String SOURCE = "source";
    private static final String REPLICA = "replica";
    private static final String ROUTING = "routing";

    @Bean
    @Qualifier(SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.source")
    public DataSource sourceDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Qualifier(REPLICA)
    @ConfigurationProperties(prefix = "spring.datasource.replica")
    public DataSource replicaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Qualifier(ROUTING)
    public DataSource routingDataSource(
            @Qualifier(SOURCE) DataSource sourceDataSource,
            @Qualifier(REPLICA) DataSource replicaDataSource
    ) {
        RoutingDataSource transactionRoutingDataSource = new RoutingDataSource();

        Map<Object, Object> dataSourceMap = Map.of(
                DataSourceType.SOURCE, sourceDataSource,
                DataSourceType.REPLICA, replicaDataSource
        );

        transactionRoutingDataSource.setDefaultTargetDataSource(sourceDataSource);
        transactionRoutingDataSource.setTargetDataSources(dataSourceMap);

        return transactionRoutingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource(
            @Qualifier(ROUTING) DataSource routingDataSource
    ) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
