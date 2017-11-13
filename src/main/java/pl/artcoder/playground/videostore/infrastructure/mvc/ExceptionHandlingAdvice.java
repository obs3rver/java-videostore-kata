package pl.artcoder.playground.videostore.infrastructure.mvc;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.artcoder.playground.videostore.film.infrastructure.boundary.rest.viewmodel.FilmNotFoundException;

@ControllerAdvice
class ExceptionHandlingAdvice {
    @ExceptionHandler(FilmNotFoundException.class)
    ResponseEntity<ErrorMessage> handleNotFoundFilms(FilmNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @Value
    class ErrorMessage {
        String message;
    }
}
