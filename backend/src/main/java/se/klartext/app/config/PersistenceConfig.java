package se.klartext.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
        "se.klartext.app.data.api.jpa",
        "se.klartext.app.data.impl.jpa"})
@EnableJpaAuditing
public class PersistenceConfig {
}
