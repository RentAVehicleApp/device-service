package rent.vehicle.deviceserviceapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rent.vehicle.dto.DeviceCreateUpdateDto;
import rent.vehicle.dto.DeviceDto;
import rent.vehicle.dto.ListDevicesRequest;

public interface DeviceService {
    DeviceDto createDevice(DeviceCreateUpdateDto deviceCreateUpdateDto);

    DeviceDto findDeviceById(long id);

    Page<DeviceDto> findDevicesByParams(ListDevicesRequest listDevicesRequest, Pageable pageable);

    DeviceDto updateDevice(long id, DeviceCreateUpdateDto deviceCreateUpdateDto);

    void removeDevice(long id);

    Page<DeviceDto> findAllDevices(Pageable pageable);
}
