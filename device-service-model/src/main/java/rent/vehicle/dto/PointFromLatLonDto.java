package rent.vehicle.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointFromLatLonDto {
    private String latitude;
    private String longitude;
}
