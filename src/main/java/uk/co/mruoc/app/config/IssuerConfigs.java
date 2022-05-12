package uk.co.mruoc.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class IssuerConfigs {

    @Bean
    @ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver.issuers")
    public Collection<IssuerConfig> issuers() {
        return new ArrayList<>();
    }

}
