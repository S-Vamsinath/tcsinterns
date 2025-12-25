package com.tcs.tcsinterns.utility;

import com.tcs.tcsinterns.exception.TcsInternException;
import com.tcs.tcsinterns.utility.ErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @Autowired
    private Environment environment;

    // 🔹 Handle custom TcsInternException
    @ExceptionHandler(TcsInternException.class)
    public ResponseEntity<ErrorInfo> meetingSchedulerExceptionHandler(
            TcsInternException exception) {

        LOGGER.error(exception.getMessage(), exception);

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(
                environment.getProperty(exception.getMessage())
        );
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    // 🔹 Handle validation errors (@Valid, @NotNull, @Min, @Max etc)
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ErrorInfo> validatorExceptionHandler(
            Exception exception) {

        ErrorInfo errorInfo = new ErrorInfo();

        if (exception instanceof MethodArgumentNotValidException ex) {

            String message = ex.getBindingResult()
                    .getFieldError()
                    .getDefaultMessage();

            errorInfo.setErrorMessage(message);
            errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());

        } else if (exception instanceof ConstraintViolationException ex) {

            String message = ex.getConstraintViolations()
                    .iterator()
                    .next()
                    .getMessage();

            errorInfo.setErrorMessage(message);
            errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        }

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    // 🔹 Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> generalExceptionHandler(
            Exception exception) {

        LOGGER.error(exception.getMessage(), exception);

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(
                environment.getProperty("General.EXCEPTION_MESSAGE")
        );
        errorInfo.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errorInfo,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}