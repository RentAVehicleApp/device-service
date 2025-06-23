package rent.vehicle.dto.list_request_dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListDeviceConfigsRequest {
    String name;
    String namePart;
}
