package rent.vehicle.dto.list_request_dto;

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
    private DeviceModel deviceModel;
    private ConnectionStatus connectionStatus;
    private String nodesPart;

    private ListDeviceConfigsRequest listDeviceConfigsRequest;
    private Set<Long> deviceConfigIds;
}
