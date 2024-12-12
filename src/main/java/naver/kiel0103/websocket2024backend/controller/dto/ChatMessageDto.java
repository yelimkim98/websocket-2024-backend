package naver.kiel0103.websocket2024backend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private Long roomId;
    private Long userId;
    private String message;
}
