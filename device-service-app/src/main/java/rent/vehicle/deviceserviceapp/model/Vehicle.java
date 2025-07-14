package rent.vehicle.deviceserviceapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;
import rent.vehicle.enums.Availability;
import rent.vehicle.enums.VehicleModel;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@ToString
@Builder
public class Vehicle implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String registrationNumber;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Device device;

    @Column()
    private VehicleModel vehicleModel;

    private Availability availability;

    @Column (columnDefinition = "geometry(Point,4326)")
    private Point point;
    // todo поискать у гугла варианты. Локейшен.
    // Использовать сторонний сервис чтобы конвертировать адрес в локацию. Сделать эндпоинт - это шаг два
    // Погуглить в каком виде я буду хранить локейшен - или сделать кастомный класс.  - это шаг один

    private int batteryStatus;

    @Column (columnDefinition = "TEXT")
    private String nodes;

}
