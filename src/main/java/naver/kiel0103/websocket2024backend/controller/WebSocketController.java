package naver.kiel0103.websocket2024backend.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import naver.kiel0103.websocket2024backend.controller.dto.ChatMessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    // 메세지 저장 테스트용 (DB 귀찮아서)
    private final List<ChatMessageDto> messages = new ArrayList<>();

    @PostConstruct
    void setUp() {
        messages.add(new ChatMessageDto(1L, 1234L, "Hello World"));
    }

    /*
     클라이언트가 "/pub/message" 경로로 STOMP 요청한거 받음

     요청 예시

     SEND
     destination:/pub/message/1
     content-type:application/json

     {"roomId":1, "userId":123,"message":"Hello World!"}
     ^@

     */
    @MessageMapping("/message")
    public void receiveMessage(ChatMessageDto dto) {
        log.debug("## 요청 들어왔음 - dto: {}", dto.toString());

        // 서버에 메세지 저장
        messages.add(dto);

        // "/pub/message/{roomId}"을 구독하고있는 모든 클라이언트에게 메세지 전송
        messagingTemplate.convertAndSend("/sub/message/" + dto.getRoomId(), dto);
    }

    @GetMapping("/messages/{roomId}")
    public ResponseEntity<List<ChatMessageDto>> getMessages(@PathVariable String roomId) {
        return ResponseEntity.ok(messages);
    }
}
