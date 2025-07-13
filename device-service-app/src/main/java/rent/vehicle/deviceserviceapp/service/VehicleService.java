package rent.vehicle.deviceserviceapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import rent.vehicle.deviceserviceapp.config.CustomPage;
import rent.vehicle.deviceserviceapp.model.Vehicle;
import rent.vehicle.dto.VehicleCreateUpdateDto;
import rent.vehicle.dto.VehicleDto;

public interface VehicleService {
    VehicleDto createVehicle(VehicleCreateUpdateDto vehicleCreateUpdateDto);

    VehicleDto findVehicleById(long id);

    VehicleDto updateVehicle(long id, VehicleCreateUpdateDto vehicleCreateUpdateDto);

    void removeVehicle(long id);

    CustomPage<VehicleDto> findAllVehicles(Pageable pageable);

    Page<Vehicle> findAllBySpec(Specification<Vehicle> spec, Pageable pageable);
}
