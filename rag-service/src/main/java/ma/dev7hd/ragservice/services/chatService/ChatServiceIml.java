package ma.dev7hd.ragservice.services.chatService;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceIml implements IChatService {
    private final ChatClient chatClient;
    private VectorStore vectorStore;

    public ChatServiceIml(ChatClient.Builder chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
        this.vectorStore = vectorStore;
    }

    @Override
    public String processQuestion(String message){
        return chatClient.prompt()
                .user(message)
                .call().content();
    }
}
