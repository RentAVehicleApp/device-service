package rent.vehicle.deviceserviceapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of = "id")
@Getter
@Setter
@ToString
@Builder

public class DeviceConfig implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    String name;
}
