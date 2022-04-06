package com.ezshopping.config.websocket;

import com.ezshopping.websocket.handler.TextWebSocketHandlerEZ;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final TextWebSocketHandlerEZ textWebSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(textWebSocketHandler, "/web-socket/**")
                .setAllowedOrigins("*")
                .addInterceptors(stationInterceptor());
    }

    @Bean
    public HandshakeInterceptor stationInterceptor() {
        return new HandshakeInterceptor() {
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                           org.springframework.web.socket.WebSocketHandler wsHandler, Map<String, Object> attributes)  {
                // Get the URI segment corresponding to the station id during handshake
                String path = request.getURI().getPath();
                String stationId = path.substring(path.lastIndexOf('/') + 1);

                // This will be added to the websocket session
                attributes.put("station", stationId);
                return true;
            }

            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       org.springframework.web.socket.WebSocketHandler wsHandler, Exception exception) {
                // Nothing to do after handshake
            }
        };
    }


}