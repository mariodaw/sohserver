package net.ausiasmarch.sohserver.exception;

public class CannotPerformOperationException extends RuntimeException {

    public CannotPerformOperationException(String msg) {
        super("ERROR: Cannot perform operation: " + msg);
    }

}