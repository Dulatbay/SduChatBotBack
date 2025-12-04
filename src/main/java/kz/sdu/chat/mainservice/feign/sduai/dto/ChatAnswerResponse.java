package kz.sdu.chat.mainservice.feign.sduai.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatAnswerResponse {
    private String question;
    private String answer;
    private List<String> sources;
}
