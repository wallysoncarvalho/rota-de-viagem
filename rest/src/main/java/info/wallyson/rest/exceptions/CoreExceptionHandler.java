package info.wallyson.rest.exceptions;

import info.wallyson.core.exceptions.CoreExceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CoreExceptionHandler {

  @ExceptionHandler(CoreExceptions.class)
  public ResponseEntity<?> handleCoreExceptions(CoreExceptions exception) {

    var error = new ErrorResponse(exception.getMessage());

    return ResponseEntity.status(400).body(error);
  }
}
