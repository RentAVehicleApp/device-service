package rent.vehicle.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListDeviceConfigsRequest {
    String name;
    String namePart;
    //todo поиск по id Vehicle - здесь или не здесь
}
