package com.cortezdevdiego.talent_guard.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.http.MediaType;
import java.util.Map;

@Service
public class FeedbackService {

    private final RestClient restClient;

    public FeedbackService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:11434").build();
    }

    public String polirFeedback(String textoCru) {
        // O prompt que mandamos pra IA
        String prompt = "Refine o seguinte feedback corporativo para ser mais profissional, mantendo o ponto central, mas com tom construtivo e empático (responda apenas o texto refinado em português): " + textoCru;

        Map<String, Object> body = Map.of(
            "model", "llama3", // OU "llama3.2" (Verifique qual você baixou no 'ollama list')
            "prompt", prompt,
            "stream", false
        );

        try {
            Map resposta = restClient.post()
                .uri("/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(Map.class);

            return (String) resposta.get("response");
        } catch (Exception e) {
            return "Erro ao contatar a IA: " + e.getMessage();
        }
    }
}