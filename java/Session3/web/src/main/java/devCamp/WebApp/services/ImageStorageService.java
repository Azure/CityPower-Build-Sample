package devCamp.WebApp.services;


import org.springframework.stereotype.Service;

@Service
public interface ImageStorageService {

    String storeImage(String IncidentId, String fileName, String contentType, byte[] fileBuffer);

    public byte[] getImageAsArray(String imagefilename);
    
}
