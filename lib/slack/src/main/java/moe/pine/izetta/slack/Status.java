package moe.pine.izetta.slack;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class Status {
    private boolean ok;

    @Nullable
    private String error;
}
