package com.pos.pos_system.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.pos_system.config.PaystackProperties;
import com.pos.pos_system.dto.payment.PaystackInitializeResponse;
import com.pos.pos_system.dto.payment.PaystackVerifyResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

@Service
public class PaystackClient {
    private final PaystackProperties props;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public PaystackClient(PaystackProperties props, ObjectMapper objectMapper) {
        this.props = props;
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public PaystackInitializeResponse initializePayment(String email, long amountPesewas, String reference) {
        requireConfigured();

        try {
            String body = objectMapper.writeValueAsString(Map.of(
                    "email", email,
                    "amount", amountPesewas,
                    "reference", reference
            ));

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(props.getBaseUrl() + "/transaction/initialize"))
                    .timeout(Duration.ofSeconds(20))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + props.getSecretKey())
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(resp.body(), PaystackInitializeResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Paystack initialize failed: " + e.getMessage(), e);
        }
    }

    public PaystackVerifyResponse verifyPayment(String reference) {
        requireConfigured();

        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(props.getBaseUrl() + "/transaction/verify/" + reference))
                    .timeout(Duration.ofSeconds(20))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + props.getSecretKey())
                    .GET()
                    .build();

            HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(resp.body(), PaystackVerifyResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Paystack verify failed: " + e.getMessage(), e);
        }
    }

    private void requireConfigured() {
        if (props.getSecretKey() == null || props.getSecretKey().isBlank() || props.getSecretKey().equals("REPLACE_ME")) {
            throw new RuntimeException("Paystack secret key is not configured. Set paystack.secret-key in application.properties");
        }
    }
}

