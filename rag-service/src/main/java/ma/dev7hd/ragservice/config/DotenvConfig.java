package ma.dev7hd.ragservice.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {
    public DotenvConfig() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        String openAiApiKey = dotenv.get("OPEN_AI_API_KEY");
        if (openAiApiKey != null) {
            System.setProperty("spring.ai.openai.api-key", openAiApiKey);
        } else {
            System.out.println("No OpenAI API Key set");
        }
        /*System.out.println("***********************************************************************************");
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey() + ": " + entry.getValue());
        });
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");*/
    }
}