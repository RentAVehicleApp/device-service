package rent.vehicle.deviceserviceapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import rent.vehicle.deviceserviceapp.config.CustomPage;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;
import rent.vehicle.dto.DeviceConfigCreateUpdateDto;
import rent.vehicle.dto.DeviceConfigDto;

public interface DeviceConfigService {
    DeviceConfigDto createDeviceConfig(DeviceConfigCreateUpdateDto deviceConfigCreateUpdateDto);

    DeviceConfigDto findDeviceConfigById(long id);

    DeviceConfigDto updateDeviceConfig(long id, DeviceConfigCreateUpdateDto deviceConfigCreateUpdateDto);

    void removeDeviceConfig(long id);

    CustomPage<DeviceConfigDto> findAllDeviceConfig(Pageable pageable);

    Page<DeviceConfig> findAllBySpec(Specification<DeviceConfig> spec, Pageable pageable);
}
