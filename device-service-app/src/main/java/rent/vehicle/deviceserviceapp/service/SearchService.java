package rent.vehicle.deviceserviceapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rent.vehicle.dto.DeviceConfigDto;
import rent.vehicle.dto.DeviceDto;
import rent.vehicle.dto.VehicleDto;
import rent.vehicle.dto.list_request_dto.ListDeviceConfigsRequest;
import rent.vehicle.dto.list_request_dto.ListDevicesRequest;
import rent.vehicle.dto.list_request_dto.ListVehiclesRequest;

public interface SearchService {
    Page<DeviceConfigDto> getListDevicesConfigByParam(ListDeviceConfigsRequest listDeviceConfigsRequest, Pageable pageable);

    Page<DeviceDto> findDevicesByParams(ListDevicesRequest listDevicesRequest, Pageable pageable);

    Page<VehicleDto> findVehicleByParams(ListVehiclesRequest listVehiclesRequest, Pageable pageable);
}
