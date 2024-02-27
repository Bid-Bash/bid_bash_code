package com.csuf.bidbash.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.csuf.bidbash.pojos.ProductFile;
import com.csuf.bidbash.repos.ProductFileRepo;

@Service
public class AmazonService {

	private final static String bucket_name = "bid-bash-bucket";

	@Autowired
	private AmazonS3 s3Client;

	@Autowired
	private ProductFileRepo pfRepo;

	public void uploadFiles(int pId, MultipartFile[] files)
			throws AmazonServiceException, SdkClientException, IOException {

		if (files == null || files.length == 0) {
			return;
		}

		for (MultipartFile file : files) {
			String key = generateUniqueKey(file.getOriginalFilename());
			ObjectMetadata metaData = new ObjectMetadata();
			metaData.setContentType(file.getContentType());

			try (InputStream inputStream = file.getInputStream()) {
				s3Client.putObject(bucket_name, key, inputStream, metaData);
				String objUrl = s3Client.getUrl(bucket_name, key).toString();
				ProductFile pf = new ProductFile(pId, objUrl);
				pfRepo.save(pf);
			}
		}
	}

	private String generateUniqueKey(String originalFilename) {
		return System.currentTimeMillis() + "_" + originalFilename;
	}
}
