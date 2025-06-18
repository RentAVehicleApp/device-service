package rent.vehicle.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListDeviceConfigsRequest {
    String nameOfConfig;
    private List<String> namesList;
}
