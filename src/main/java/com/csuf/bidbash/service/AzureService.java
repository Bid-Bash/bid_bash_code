package com.csuf.bidbash.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.csuf.bidbash.pojos.ProductFile;
import com.csuf.bidbash.repos.ProductFileRepo;

@Service
public class AzureService {

	@Autowired
	private BlobContainerClient client;

	@Autowired
	private ProductFileRepo repo;

	public void uploadFiles(int pId, MultipartFile[] files) {

		for (MultipartFile file : files) {
			String key = generateUniqueKey(file.getOriginalFilename());
			BlobClient blobClient = client.getBlobClient(key);
			BlobHttpHeaders header = new BlobHttpHeaders();
			header.setContentType(file.getContentType());
			try (InputStream stream = file.getInputStream()) {
				blobClient.upload(stream);
				blobClient.setHttpHeaders(header);
			} catch (Exception e) {
				System.out.println(e);
			}

			String url = blobClient.getBlobUrl();
			ProductFile pf = new ProductFile(pId, url);
			repo.save(pf);
		}
	}

	private String generateUniqueKey(String originalFilename) {
		return System.currentTimeMillis() + "_" + originalFilename;
	}

}
