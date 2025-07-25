package kz.sdu.chat.mainservice.exceptions.handler;

import kz.sdu.chat.mainservice.exceptions.DbNotFoundException;
import kz.sdu.chat.mainservice.rest.dto.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(DbNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDbNotFoundException(DbNotFoundException ex) {
        log.error("DbNotFoundException exception: ", ex);

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getDescription());
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }
}
