package rent.vehicle.dto;

import lombok.*;
import rent.vehicle.enums.Availability;
import rent.vehicle.enums.VehicleModel;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListVehiclesRequest {
    private String registrationNumber;
    private String registrationNumberPart;
    private double latitude;
    private double longitude;
    private double radius;
    private VehicleModel vehicleModel;
    private Availability availability;
    private Integer batteryStatusMin;
    private Integer batteryStatusMax;
    private String nodes;
    private ListDevicesRequest listDevicesRequest;
    private Set<Long> deviceIds;

}
