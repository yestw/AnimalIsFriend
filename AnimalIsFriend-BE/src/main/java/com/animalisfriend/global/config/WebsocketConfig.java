package com.animalisfriend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

	//STOMP에서 사용하는 메시지 브로커를 설정하는 메소드
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//내장 메시지 브로커를 사용하기 위한 메소드
		registry.enableSimpleBroker("/queue");
		registry.setApplicationDestinationPrefixes("/pub");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
			//처음 웹소켓 Handshake를 위한 경로
			.addEndpoint("/ws")
			.setAllowedOriginPatterns("http://localhost:3000/", "http://localhost:8080/")
			.withSockJS();
	}

}
