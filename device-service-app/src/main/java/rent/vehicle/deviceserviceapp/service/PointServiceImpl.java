package rent.vehicle.deviceserviceapp.service;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import rent.vehicle.dto.PointFromLatLonDto;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    final ModelMapper modelMapper;

    @Override
    public Point getPointFromCoordinate(PointFromLatLonDto pointFromLatLonDto) {
        Point point = geometryFactory.createPoint(
                new Coordinate(
                        Double.parseDouble(pointFromLatLonDto.getLongitude()),
                        Double.parseDouble(pointFromLatLonDto.getLatitude())
                ));
        return point;
    }

}
