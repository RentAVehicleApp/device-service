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
import rent.vehicle.deviceserviceapp.config.CustomPage;
import rent.vehicle.deviceserviceapp.dao.DeviceConfigRepository;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;
import rent.vehicle.dto.DeviceConfigCreateUpdateDto;
import rent.vehicle.dto.DeviceConfigDto;
import rent.vehicle.exception.EntityNotFoundException;

import java.util.List;

@Order(10)
@Service
@RequiredArgsConstructor
public class DeviceConfigServiceImpl implements DeviceConfigService {
    final DeviceConfigRepository deviceConfigRepository;
    final ModelMapper modelMapper;


    @Transactional
    @Override
    public DeviceConfigDto createDeviceConfig(DeviceConfigCreateUpdateDto deviceConfigCreateUpdateDto) {
        DeviceConfig deviceConfig = DeviceConfig.builder()
                .name(deviceConfigCreateUpdateDto.getName())
                .build();

        return modelMapper.map(deviceConfigRepository.save(deviceConfig), DeviceConfigDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public DeviceConfigDto findDeviceConfigById(long id) {
        DeviceConfig deviceConfig = deviceConfigRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(deviceConfig, DeviceConfigDto.class);
    }

    @Transactional (readOnly = true)
    @Override
    public CustomPage<DeviceConfigDto> findAllDeviceConfig(Pageable pageable) {
        Page<DeviceConfig> deviceConfigPage = deviceConfigRepository.findAll(pageable);

        List<DeviceConfigDto> dtoContent = deviceConfigPage.getContent().stream()
                .map(deviceConfig -> modelMapper.map(deviceConfig, DeviceConfigDto.class))
                .toList();

        return new CustomPage<>(dtoContent, deviceConfigPage.getNumber(), deviceConfigPage.getSize(), deviceConfigPage.getTotalElements());
    }

    @Override
    public Page<DeviceConfig> findAllBySpec(Specification<DeviceConfig> spec, Pageable pageable) {
        return deviceConfigRepository.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public DeviceConfigDto updateDeviceConfig(long id, DeviceConfigCreateUpdateDto deviceConfigCreateUpdateDto) {
        DeviceConfig deviceConfig = deviceConfigRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        deviceConfig.setName(deviceConfigCreateUpdateDto.getName());
        return modelMapper.map(deviceConfigRepository.save(deviceConfig), DeviceConfigDto.class);
    }

    @Transactional
    @Override
    public void removeDeviceConfig(long id) {
        DeviceConfig deviceConfig = deviceConfigRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "DeviceConfig with id " + id + " not found"));
        deviceConfigRepository.detachDevices(deviceConfig);
        deviceConfigRepository.delete(deviceConfig);
    }




}
