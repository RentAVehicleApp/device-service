package rent.vehicle.dto;

import lombok.*;
import rent.vehicle.enums.Availability;
import rent.vehicle.enums.VehicleModel;

import java.awt.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {
    private long id;
    //TODO check not null, format reg number, not exist - для этого создать свой кастомный валидатор Unic и переношу туда логику
    private String registrationNumber;
    private VehicleModel vehicleModel;
    private DeviceDto device;
    private Availability availability;
    private Point point;
    private Integer batteryStatus;
    private String nodes;
}
