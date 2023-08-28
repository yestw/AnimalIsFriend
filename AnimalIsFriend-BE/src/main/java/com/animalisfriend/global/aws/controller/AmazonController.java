package com.animalisfriend.global.aws.controller;

import java.io.Serializable;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animalisfriend.global.aws.service.AmazonS3Service;
import com.animalisfriend.global.common.auth.AuthRequired;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/s3")
public class AmazonController {

	private final AmazonS3Service amazonS3Service;

	@GetMapping
	@AuthRequired
	public Map<String, Serializable> getPreSignedUrl() {
		return amazonS3Service.getPreSignedUrl();
	}
}
