package com.csuf.bidbash.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

@Configuration
public class AzureStorageConfig {
	

	@Value("${spring.azure.connection-string}")
	private String connectionString;
	
	@Value("${spring.azure.container-name}")
	private String containerName;

	@Bean
	public BlobContainerClient getClient() {
		
		return new BlobContainerClientBuilder().connectionString(connectionString).containerName(containerName)
				.buildClient();
	}
}
