package pl.com.tenderflex.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(AuthenticationProviderException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
     public ApiError handleExceptionAuthenticationProviderException(AuthenticationProviderException exception) {
        return new ApiError(HttpStatus.UNAUTHORIZED, exception);
    }
    
    @ExceptionHandler(AuthenticationControllerException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
     public ApiError handleExceptionAuthenticationControllerException(AuthenticationControllerException exception) {
        return new ApiError(HttpStatus.UNAUTHORIZED, exception);
    }
    
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ApiError handleExceptionServiceException(ServiceException exception) {
        return new ApiError(HttpStatus.BAD_GATEWAY, exception);
    }
}