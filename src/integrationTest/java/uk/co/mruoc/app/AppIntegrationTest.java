package uk.co.mruoc.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import uk.co.mruoc.app.env.AppExtension;
import uk.co.mruoc.app.env.AppExtensionConfig;
import uk.co.mruoc.app.env.TokenMother;
import uk.co.mruoc.rest.client.RestClient;
import uk.co.mruoc.rest.client.SimpleRestClient;
import uk.co.mruoc.rest.client.header.BearerTokenHeader;
import uk.co.mruoc.rest.client.header.DefaultHeaders;
import uk.co.mruoc.rest.client.header.Headers;
import uk.co.mruoc.rest.client.response.Response;

class AppIntegrationTest {

    private static final AppExtensionConfig EXTENSION_CONFIG = AppExtensionConfig.build();

    @RegisterExtension
    public static final AppExtension EXTENSION = new AppExtension(EXTENSION_CONFIG);

    private static final RestClient CLIENT = new SimpleRestClient();

    @Test
    void shouldNotAuthenticateIfNoAuthorizationHeaderSupplied() {
        String endpoint = getWidgetEndpoint();

        Response response = CLIENT.get(endpoint);

        assertThat(response.getStatusCode()).isEqualTo(401);
    }

    @Test
    void shouldAuthenticateSuccessfullyForKeycloak1User() {
        Headers headers = toHeaders(EXTENSION.toKeycloak1Token(TokenMother.demoUser1()));
        String endpoint = getWidgetEndpoint();

        Response response = CLIENT.get(endpoint, headers);

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    void shouldAuthenticateSuccessfullyForKeycloak2User() {
        Headers headers = toHeaders(EXTENSION.toKeycloak1Token(TokenMother.demoUser1()));
        String endpoint = getWidgetEndpoint();

        Response response = CLIENT.get(endpoint, headers);

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    private static String getWidgetEndpoint() {
        return String.format("%s/widgets/1", EXTENSION_CONFIG.getAppUri());
    }

    private static Headers toHeaders(String token) {
        Headers headers = new DefaultHeaders();
        headers.set(new BearerTokenHeader(token));
        return headers;
    }
}
