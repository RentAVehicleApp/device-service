package rent.vehicle.deviceserviceapp.service;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import rent.vehicle.deviceserviceapp.dao.VehicleRepository;
import rent.vehicle.deviceserviceapp.model.Device;
import rent.vehicle.deviceserviceapp.model.Vehicle;
import rent.vehicle.dto.VehicleCreateUpdateDto;
import rent.vehicle.dto.VehicleDto;
import rent.vehicle.exception.DuplicateVehicleException;
import rent.vehicle.exception.EntityNotFoundException;


@Order(30)
@Service
@RequiredArgsConstructor
public class VehicleServisImpl implements VehicleService {
    final VehicleRepository vehicleRepository;
    final DeviceService deviceService;
    PointService pointService;
    final ModelMapper modelMapper;


    @Transactional
    @Override
    public VehicleDto createVehicle(VehicleCreateUpdateDto vehicleCreateUpdateDto) {

        //todo Validation - hibernate validation framework
        if (vehicleRepository.existsByRegistrationNumber(vehicleCreateUpdateDto.getRegistrationNumber())) {
            throw new DuplicateVehicleException("Vehicle with this serial number already exists");
        }

        Vehicle vehicle = Vehicle.builder()
                .vehicleModel(vehicleCreateUpdateDto.getVehicleModel())
                .nodes(vehicleCreateUpdateDto.getNodes())
                .registrationNumber(vehicleCreateUpdateDto.getRegistrationNumber())
                .device(modelMapper.map(
                        deviceService.findDeviceById(vehicleCreateUpdateDto.getDeviceId()), Device.class
                ))
                .point(pointService.getPointFromCoordinate(vehicleCreateUpdateDto.getPointFromLatLonDto()))
                .build();

        return modelMapper.map(vehicleRepository.save(vehicle), VehicleDto.class);
    }

    @Override
    public VehicleDto findVehicleById(long id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(vehicle, VehicleDto.class);
    }

    @Override
    public VehicleDto updateVehicle(long id, VehicleCreateUpdateDto vehicleCreateUpdateDto) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        if (vehicleCreateUpdateDto.getRegistrationNumber() != null) {
            vehicle.setRegistrationNumber(vehicleCreateUpdateDto.getRegistrationNumber());
        }

        if (vehicleCreateUpdateDto.getVehicleModel() != null) {
            vehicle.setVehicleModel(vehicleCreateUpdateDto.getVehicleModel());
        }

        if (vehicleCreateUpdateDto.getAvailability() != null) {
            vehicle.setAvailability(vehicleCreateUpdateDto.getAvailability());
        }

        if (vehicleCreateUpdateDto.getPointFromLatLonDto() != null) {
            vehicle.setPoint(pointService.getPointFromCoordinate(vehicleCreateUpdateDto.getPointFromLatLonDto()));
        }

        if (vehicleCreateUpdateDto.getBatteryStatus() != null) {
            vehicle.setBatteryStatus(vehicleCreateUpdateDto.getBatteryStatus());
        }

        if (vehicleCreateUpdateDto.getNodes() != null) {
            vehicle.setNodes(vehicleCreateUpdateDto.getNodes());
        }

        if (vehicleCreateUpdateDto.getDeviceId() != 0) {
            vehicle.setDevice(modelMapper.map(
                    deviceService.findDeviceById(vehicleCreateUpdateDto.getDeviceId()), Device.class
            ));
        }

        vehicleRepository.save(vehicle);
        return modelMapper.map(vehicle, VehicleDto.class);
    }

    @Override
    public void removeVehicle(long id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        vehicleRepository.delete(vehicle);
    }

    @Override
    public Page<VehicleDto> findAllVehicles(Pageable pageable) {
        return vehicleRepository.findAll(pageable)
                .map(vehicle -> modelMapper.map(vehicle, VehicleDto.class));
    }

    @Override
    public Page<Vehicle> findAllBySpec(Specification<Vehicle> spec, Pageable pageable) {
        return vehicleRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Vehicle> findNearbyVehicles(Point point, long radiusMeters, Pageable pageable) {
        return vehicleRepository.findNearbyVehicles(point, radiusMeters, pageable);
    }


}
