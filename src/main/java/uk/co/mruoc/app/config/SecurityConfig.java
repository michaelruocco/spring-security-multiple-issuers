package uk.co.mruoc.app.config;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String[] issuerUris;

    public SecurityConfig(@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uris}") String[] issuerUris) {
        this.issuerUris = issuerUris;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info(
                "configuring jwt issuer authentication manager resolver with issuer uris {}",
                Arrays.toString(issuerUris));
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .authenticationManagerResolver(new JwtIssuerAuthenticationManagerResolver(issuerUris));
    }
}
