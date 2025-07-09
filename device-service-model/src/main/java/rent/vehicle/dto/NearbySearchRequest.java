package rent.vehicle.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearbySearchRequest {
    private PointFromLatLonDto pointFromLatLonDto;
    private String radius;
}
