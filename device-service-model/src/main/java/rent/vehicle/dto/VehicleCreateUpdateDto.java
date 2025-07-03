package rent.vehicle.dto;

import lombok.*;
import rent.vehicle.enums.Availability;
import rent.vehicle.enums.VehicleModel;

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
    private Availability availability;
    private PointFromLatLonDto pointFromLatLonDto;
    private Integer batteryStatus;
    private String nodes;
}
