package com.example.gestioncatalogue.controller;

import com.example.gestioncatalogue.tools.ProductTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder, ChatMemory chatMemory, ProductTools productTools ) {
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultAdvisors(a -> a.param(ChatMemory.CONVERSATION_ID, "default"))
                .defaultTools(productTools)
                .defaultSystem("Tu es un assistant e- commerce d’elite pour la boutique Polytechnique Sousse Regles strictes que tu dois imperativement respecter :\n" +
                        "- Reponds UNIQUEMENT aux questions relatives aux produits du catalogue .\n" +
                        "- Utilise SYSTEMATIQUEMENT tes outils pour verifier les prix et disponibilites .\n" +
                        "- Si la question concerne un autre sujet , reponds exactement ’Je ne sais pas ’\n" +
                        "- Ne divulgue jamais de fausses informations inventees .\n  ")

                .build();
    }
    @GetMapping("/agent")
    public String chat(@RequestParam String message){
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
