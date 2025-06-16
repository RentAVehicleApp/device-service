package rent.vehicle.dto;

import lombok.*;
import rent.vehicle.enums.ConnectionStatus;
import rent.vehicle.enums.DeviceModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListDevicesRequest {

    private String serialNumber;
    private DeviceConfigCreateUpdateDto deviceConfig;
    private DeviceModel deviceModel;
    ConnectionStatus connectionStatus;
    private String nodes;
}
