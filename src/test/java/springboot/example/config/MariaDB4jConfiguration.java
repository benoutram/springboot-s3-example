package springboot.example.config;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
public class MariaDB4jConfiguration {

    @Bean
    public MariaDB4jSpringService mariaDB4jSpringService() {
        return new MariaDB4jSpringService();
    }

    @Bean
    @DependsOn("mariaDB4jSpringService")
    public DataSource dataSource(final MariaDB4jSpringService mariaDB4jSpringService,
                                 @Value("${spring.datasource.driver-class-name}") final String datasourceDriver)
            throws ManagedProcessException {

        mariaDB4jSpringService.getDB().createDB("terraform_test_db");

        final DBConfigurationBuilder config = mariaDB4jSpringService.getConfiguration();

        return DataSourceBuilder
                .create()
                .username("terraform")
                .password("")
                .url(config.getURL("terraform_test_db"))
                .driverClassName(datasourceDriver)
                .build();
    }
}