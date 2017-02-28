package devCamp.WebApp.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	public byte[] getImageAsArray(String imagefilename) {
		byte[] b = null;
		try {
			String completeFileName = String.format("%s/%s",imageStorageProperties.getStorageLocation(),imagefilename);
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
