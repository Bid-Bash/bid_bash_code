package com.csuf.bidbash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfig {

	@Bean
	public AmazonS3 generateS3Client() {
		AWSCredentials cred = new BasicAWSCredentials("WHO",
				"WHO");
		return AmazonS3ClientBuilder.standard().withRegion("us-west-1")
				.withCredentials(new AWSStaticCredentialsProvider(cred)).build();

	}

}
