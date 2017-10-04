package se.klartext.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
        "se.klartext.app.data.jpa.repository.api","se.klartext.app.data.jpa.repository.impl"})
@EnableJpaAuditing
public class PersistenceConfig {
}
