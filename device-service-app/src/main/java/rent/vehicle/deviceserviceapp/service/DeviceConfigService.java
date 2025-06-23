package rent.vehicle.deviceserviceapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;
import rent.vehicle.dto.DeviceConfigCreateUpdateDto;
import rent.vehicle.dto.DeviceConfigDto;

import java.util.List;

public interface DeviceConfigService {
    DeviceConfigDto createDeviceConfig(DeviceConfigCreateUpdateDto deviceConfigCreateUpdateDto);

    DeviceConfigDto findDeviceConfigById(long id);

    DeviceConfigCreateUpdateDto updateDeviceConfig(long id, DeviceConfigCreateUpdateDto deviceConfigCreateUpdateDto);

    void removeDeviceConfig(long id);

    List<DeviceConfigCreateUpdateDto> getListDevicesConfig();

    Page<DeviceConfigDto> findAllDeviceConfig(Pageable pageable);

    Page<DeviceConfig> findAllBySpec(Specification<DeviceConfig> spec, Pageable pageable);
}
