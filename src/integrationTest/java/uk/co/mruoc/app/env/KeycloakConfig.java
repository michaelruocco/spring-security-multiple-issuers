package uk.co.mruoc.app.env;

import com.tngtech.keycloakmock.api.ServerConfig;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KeycloakConfig {

    private final int port;
    private final String realm;
    private final String audience;

    public ServerConfig toServerConfig() {
        return ServerConfig.aServerConfig()
                .withPort(port)
                .withDefaultRealm(realm)
                .build();
    }

    public String toIssuerUri() {
        return String.format("http://localhost:%d/auth/realms/%s", port, realm);
    }
}
