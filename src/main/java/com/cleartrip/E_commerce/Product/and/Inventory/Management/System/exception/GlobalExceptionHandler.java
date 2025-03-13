
package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
        import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleCustomException(CustomException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }
}
