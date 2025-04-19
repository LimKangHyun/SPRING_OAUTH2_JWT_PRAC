package io.kanghyun.jwt_auth_prac.member.exception;

public class UnavailableProviderException extends RuntimeException {

    public UnavailableProviderException() {
        super();
    }

    public UnavailableProviderException(String message) {
        super(message);
    }

    public UnavailableProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnavailableProviderException(Throwable cause) {
        super(cause);
    }

    protected UnavailableProviderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
