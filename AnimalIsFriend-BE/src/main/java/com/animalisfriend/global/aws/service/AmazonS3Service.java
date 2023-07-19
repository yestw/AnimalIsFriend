package com.animalisfriend.global.aws.service;

import static org.springframework.boot.context.properties.bind.Bindable.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.animalisfriend.global.config.AwsS3Config;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AmazonS3Service {

	private final AwsS3Config awsS3Config;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	public Map<String, Serializable> getPreSignedUrl() {

		String encodeFileName = "image_"+ LocalDateTime.now();
		String objectKey = "image/"+encodeFileName;

		Date expiration = new Date();
		Long expTimeMillis = expiration.getTime();
		expTimeMillis += (3 * 60 * 1000); //3분
		expiration.setTime(expTimeMillis);

		GeneratePresignedUrlRequest generatePresignedUrlRequest
			= new GeneratePresignedUrlRequest(bucketName, objectKey)
			.withMethod(HttpMethod.PUT)
			.withExpiration(expiration);

		Map<String, Serializable> result = new HashMap<>();
		result.put("preSignedUrl", awsS3Config.amazonS3().generatePresignedUrl(generatePresignedUrlRequest));
		result.put("encodedFileName", encodeFileName);

		return result;
	}
}
