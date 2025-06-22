package rent.vehicle.dto;

import lombok.*;
import rent.vehicle.enums.Availability;
import rent.vehicle.enums.VehicleModel;

import java.awt.*;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListVehiclesRequest {
    private String registrationNumber;
    private String registrationNumberPart;
    private VehicleModel vehicleModel;
    private Availability availability;
    private Point point;
    private Integer batteryStatusMin;
    private Integer batteryStatusMax;
    private String nodes;
    private ListDevicesRequest listDevicesRequest;
    private Set<Long> deviceIds;

}
