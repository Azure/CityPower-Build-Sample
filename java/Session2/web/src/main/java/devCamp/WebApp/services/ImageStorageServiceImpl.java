package devCamp.WebApp.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import devCamp.WebApp.properties.ImageStorageProperties;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {
    private static final Logger LOG = LoggerFactory.getLogger(ImageStorageServiceImpl.class);

    @Autowired
    private ImageStorageProperties imageStorageProperties;

    @Override
    public String storeImage(String IncidentId, String fileName,String contentType, byte[] fileBuffer) {
    	String imageFileName = null;
            try {
            	imageFileName = getIncidentImageFilename(IncidentId, fileName);
            	String completeFileName = String.format("%s/%s",imageStorageProperties.getStorageLocation(),imageFileName);
            	
            	FileOutputStream fos = new FileOutputStream(completeFileName);
            	fos.write(fileBuffer);
            	fos.close();
            } catch ( IOException e) {
                // TODO Auto-generated catch block
                LOG.error("storeImageAsync - error {}", e);
                imageFileName = null;
            }
        return imageFileName;
    }
    
    

    @Async
    @Override
    public CompletableFuture<String> storeImageAsync(String IncidentId, String fileName,
                                                                  String contentType, byte[] fileBuffer) {
        CompletableFuture<String> cf = new CompletableFuture<>();
        CompletableFuture.runAsync(() ->{
            try {
            	String imageFileName = getIncidentImageFilename(IncidentId, fileName);
            	String completeFileName = String.format("%s/%s",imageStorageProperties.getStorageLocation(),imageFileName);
            	FileOutputStream fos = new FileOutputStream(completeFileName);
            	fos.write(fileBuffer);
            	fos.close();
                //return result
                cf.complete(imageFileName);
            } catch ( IOException e) {
                // TODO Auto-generated catch block
                LOG.error("storeImageAsync - error {}", e);
                cf.completeExceptionally(e);
            }
        });
        return cf;
    }
    
    public InputStream getImage(String id){
    	FileInputStream fis = null;
    	try {
	    	String completeFileName = String.format("%s/%s",imageStorageProperties.getStorageLocation(),id);
	    	
			fis = new FileInputStream(completeFileName);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return fis;
    }
    
    public byte[] getImageAsArray(String id) {
    	byte[] b = null;
		try {
	    	String completeFileName = String.format("%s/%s",imageStorageProperties.getStorageLocation(),id);
	    	FileInputStream fis;
			fis = new FileInputStream(completeFileName);
			b = IOUtils.toByteArray(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return b;
    }
    
    private String getIncidentImageFilename(String IncidentId,String FileName) {
        String fileExt = FilenameUtils.getExtension(FileName);
        return String.format("%s.%s", IncidentId,fileExt);
    }
}
