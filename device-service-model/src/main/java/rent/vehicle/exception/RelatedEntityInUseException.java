package rent.vehicle.exception;


public class RelatedEntityInUseException extends RuntimeException {
    public RelatedEntityInUseException(String s) {
        super(s);
    }
}
