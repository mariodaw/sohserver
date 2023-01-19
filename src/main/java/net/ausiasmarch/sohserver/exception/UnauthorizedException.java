package net.ausiasmarch.sohserver.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String msg) {
        super("ERROR: Unauthorized access attempt: " + msg);
    }
}
