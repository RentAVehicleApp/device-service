package rent.vehicle.deviceserviceapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rent.vehicle.dto.*;

public interface SearchService {
    Page<DeviceConfigDto> getListDevicesConfigByParam(ListDeviceConfigsRequest listDeviceConfigsRequest, Pageable pageable);

    Page<DeviceDto> findDevicesByParams(ListDevicesRequest listDevicesRequest, Pageable pageable);

    Page<VehicleDto> findVehicleByParams(ListVehiclesRequest listVehiclesRequest, Pageable pageable);

    Page<DeviceDto> findDevicesWithoutVehicle(Pageable pageable);
}
