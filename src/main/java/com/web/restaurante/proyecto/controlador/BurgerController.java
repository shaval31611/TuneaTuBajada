/*package com.web.restaurante.proyecto.controlador;
import java.util.Arrays;
 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@RestController
@RequestMapping("/api/burger")
public class BurgerController {
 
    private final WebClient webClient;
 
    public BurgerController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("").build();
    }
 
    @PostMapping("/preview")
    public ResponseEntity<String> getBurgerPreview(@RequestBody BurgerRequest burgerRequest) {
        String prompt  = generatePrompt(burgerRequest.getIngredients());
        //String apiKey  = "";
 
        try {
            String response = webClient.post()
                    .uri("/images/generations")
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(new DalleRequest(prompt))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
 
            System.out.println("API Response: " + response);
            String imageUrl = extractImageUrl(response);
            System.out.println("Image URL: " + imageUrl);
            return ResponseEntity.ok(imageUrl);
 
        } catch (Exception e) {
            System.out.println("ERROR OPENAI: " + e.getMessage());
            return ResponseEntity.status(500).body("ERROR: " + e.getMessage());
        }
    }
 
    private String generatePrompt(String[] ingredients) {
        StringBuilder prompt = new StringBuilder("A delicious burger with: ");
        for (String ingredient : ingredients) {
            prompt.append(ingredient).append(", ");
        }
        prompt.append("food photography, high quality, appetizing");
        return prompt.toString();
    }
 
    private String extractImageUrl(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode urlNode  = rootNode.path("data").get(0).path("url");
            return urlNode.asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 
    static class BurgerRequest {
        private String[] ingredients;
 
        public String[] getIngredients() { return ingredients; }
        public void setIngredients(String[] ingredients) { this.ingredients = ingredients; }
 
        @Override
        public String toString() {
            return "BurgerRequest{ingredients=" + Arrays.toString(ingredients) + '}';
        }
    }
 
    static class DalleRequest {
        private final String model  = "dall-e-3";
        private final String prompt;
        private final int    n      = 1;
        private final String size   = "1024x1024";
 
        public DalleRequest(String prompt) { this.prompt = prompt; }
 
        public String getModel()  { return model;  }
        public String getPrompt() { return prompt; }
        public int    getN()      { return n;      }
        public String getSize()   { return size;   }
    }
}*/