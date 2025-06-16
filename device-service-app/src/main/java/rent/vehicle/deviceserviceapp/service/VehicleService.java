package rent.vehicle.deviceserviceapp.service;

import org.springframework.data.domain.Page;
import rent.vehicle.dto.ListVehiclesRequest;
import rent.vehicle.dto.VehicleCreateUpdateDto;
import rent.vehicle.dto.VehicleDto;

import org.springframework.data.domain.Pageable;

public interface VehicleService {
    VehicleDto createVehicle(VehicleCreateUpdateDto vehicleCreateUpdateDto);

    VehicleDto findVehicleById(long id);

    Page<VehicleDto> findVehicleByParams(ListVehiclesRequest listVehiclesRequest, Pageable pageable);

    VehicleDto updateVehicle(long id, VehicleCreateUpdateDto vehicleCreateUpdateDto);

    void removeVehicle(long id);

    Page<VehicleDto> findAllVehicles(Pageable pageable);
}
