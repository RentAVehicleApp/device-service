package rent.vehicle.deviceserviceapp.model;

import jakarta.persistence.*;
import lombok.*;
import rent.vehicle.enums.Availibility;
import rent.vehicle.enums.VehicleModel;

import java.awt.*;
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
    private Device device;

    @Column()
    private VehicleModel vehicleModel;

    private Availibility availibility;

    @Column (columnDefinition = "geometry(Point,4326)")
    private Point point;

    int batteryStatus;

    @Column (columnDefinition = "TEXT")
    private String nodes;

}
