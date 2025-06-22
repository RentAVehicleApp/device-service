package rent.vehicle.dto;

import lombok.*;
import rent.vehicle.enums.ConnectionStatus;
import rent.vehicle.enums.DeviceModel;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListDevicesRequest {

    private String serialNumber;
    private String serialNumberPart;
    private Set<Long> deviceConfigsIdList;
    private DeviceModel deviceModel;
    private ConnectionStatus connectionStatus;
    private String nodesPart;
}
