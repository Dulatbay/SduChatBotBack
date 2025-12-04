package kz.sdu.chat.mainservice.rest.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class MessageResponse {
    private long id;
    private String content;
    private List<String> sources;
    private boolean isUser;
    private int number;
    private int version = 1;
    private String createdDate;
}
