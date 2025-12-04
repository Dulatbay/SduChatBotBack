package kz.sdu.chat.mainservice.feign.sduai.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessageSendRequest {
    private String question;
    private String chat_id;
}
