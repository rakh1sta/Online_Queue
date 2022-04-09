package uz.sh.online_queue.exception.service;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
