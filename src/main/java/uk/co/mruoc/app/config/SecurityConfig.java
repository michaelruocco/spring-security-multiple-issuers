package uk.co.mruoc.app.config;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;

@Configuration

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String[] issuerUris;
    private final String[] audiences;

    public SecurityConfig(@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uris}") String[] issuerUris,
                          @Value("${spring.security.oauth2.resourceserver.jwt.audiences}") String[] audiences) {
        this.issuerUris = issuerUris;
        this.audiences = audiences;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer().authenticationManagerResolver(buildResolver());
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private JwtIssuerAuthenticationManagerResolver buildResolver() {
        Map<String, AuthenticationManager> managers = new HashMap<>();
        for (String issuer : issuerUris) {
            JwtDecoder decoder = toDecoder(issuer);
            JwtAuthenticationProvider provider = new JwtAuthenticationProvider(decoder);
            provider.setJwtAuthenticationConverter(new JwtAuthenticationConverter());
            managers.put(issuer, provider::authenticate);
        }
        return new JwtIssuerAuthenticationManagerResolver(managers::get);
    }

    private NimbusJwtDecoder toDecoder(String issuerUri) {
        NimbusJwtDecoder decoder = JwtDecoders.fromOidcIssuerLocation(issuerUri);
        OAuth2TokenValidator<Jwt> audienceValidator = new JwtAudienceValidator(audiences);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
        decoder.setJwtValidator(withAudience);
        return decoder;
    }

}
