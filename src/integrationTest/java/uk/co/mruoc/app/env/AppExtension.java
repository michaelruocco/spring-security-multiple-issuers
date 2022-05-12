package uk.co.mruoc.app.env;

import com.tngtech.keycloakmock.api.TokenConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

@RequiredArgsConstructor
public class AppExtension implements BeforeAllCallback, AfterAllCallback, ExtensionContext.Store.CloseableResource {

    private static final KeycloakRunner KEYCLOAK_1 = new KeycloakRunner();
    private static final KeycloakRunner KEYCLOAK_2 = new KeycloakRunner();

    private final AppExtensionConfig extensionConfig;

    @Override
    public void beforeAll(ExtensionContext context) {
        KEYCLOAK_1.startIfNotStarted(extensionConfig.getKeycloak1ServerConfig());
        KEYCLOAK_2.startIfNotStarted(extensionConfig.getKeycloak2ServerConfig());
        AppRunner.startAppIfNotStarted(extensionConfig);
    }

    @Override
    public void afterAll(ExtensionContext context) {
        shutdown();
    }

    @Override
    public void close() {
        shutdown();
    }

    public String toKeycloak1Token(TokenConfig tokenConfig) {
        return KEYCLOAK_1.generateToken(tokenConfig);
    }

    public String buildKeycloak2Token(TokenConfig tokenConfig) {
        return KEYCLOAK_2.generateToken(tokenConfig);
    }

    private void shutdown() {
        AppRunner.shutdownIfStarted();
        KEYCLOAK_1.shutdownIfStarted();
        KEYCLOAK_2.shutdownIfStarted();
    }
}
