package rent.vehicle.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Entity Not Found");
    }
}
