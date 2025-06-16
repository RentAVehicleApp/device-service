package rent.vehicle.dto;

import lombok.*;
import rent.vehicle.enums.DeviceModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {
    private long id;
//    @UniqueConstraint() //todo Unique
    private String serialNumber;
    private DeviceConfigCreateUpdateDto deviceConfig;
    private DeviceModel deviceModel;
    private String nodes;
}

