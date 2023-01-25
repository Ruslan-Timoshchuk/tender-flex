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
        return new ApiError(HttpStatus.UNAUTHORIZED, exception, exception.getCause());
    }

    @ExceptionHandler(AuthenticationControllerException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleExceptionAuthenticationControllerException(AuthenticationControllerException exception) {
        return new ApiError(HttpStatus.UNAUTHORIZED, exception, exception.getCause());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleExceptionServiceException(ServiceException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception, exception.getCause());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleExceptionUserNotFoundException(UserNotFoundException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception, exception.getCause());
    }
}