package devCamp.WebApp.services;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

@Service
public interface ImageStorageService {

    String storeImage(String IncidentId, String fileName, String contentType, byte[] fileBuffer);
    
    @Async
    CompletableFuture<String> storeImageAsync(String IncidentId, String fileName, String contentType, byte[] fileBuffer);

    public InputStream getImage(String id);
    
    public byte[] getImageAsArray(String id);
    
}
