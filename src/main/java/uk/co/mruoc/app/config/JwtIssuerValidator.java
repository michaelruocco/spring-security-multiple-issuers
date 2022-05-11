package uk.co.mruoc.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public class JwtIssuerValidator implements OAuth2TokenValidator<Jwt> {

    private final Collection<String> issuers;

    public JwtIssuerValidator(String[] issuers) {
        this(Arrays.asList(issuers));
    }

    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if (issuers.contains(jwt.getIssuer().toString())) {
            return OAuth2TokenValidatorResult.success();
        }
        return OAuth2TokenValidatorResult.failure(buildError());
    }

    private static OAuth2Error buildError() {
        return new OAuth2Error("invalid_token", "A required issuer is missing", null);
    }
}
