package rent.vehicle.deviceserviceapp.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rent.vehicle.exception.EntityNotFoundException;
import rent.vehicle.exception.RelatedEntityInUseException;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(RelatedEntityInUseException.class)
    public ResponseEntity<String> handler(RelatedEntityInUseException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handler(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    //2 вариант как это сделать:
    // Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
}
