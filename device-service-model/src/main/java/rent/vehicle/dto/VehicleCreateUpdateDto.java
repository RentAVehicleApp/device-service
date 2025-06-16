package rent.vehicle.dto;

import lombok.*;
import rent.vehicle.enums.Availibility;
import rent.vehicle.enums.VehicleModel;

import java.awt.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCreateUpdateDto {
    private long id;
    //TODO check not null, format reg number, not exist - для этого создать свой кастомный валидатор Unic и переношу туда логику
    private String registrationNumber;
    private VehicleModel vehicleModel;
    private long deviceId;
    private Availibility availibility;
    private Point point;
    private String nodes;
}
