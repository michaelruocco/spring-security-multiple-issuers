package uk.co.mruoc.app.env;

import com.tngtech.keycloakmock.api.ServerConfig;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AppExtensionConfig {

    private final int appPort;

    private final KeycloakConfig keycloak1Config;
    private final KeycloakConfig keycloak2Config;

    public static AppExtensionConfig build() {
        return AppExtensionConfig.builder()
                .appPort(FreePortFinder.findAvailableTcpPort())
                .keycloak1Config(keycloakConfig(1))
                .keycloak2Config(keycloakConfig(2))
                .build();
    }

    public String[] getArgs() {
        return new String[] {
            String.format("--server.port=%s", appPort),
            String.format("--auth.issuer.1.uri=%s", keycloak1Config.toIssuerUri()),
            String.format("--auth.issuer.1.audience=%s", keycloak1Config.getAudience()),
            String.format("--auth.issuer.2.uri=%s", keycloak2Config.toIssuerUri()),
            String.format("--auth.issuer.2.audience=%s", keycloak2Config.getAudience())
        };
    }

    public String getAppUri() {
        return String.format("http://localhost:%d", appPort);
    }

    public ServerConfig getKeycloak1ServerConfig() {
        return keycloak1Config.toServerConfig();
    }

    public ServerConfig getKeycloak2ServerConfig() {
        return keycloak2Config.toServerConfig();
    }

    private static KeycloakConfig keycloakConfig(int index) {
        return KeycloakConfig.builder()
                .port(FreePortFinder.findAvailableTcpPort())
                .realm(String.format("test-realm-%d", index))
                .audience(String.format("test-audience-%d", index))
                .build();
    }
}
