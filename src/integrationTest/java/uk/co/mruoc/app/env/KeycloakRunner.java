package uk.co.mruoc.app.env;

import static org.awaitility.Awaitility.await;

import com.tngtech.keycloakmock.api.KeycloakMock;
import com.tngtech.keycloakmock.api.ServerConfig;
import com.tngtech.keycloakmock.api.TokenConfig;
import java.time.Duration;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeycloakRunner {

    private KeycloakMock keycloak;

    public void startIfNotStarted(ServerConfig config) {
        if (keycloak == null) {
            startKeycloak(config);
        }
    }

    public String generateToken(TokenConfig config) {
        return keycloak.getAccessToken(config);
    }

    public void shutdownIfStarted() {
        if (Objects.isNull(keycloak)) {
            return;
        }
        keycloak.stop();
    }

    private void startKeycloak(ServerConfig config) {
        int port = config.getPort();
        log.info("starting keycloak mock on port {} with realm {}", port, config.getDefaultRealm());
        keycloak = new KeycloakMock(config);
        keycloak.start();
        waitForStartToComplete(port);
    }

    private static void waitForStartToComplete(int port) {
        log.info("waiting for mock keycloak startup to complete...");
        await().dontCatchUncaughtExceptions()
                .atMost(Duration.ofSeconds(15))
                .pollInterval(Duration.ofMillis(250))
                .until(PortReady.local(port));
    }
}
