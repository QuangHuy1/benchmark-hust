package vn.edu.benchmarkhust.service.connector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import vn.edu.benchmarkhust.model.response.TsHustResponse;

import java.text.Normalizer;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
public class TsHustClient {
    @Value("${token:ZSH6sZKRS9ymfFlxeHtyo65T4rwenanJ3afaqQik}")
    private String token;
    @Value("${csrf-token:ZSH6sZKRS9ymfFlxeHtyo65T4rwenanJ3afaqQik}")
    private String csrfToken;
    private final RestTemplate restTemplate;

    public TsHustClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<TsHustResponse> getTsHustResponse(String faculty, String year) {
        String normal = Normalizer.normalize(faculty.toLowerCase()
                        .replace(" (", "-")
                        .replace(") ", "-")
                        .replace(")", "")
                        .replace(" - ", "-")
                        .replace(" ", "-"),
                Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String output = pattern.matcher(normal).replaceAll("")
                .replace("ê", "e")
                .replace("ơ", "o")
                .replace("ô", "o")
                .replace("â", "a")
                .replace("ă", "a")
                .replace("ư", "u")
                .replace("đ", "d");

        HttpHeaders headers = buildHeader();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("_token", token);
        map.add("_slug", output);
        map.add("_year", year);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            String url = "https://ts.hust.edu.vn/training-point";
            return Optional.ofNullable(restTemplate.postForEntity(url, request, TsHustResponse.class).getBody());
        } catch (Exception e) {
            log.error("Cannot getTsHustResponse: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    private HttpHeaders buildHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("x-requested-with", "XMLHttpRequest");
        headers.add("cookie", csrfToken);
        return headers;
    }
}
