package naver.kiel0103.websocket2024backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*
         registry.addEndpoint("/ws")
          - '/ws' 경로를 통해 STOMP 엔드포인트를 등록
          - 클라이언트는 'ws://your-server-url/ws' 로 연결을 시도하게 됨
        */
        registry.addEndpoint("/ws")
                /*
                 setAllowedOrigins("*")
                  - 모든 출처의 연결 요청을 허용
                  - 이를 통해
                  - CORS(Cross-Origin Resource Sharing) 제약 없이
                  - 다양한 출처의 클라이언트가 서버에 연결할 수 있다.

                  클라이언트 URL 같은거로 변경해주면 좀더 합당할듯
                 */
                .setAllowedOrigins("*");
    }

    /*
     웹소켓 연결 후
     /sub 가 prefix 로 붙으면 메세지 구독 요청
     /pub 이 prefix 로 붙은 요청은 메세지 발송 요청
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
