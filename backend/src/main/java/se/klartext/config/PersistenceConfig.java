package se.klartext.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
    "se.klartext.domain.model.post",
    "se.klartext.domain.model.example",
    "se.klartext.domain.model.comment",
    "se.klartext.domain.model.shared",
    "se.klartext.data.jpa.repository.api",
    "se.klartext.data.jpa.repository.impl"})
@EnableJpaAuditing
public class PersistenceConfig {
}
