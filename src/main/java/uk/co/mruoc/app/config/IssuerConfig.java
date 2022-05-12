package uk.co.mruoc.app.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Data
public class IssuerConfig {

    private String uri;
    private String audience;
}
