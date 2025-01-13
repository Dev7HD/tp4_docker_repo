package ma.dev7hd.ragservice.dataLoaders;

import lombok.AllArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PDFLoaderImpl implements IPDFLoader{
    private VectorStore vectorStore;

    public void loadPDF(Resource pdfResource){
        PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfResource);
        List<Document> documents = reader.get();
        TextSplitter textSplitter = new TokenTextSplitter();
        List<Document> chunks = textSplitter.split(documents);
        vectorStore.accept(chunks);
    }
}
