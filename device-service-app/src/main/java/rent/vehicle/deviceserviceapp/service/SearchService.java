package rent.vehicle.deviceserviceapp.service;

import org.springframework.data.domain.Pageable;
import rent.vehicle.deviceserviceapp.config.CustomPage;
import rent.vehicle.dto.*;

public interface SearchService {
    CustomPage<DeviceConfigDto> findDevicesConfigByParam(ListDeviceConfigsRequest listDeviceConfigsRequest, Pageable pageable);

    CustomPage<DeviceDto> findDevicesByParams(ListDevicesRequest listDevicesRequest, Pageable pageable);

    CustomPage<VehicleDto> findVehicleByParams(ListVehiclesRequest listVehiclesRequest, Pageable pageable);

    CustomPage<VehicleDto> findNearbyVehicles(PointFromLatLonDto pointFromLatLonDto, long radiusMeters, Pageable pageable);
}
