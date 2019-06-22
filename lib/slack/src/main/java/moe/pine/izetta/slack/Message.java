package moe.pine.izetta.slack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Nullable
    private String token;

    @Nullable
    private String channel;

    @Nullable
    private String username;

    @Nullable
    private String iconUrl;

    @Nullable
    private String text;
}
