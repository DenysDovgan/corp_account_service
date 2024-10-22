package faang.school.accountservice.exception.handler;

import faang.school.accountservice.dto.Error;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String SOMETHING_ERROR = "Something went wrong";

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleEntityNotFoundException(EntityNotFoundException exception) {
        String message = exception.getMessage();
        log.error(message, exception);

        return Error.builder()
                .code(HttpStatus.NOT_FOUND.toString())
                .message(message)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error instanceof FieldError ? ((FieldError) error).getField() : "Error",
                        error -> Optional.ofNullable(error.getDefaultMessage()).orElse(SOMETHING_ERROR)
                ));
        log.error("Validation errors: {}", errors);

        return Error.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .errors(errors)
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        String message = exception.getMostSpecificCause().getMessage();
        log.error(message, exception);

        return Error.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .message(message)
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, WebRequest request) {
        Map<String, String> errorDetails = Map.of(
                "error", exception.getLocalizedMessage(),
                "path", request.getDescription(false)
        );
        log.error("Validation error: {}", errorDetails);

        return Error.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .message("Malformed JSON request")
                .errors(errorDetails)
                .build();
    }
}
