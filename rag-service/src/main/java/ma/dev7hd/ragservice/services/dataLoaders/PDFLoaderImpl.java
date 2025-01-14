package ma.dev7hd.ragservice.services.dataLoaders;

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
    private final VectorStore vectorStore;

    @Override
    public void loadPDF(Resource pdfResource){
        // Process the PDF document
        PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfResource);
        List<Document> documents = reader.get();

        // Split the document into chunks
        TextSplitter textSplitter = new TokenTextSplitter();
        List<Document> chunks = textSplitter.split(documents);

        // Save the new chunks to the vector store
        vectorStore.accept(chunks);
    }
}
