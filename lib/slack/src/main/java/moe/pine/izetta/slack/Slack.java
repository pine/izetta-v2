package moe.pine.izetta.slack;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RequiredArgsConstructor
public class Slack {
    @SuppressWarnings("WeakerAccess")
    public static final String SLACK_CHAT_POST_MESSAGE = "https://slack.com/api/chat.postMessage";

    private final RestTemplate restTemplate;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void postMessage(final Message message) {
        Objects.requireNonNull(message);

        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + message.getToken());

        final MultiValueMap<String, String> values = new LinkedMultiValueMap<>();
        values.add("channel", message.getChannel());
        values.add("username", message.getUsername());
        values.add("icon_url", message.getIconUrl());
        values.add("text", message.getText());

        final HttpEntity<?> request = new HttpEntity<>(values, headers);
        final Status status = restTemplate.postForObject(SLACK_CHAT_POST_MESSAGE, request, Status.class);

        if (status == null) {
            throw new RuntimeException("Failed to call `chat.postMessage` API. An empty response received.");
        }
        if (!status.isOk()) {
            throw new RuntimeException(
                String.format(
                    "Failed to call `chat.postMessage` API :: ok=%s, error=\"%s\"",
                    String.valueOf(status.isOk()),
                    status.getError()));
        }
    }
}
