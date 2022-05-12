package uk.co.mruoc.app.env;

import com.tngtech.keycloakmock.api.TokenConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenMother {

    public static TokenConfig demoUser1() {
        return demoUser1Builder().build();
    }

    public static TokenConfig.Builder demoUser1Builder() {
        return TokenConfig.aTokenConfig()
                .withName("demo user-one")
                .withAudience("test-audience-1")
                .withEmail("demo-user-1@hotmail.com");
    }

    public static TokenConfig demoUser3() {
        return demoUser3Builder().build();
    }

    public static TokenConfig.Builder demoUser3Builder() {
        return TokenConfig.aTokenConfig()
                .withName("demo user-three")
                .withAudience("test-audience-2")
                .withEmail("demo-user-3@hotmail.com");
    }
}
