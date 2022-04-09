package uz.sh.online_queue.exception.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import uz.sh.online_queue.response.AppError;

@RestController
@ControllerAdvice("com.example.onlinequeue")
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AppError invalidTokenExceptionHandler(InvalidTokenException e, WebRequest request) {
        return AppError.builder()
                .message(e.getMessage())
                .request(request)
                .status(HttpStatus.FORBIDDEN)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError badRequestException(InvalidTokenException e, WebRequest request) {
        return AppError.builder()
                .message(e.getMessage())
                .request(request)
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    public AppError conflictException(InvalidTokenException e, WebRequest request) {
        return AppError.builder()
                .message(e.getMessage())
                .request(request)
                .status(HttpStatus.CONFLICT)
                .build();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppError notFoundException(InvalidTokenException e, WebRequest request) {
        return AppError.builder()
                .message(e.getMessage())
                .request(request)
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public AppError noContentException(InvalidTokenException e, WebRequest request) {
        return AppError.builder()
                .message(e.getMessage())
                .request(request)
                .status(HttpStatus.NO_CONTENT)
                .build();
    }



}
