package ma.dev7hd.ragservice.services.dataLoaders;

import org.springframework.core.io.Resource;

public interface IPDFLoader {
    void loadPDF(Resource pdfResource);
}
