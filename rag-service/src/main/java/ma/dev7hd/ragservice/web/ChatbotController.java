package ma.dev7hd.ragservice.web;

import lombok.AllArgsConstructor;
import ma.dev7hd.ragservice.services.chatService.IChatService;
import ma.dev7hd.ragservice.services.dataLoaders.IPDFLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class ChatbotController {
    private final IChatService chatService;
    private final IPDFLoader pdfLoader;

    @PostMapping(value = "/chat", produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(@RequestBody String message){
        return chatService.processQuestion(message);
    }

    @PostMapping(value = "/load-pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void loadPDF(@RequestPart(value = "pdf_file") MultipartFile file){
        Resource pdfResource = file.getResource();
        pdfLoader.loadPDF(pdfResource);
    }


}
