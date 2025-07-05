package rent.vehicle.deviceserviceapp.service;

import org.locationtech.jts.geom.Point;
import rent.vehicle.deviceserviceapp.model.Vehicle;
import rent.vehicle.dto.VehicleCreateUpdateDto;
import rent.vehicle.dto.VehicleDto;

public interface PointService {
    public Point getPointFromCoordinate(VehicleCreateUpdateDto vehicleCreateUpdateDto);

    VehicleDto mapVehicleToVehicleDto(Vehicle vehicle);
}
