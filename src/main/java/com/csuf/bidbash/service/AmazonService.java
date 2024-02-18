package com.csuf.bidbash.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class AmazonService {

	private final static String bucket_name = "bid-bash-bucket";

	@Autowired
	private AmazonS3 s3Client;

	public void uploadFiles(List<MultipartFile> files) {

		for (MultipartFile f : files) {
			File fileObj = convertToFile(f);
			String key = System.currentTimeMillis()+f.getOriginalFilename();
			s3Client.putObject(bucket_name, key, fileObj);
		}

	}

	private File convertToFile(MultipartFile file) {
		File f = new File(file.getOriginalFilename());

		try (FileOutputStream fos = new FileOutputStream(f)) {
			fos.write(file.getBytes());
		} catch (IOException ex) {
			System.out.println("Error Occurred" + ex.toString());
		}

		return f;
	}

}
