package uz.sh.online_queue.exception.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
