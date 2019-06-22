package moe.pine.izetta.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@ConfigurationProperties("slack")
public class SlackProperties {
    private @NotBlank String token;
    private @NotBlank String channel;
    private @NotBlank String text;
}
