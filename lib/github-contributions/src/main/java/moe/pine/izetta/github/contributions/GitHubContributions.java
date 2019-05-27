package moe.pine.izetta.github.contributions;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GitHubContributions {
    private static final String ENDPOINT = "https://github.com/users/%s/contributions";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final RestTemplate restTemplate;

    public GitHubContributions(
        @Nonnull final RestTemplate restTemplate
    ) {
        this.restTemplate = Objects.requireNonNull(restTemplate);
    }

    @Nonnull
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Map<LocalDate, Integer> collect(
        @Nonnull final String username
    ) {
        Objects.requireNonNull(username);

        return parseBody(fetchBody(username));
    }

    @Nonnull
    @VisibleForTesting
    @SuppressWarnings("ResultOfMethodCallIgnored")
    String fetchBody(
        @Nonnull final String username
    ) {
        Objects.requireNonNull(username);

        final String endpoint = String.format(ENDPOINT, username);
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML));

        final HttpEntity<?> request = new HttpEntity<>(null, headers);
        final ResponseEntity<String> response =
            restTemplate.exchange(endpoint, HttpMethod.GET, request, String.class);

        final String body = response.getBody();
        if (StringUtils.isEmpty(body)) {
            throw new RuntimeException(
                String.format(
                    "A HTTP body is empty :: username=%s, endpoint=%s",
                    username,
                    endpoint));
        }

        return body;
    }

    @Nonnull
    @VisibleForTesting
    @SuppressWarnings("ResultOfMethodCallIgnored")
    Map<LocalDate, Integer> parseBody(
        @Nonnull final String body
    ) {
        Objects.requireNonNull(body);

        final Document doc = Jsoup.parse(body);
        final Elements elements = doc.select("rect");
        return elements
            .stream()
            .flatMap(element -> {
                final String dateString = element.attr("data-date");
                final String countString = element.attr("data-count");

                if (StringUtils.isEmpty(dateString)) {
                    return Stream.empty();
                }
                if (StringUtils.isEmpty(countString)) {
                    return Stream.empty();
                }

                final LocalDate date = LocalDate.parse(dateString, FORMATTER);
                final Integer count = Integer.valueOf(countString);
                return Stream.of(Pair.of(date, count));
            })
            .collect(Collectors.toUnmodifiableMap(Pair::getKey, Pair::getValue));
    }
}
