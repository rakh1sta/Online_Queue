package uz.sh.online_queue.exception.service;

public class ConflictException extends RuntimeException {

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
