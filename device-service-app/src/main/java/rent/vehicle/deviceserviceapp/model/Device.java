package rent.vehicle.deviceserviceapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import rent.vehicle.enums.ConnectionStatus;
import rent.vehicle.enums.DeviceModel;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@ToString
@Builder

//@Table(uniqueConstraints = { @SerialNumberConstrain(columnNames = { "serialNumber" }) })
//todo: annotation Index - google it for field serialNumber
public class Device implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //todo во всех остальных энтити
    @Column(unique = true)

    private String serialNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private DeviceConfig deviceConfig;


    private DeviceModel deviceModel;

    private ConnectionStatus connectionStatus;

    @Column(columnDefinition = "TEXT") //todo check if it is right

    private String nodes;

}
