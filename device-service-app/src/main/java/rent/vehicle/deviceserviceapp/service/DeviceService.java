package rent.vehicle.deviceserviceapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import rent.vehicle.deviceserviceapp.model.Device;
import rent.vehicle.dto.DeviceCreateUpdateDto;
import rent.vehicle.dto.DeviceDto;

public interface DeviceService {
    DeviceDto createDevice(DeviceCreateUpdateDto deviceCreateUpdateDto);

    DeviceDto findDeviceById(long id);

    DeviceDto updateDevice(long id, DeviceCreateUpdateDto deviceCreateUpdateDto);

    void removeDevice(long id);

    Page<DeviceDto> findAllDevices(Pageable pageable);

    Page<Device> findAllBySpec(Specification<Device> spec, Pageable pageable);

    Page<Device> findDevicesWithoutVehicle(Pageable pageable);
}
