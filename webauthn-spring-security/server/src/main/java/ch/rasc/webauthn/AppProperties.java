package ch.rasc.webauthn;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;

@ConfigurationProperties(prefix = "app")
@Validated
public record AppProperties(@NotEmpty String relyingPartyId,
    @NotEmpty String relyingPartyName, @NotEmpty Set<String> relyingPartyOrigins,
    Set<String> corsOrigins) {

}
