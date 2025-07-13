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
import rent.vehicle.deviceserviceapp.config.CustomPage;
import rent.vehicle.deviceserviceapp.model.Device;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;
import rent.vehicle.deviceserviceapp.model.Vehicle;
import rent.vehicle.deviceserviceapp.specification.DeviceConfigSpecification;
import rent.vehicle.deviceserviceapp.specification.DeviceSpecification;
import rent.vehicle.deviceserviceapp.specification.VehicleSpecification;
import rent.vehicle.dto.*;

import java.util.List;
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
    public CustomPage<DeviceConfigDto> findDevicesConfigByParam(
            ListDeviceConfigsRequest listDeviceConfigsRequest,
            Pageable pageable) {

        Specification<DeviceConfig> spec = DeviceConfigSpecification.buildSpecification(listDeviceConfigsRequest);

        return getDeviceConfigDtos(pageable, spec);
    }

    @Override
    public CustomPage<DeviceDto> findDevicesByParams(ListDevicesRequest listDevicesRequest, Pageable pageable) {

        if (listDevicesRequest.getListDeviceConfigsRequest() != null) {
            Pageable devicePageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize()
            );
            CustomPage<DeviceConfigDto> deviceConfigDtoCustomPage = findDevicesConfigByParam(listDevicesRequest.getListDeviceConfigsRequest(), devicePageable);
            listDevicesRequest.setDeviceConfigIds(
                    deviceConfigDtoCustomPage.stream()
                            .map(DeviceConfigDto::getId)
                            .collect(Collectors.toSet())
            );
        }

        Specification<Device> spec = DeviceSpecification.buildSpecification(listDevicesRequest);

        return getDeviceDtos(pageable, spec);

    }



    @Override
    public CustomPage<VehicleDto> findVehicleByParams(
            ListVehiclesRequest listVehiclesRequest,
            Pageable pageable) {

        if (listVehiclesRequest.getListDevicesRequest() != null) {
            Pageable devicePageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize()
            );

            CustomPage<DeviceDto> deviceDtoCustomPage = findDevicesByParams(listVehiclesRequest.getListDevicesRequest(), devicePageable);
            listVehiclesRequest.setDeviceIds(
                    deviceDtoCustomPage.stream()
                            .map(DeviceDto::getId)
                            .peek(id -> System.out.println("Device ID: " + id))
                            .collect(Collectors.toSet())
            );
        }

        Specification<Vehicle> spec = VehicleSpecification.buildSpecification(listVehiclesRequest);

        return getVehicleDtos(pageable, spec);

    }

    @Override
    public CustomPage<VehicleDto> findNearbyVehicles(PointFromLatLonDto pointFromLatLonDto, long radiusMeters, Pageable pageable) {

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

        return getVehicleDtos(pageable, spec);
    }

    private CustomPage<VehicleDto> getVehicleDtos(Pageable pageable, Specification<Vehicle> spec) {
        Page<Vehicle> devicePage = vehicleService.findAllBySpec(spec, pageable);

        List<VehicleDto> dtoContent = devicePage.getContent().stream()
                .map(v -> modelMapper.map(v, VehicleDto.class))
                .collect(Collectors.toList());

        return new CustomPage<VehicleDto>(dtoContent, devicePage.getNumber(), devicePage.getSize(), devicePage.getTotalElements());
    }

    private CustomPage<DeviceDto> getDeviceDtos(Pageable pageable, Specification<Device> spec) {
        Page<Device> devicePage = deviceService.findAllBySpec(spec, pageable);

        List<DeviceDto> dtoContent = devicePage.getContent().stream()
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .collect(Collectors.toList());

        return new CustomPage<DeviceDto>(dtoContent, devicePage.getNumber(), devicePage.getSize(), devicePage.getTotalElements());
    }

    private CustomPage<DeviceConfigDto> getDeviceConfigDtos(Pageable pageable, Specification<DeviceConfig> spec) {
        Page<DeviceConfig> deviceConfigPage = deviceConfigService.findAllBySpec(spec, pageable);

        List<DeviceConfigDto> deviceConfigDtoList = deviceConfigPage.getContent().stream()
                .map(deviceConfig -> modelMapper.map(deviceConfig, DeviceConfigDto.class))
                .collect(Collectors.toList());

        return new CustomPage<DeviceConfigDto>(deviceConfigDtoList, deviceConfigPage.getNumber(), deviceConfigPage.getSize(), deviceConfigPage.getTotalElements());
    }


}
