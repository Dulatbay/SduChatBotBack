package kz.sdu.chat.mainservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class DbNotFoundException extends RuntimeException {
    private HttpStatus status;
    private String message;
    private String description;
}
