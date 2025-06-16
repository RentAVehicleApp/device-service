package rent.vehicle.dto;

import lombok.*;
import rent.vehicle.enums.Availibility;
import rent.vehicle.enums.VehicleModel;

import java.awt.*;
import java.util.Locale;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListVehiclesRequest {
    String registrationNumber;
    VehicleModel vehicleModel;
    ListDevicesRequest device;
    Availibility availibility;
    Point locale;
    String nodes;
}
