package rent.vehicle.deviceserviceapp.service;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import rent.vehicle.deviceserviceapp.model.Vehicle;
import rent.vehicle.dto.PointFromLatLonDto;
import rent.vehicle.dto.VehicleCreateUpdateDto;
import rent.vehicle.dto.VehicleDto;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    final ModelMapper modelMapper;

    @Override
    public Point getPointFromCoordinate(VehicleCreateUpdateDto vehicleCreateUpdateDto) {
        Point point = geometryFactory.createPoint(
                new Coordinate(
                        Double.parseDouble(vehicleCreateUpdateDto.getPointFromLatLonDto().getLongitude()),
                        Double.parseDouble(vehicleCreateUpdateDto.getPointFromLatLonDto().getLatitude())
                ));
        return point;
    }

    @Override
    public VehicleDto mapVehicleToVehicleDto(Vehicle vehicle) {
        PointFromLatLonDto pointFromLatLonDto = PointFromLatLonDto.builder()
                .longitude(String.valueOf(vehicle.getPoint().getX()))
                .latitude(String.valueOf(vehicle.getPoint().getY()))
                .build();
        VehicleDto vehicleDto = modelMapper.map(vehicle, VehicleDto.class);
        vehicleDto.setPointFromLatLonDto(pointFromLatLonDto);
        return vehicleDto;
    }
}
