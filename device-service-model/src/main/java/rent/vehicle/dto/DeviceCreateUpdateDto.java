package rent.vehicle.dto;

import lombok.*;
import rent.vehicle.enums.DeviceModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCreateUpdateDto {
    private long id;
//    @UniqueConstraint() //todo Unique
    private String serialNumber;
    private long deviceConfigId;
    private DeviceModel deviceModel;
    private String nodes;
}

