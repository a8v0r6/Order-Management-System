package com.manage.order.controllerAdvice;

import com.manage.order.exception.OrderNotFoundException;
import com.manage.order.exception.UserNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ProblemDetail handleOrderNotFoundException(OrderNotFoundException oe) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), oe.getMessage());
        problemDetail.setProperty("description", "Order not found");
        return problemDetail;
    }

    @ExceptionHandler(OptimisticLockException.class)
    public ProblemDetail handleLockConflictException(OptimisticLockException oe) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(409), oe.getMessage());
        problemDetail.setProperty("description", "The order was modified concurrently");
        return problemDetail;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(UserNotFoundException ue) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ue.getMessage());
        problemDetail.setProperty("description", "User does not exist in the system");
        return problemDetail;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), "Bad Request");
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(err ->
                errors.add(err.getDefaultMessage())
        );
        problemDetail.setProperty("description", errors);
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(500);
        problemDetail.setProperty("description", "Unknown Internal Server Error");
        return problemDetail;
    }
}
