package rent.vehicle.deviceserviceapp.config;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value={})
@Retention(value=RUNTIME)

public @interface SerialNumberConstrain {
    String name() default "";
    String[] columnNames();
}
