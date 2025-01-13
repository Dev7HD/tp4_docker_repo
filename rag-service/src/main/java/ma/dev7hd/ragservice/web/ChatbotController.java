package ma.dev7hd.ragservice.web;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatbotController {
    private final ChatClient chatClient;
    private VectorStore vectorStore;

    public ChatbotController(ChatClient.Builder chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
        this.vectorStore = vectorStore;
    }
    @GetMapping("/chat")
    public String chat(String message){
        return chatClient.prompt()
                .user(message)
                .call().content();
    }
}
