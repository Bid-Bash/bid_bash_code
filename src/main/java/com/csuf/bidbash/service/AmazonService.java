package com.csuf.bidbash.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.csuf.bidbash.pojos.ProductFile;
import com.csuf.bidbash.repos.ProductFileRepo;

@Service
public class AmazonService {

	private final static String bucket_name = "bid-bash-bucket";

	@Autowired
	private AmazonS3 s3Client;

	@Autowired
	private ProductFileRepo pfRepo;

	public void uploadFiles(int pId, List<MultipartFile> files)
			throws AmazonServiceException, SdkClientException, IOException {

		for (MultipartFile f : files) {
			String key = System.currentTimeMillis() + f.getOriginalFilename();
			s3Client.putObject(bucket_name, key, f.getInputStream(), null);
			String objUrl = s3Client.getUrl(bucket_name, key).toString();
			ProductFile pf = new ProductFile(pId, objUrl);
			pfRepo.save(pf);
		}

	}

}
