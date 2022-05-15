package uk.co.mruoc.app.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Collection<IssuerConfig> issuers;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer()
                .authenticationManagerResolver(buildResolver());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private JwtIssuerAuthenticationManagerResolver buildResolver() {
        Map<String, AuthenticationManager> managers = new HashMap<>();
        for (IssuerConfig issuer : issuers) {
            log.debug("setting up issuer config for {}", issuer.getUri());
            JwtDecoder decoder = toDecoder(issuer);
            JwtAuthenticationProvider provider = new JwtAuthenticationProvider(decoder);
            provider.setJwtAuthenticationConverter(new JwtAuthenticationConverter());
            managers.put(issuer.getUri(), provider::authenticate);
        }
        return new JwtIssuerAuthenticationManagerResolver(managers::get);
    }

    private NimbusJwtDecoder toDecoder(IssuerConfig issuer) {
        NimbusJwtDecoder decoder = JwtDecoders.fromOidcIssuerLocation(issuer.getUri());
        OAuth2TokenValidator<Jwt> audienceValidator = new JwtAudienceValidator(issuer.getAudience());
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer.getUri());
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
        decoder.setJwtValidator(withAudience);
        return decoder;
    }
}
