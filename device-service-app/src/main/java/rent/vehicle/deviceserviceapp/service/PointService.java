package rent.vehicle.deviceserviceapp.service;

import org.locationtech.jts.geom.Point;
import rent.vehicle.dto.PointFromLatLonDto;

public interface PointService {
    public Point getPointFromCoordinate(PointFromLatLonDto pointFromLatLonDto);

}
