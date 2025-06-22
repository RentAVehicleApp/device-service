package rent.vehicle.deviceserviceapp.service;

import lombok.RequiredArgsConstructor;
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
import rent.vehicle.deviceserviceapp.specification.VehicleSpecification;
import rent.vehicle.dto.DeviceDto;
import rent.vehicle.dto.ListVehiclesRequest;
import rent.vehicle.dto.VehicleCreateUpdateDto;
import rent.vehicle.dto.VehicleDto;
import rent.vehicle.exception.DuplicateVehicleException;
import rent.vehicle.exception.EntityNotFoundException;

import java.util.stream.Collectors;

@Order(30)
@Service
@RequiredArgsConstructor
public class VehicleServisImpl implements VehicleService {
    final VehicleRepository vehicleRepository;
    final DeviceService deviceService;
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

        if (vehicleCreateUpdateDto.getPoint() != null) {
            vehicle.setPoint(vehicleCreateUpdateDto.getPoint());
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
    public Page<VehicleDto> findVehicleByParams(
            ListVehiclesRequest listVehiclesRequest,
            Pageable pageable) {

        if (listVehiclesRequest.getListDevicesRequest() != null) {
            Page<DeviceDto> deviceDtoPage = deviceService.findDevicesByParams(listVehiclesRequest.getListDevicesRequest(), pageable);
            listVehiclesRequest.setDeviceIds(
                    deviceDtoPage.stream()
                            .map(DeviceDto::getId)
                            .collect(Collectors.toSet())
            );
        }

        Specification<Vehicle> spec = VehicleSpecification.buildSpecification(listVehiclesRequest);

        Page<Vehicle> vehiclePage = vehicleRepository.findAll(spec, pageable);

        return vehiclePage.map(vehicle -> modelMapper.map(vehicle, VehicleDto.class));
    }


}
