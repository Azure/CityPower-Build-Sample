package devCamp.WebApp.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import devCamp.WebApp.properties.AzureStorageAccountProperties;

@Primary
@Service
public class AzureImageStorageServiceImpl implements ImageStorageService {

	@Autowired
	private AzureStorageAccountProperties azureStorageProperties;

	private byte[] downloadImage(CloudBlockBlob imgBlob){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			imgBlob.download(bos);
		} catch (StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	public byte[] getImageAsArray(String imagefilename) {
		byte[] b = null;

		CloudBlobClient serviceClient = cloudStorageAccount.createCloudBlobClient();
		// Container name must be lower case.
		CloudBlobContainer container;
		try {
			container = serviceClient.getContainerReference(azureStorageProperties.getBlobContainer());
			CloudBlockBlob imgBlob = container.getBlockBlobReference(imagefilename);
			b = downloadImage(imgBlob);
		} catch (URISyntaxException | StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return result
		return b;    	
	}

	@Autowired
	private CloudStorageAccount cloudStorageAccount;

	public String storeImage(String IncidentId, String fileName, String contentType, byte[] fileBuffer) {
		CloudBlobClient serviceClient = cloudStorageAccount.createCloudBlobClient();
		String imageUriString = null;
		// Container name must be lower case.
		CloudBlobContainer container;
		try {
			container = serviceClient.getContainerReference(azureStorageProperties.getBlobContainer());
			container.createIfNotExists();
			// Set anonymous access on the container.
			BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
			containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
			container.uploadPermissions(containerPermissions);
			String incidentBlob = getIncidentBlobFilename(IncidentId,fileName);
			CloudBlockBlob imgBlob = container.getBlockBlobReference(incidentBlob);
			imgBlob.getProperties().setContentType(contentType);
			imgBlob.uploadFromByteArray(fileBuffer, 0, fileBuffer.length);
			imageUriString = incidentBlob;
		} catch (URISyntaxException | StorageException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return result
		return imageUriString;

	}    

	private String getIncidentBlobFilename(String IncidentId,String FileName) {
		String fileExt = FilenameUtils.getExtension(FileName);
		return String.format("%s.%s", IncidentId,fileExt);
	}

}

