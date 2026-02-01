package com.cortezdevdiego.talent_guard.controller;

import com.cortezdevdiego.talent_guard.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/hr")
@RequiredArgsConstructor
public class HrController {

    private final FeedbackService feedbackService;

    @PostMapping("/refine")
    public ResponseEntity<Map<String, String>> refinarFeedback(@RequestBody Map<String, String> request) {
        // Log para vermos no terminal
        System.out.println(">>> 🏁 CHEGOU NO CONTROLLER! CONEXÃO BEM SUCEDIDA!");
        
        String original = request.get("feedback");
        System.out.println(">>> TEXTO RECEBIDO: " + original);

        try {
            // Chama a IA (Ollama)
            String melhorado = feedbackService.polirFeedback(original);
            System.out.println(">>> IA RESPONDEU: " + melhorado);
            
            return ResponseEntity.ok(Map.of(
                "original", original,
                "refinado", melhorado
            ));
        } catch (Exception e) {
            System.out.println(">>> 💥 ERRO NA CONEXÃO COM A IA: " + e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("erro", e.getMessage()));
        }
    }
}