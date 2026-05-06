package com.example.pessoasapi.adapter.in.web;

import com.example.pessoasapi.domain.exception.EmailJaCadastradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EmailJaCadastradoException.class)
    ProblemDetail handleEmailJaCadastrado(EmailJaCadastradoException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
        problemDetail.setTitle("E-mail ja cadastrado");
        return problemDetail;
    }
}
