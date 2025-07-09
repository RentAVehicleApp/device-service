package rent.vehicle.deviceserviceapp.service;

import jakarta.persistence.criteria.Expression;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import rent.vehicle.deviceserviceapp.model.Device;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;
import rent.vehicle.deviceserviceapp.model.Vehicle;
import rent.vehicle.deviceserviceapp.specification.DeviceConfigSpecification;
import rent.vehicle.deviceserviceapp.specification.DeviceSpecification;
import rent.vehicle.deviceserviceapp.specification.VehicleSpecification;
import rent.vehicle.dto.*;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    final DeviceConfigService deviceConfigService;
    final DeviceService deviceService;
    final VehicleService vehicleService;
    final PointService pointService;
    final ModelMapper modelMapper;
    private final PointServiceImpl pointServiceImpl;

    @Override
    public Page<DeviceConfigDto> getListDevicesConfigByParam(
            ListDeviceConfigsRequest listDeviceConfigsRequest,
            Pageable pageable) {

        Specification<DeviceConfig> spec = DeviceConfigSpecification.buildSpecification(listDeviceConfigsRequest);

        Page<DeviceConfig> deviceConfigPage = deviceConfigService.findAllBySpec(spec, pageable);

        return deviceConfigPage.map(deviceConfig -> modelMapper.map(deviceConfig, DeviceConfigDto.class));
    }

    @Override
    public Page<DeviceDto> findDevicesByParams(
            ListDevicesRequest listDevicesRequest,
            Pageable pageable) {

        if (listDevicesRequest.getListDeviceConfigsRequest() != null) {
            Pageable devicePageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize()
            );
            Page<DeviceConfigDto> deviceConfigDtoPage = getListDevicesConfigByParam(listDevicesRequest.getListDeviceConfigsRequest(), devicePageable);
            listDevicesRequest.setDeviceConfigIds(
                    deviceConfigDtoPage.stream()
                            .map(DeviceConfigDto::getId)
                            .collect(Collectors.toSet())
            );
        }

        Specification<Device> spec = DeviceSpecification.buildSpecification(listDevicesRequest);

        Page<Device> devicePage = deviceService.findAllBySpec(spec, pageable);

        return devicePage.map(device -> modelMapper.map(device, DeviceDto.class));

    }

    @Override
    public Page<VehicleDto> findVehicleByParams(
            ListVehiclesRequest listVehiclesRequest,
            Pageable pageable) {

        if (listVehiclesRequest.getListDevicesRequest() != null) {
            Pageable devicePageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize()
            );

            Page<DeviceDto> deviceDtoPage = findDevicesByParams(listVehiclesRequest.getListDevicesRequest(), devicePageable);
            listVehiclesRequest.setDeviceIds(
                    deviceDtoPage.stream()
                            .map(DeviceDto::getId)
                            .peek(id -> System.out.println("Device ID: " + id))
                            .collect(Collectors.toSet())
            );
        }

        Specification<Vehicle> spec = VehicleSpecification.buildSpecification(listVehiclesRequest);

        Page<Vehicle> vehiclePage = vehicleService.findAllBySpec(spec, pageable);

        return vehiclePage.map(vehicle -> modelMapper.map(vehicle, VehicleDto.class));
    }

    @Override
    public Page<DeviceDto> findDevicesWithoutVehicle(Pageable pageable) {

        Page<Device> devicePage = deviceService.findDevicesWithoutVehicle(pageable);

        return devicePage.map(device -> modelMapper.map(device, DeviceDto.class));
    }

    @Override
    public Page<VehicleDto> findNearbyVehicles(PointFromLatLonDto pointFromLatLonDto, long radiusMeters, Pageable pageable) {

        Point targetPoint = pointServiceImpl.getPointFromCoordinate(pointFromLatLonDto);
        targetPoint.setSRID(4326);

        Specification<Vehicle> spec = (root, query, cb) -> {
            // ST_DistanceSphere(root.point, POINT(long, lat)) <= radiusMeters
            Expression<Double> distance = cb.function("ST_DistanceSphere", Double.class,
                    root.get("point"),
                    cb.function("ST_MakePoint", Object.class,
                            cb.literal(pointFromLatLonDto.getLongitude()),
                            cb.literal(pointFromLatLonDto.getLatitude())
                    )
            );

            return cb.lessThanOrEqualTo(distance, cb.literal((double) radiusMeters));
        };

        Page<Vehicle> vehiclePage = vehicleService.findAllBySpec(spec, pageable);

        return vehiclePage.map(vehicle -> modelMapper.map(vehicle, VehicleDto.class));
    }


}
