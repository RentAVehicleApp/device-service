package rent.vehicle.deviceserviceapp.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rent.vehicle.deviceserviceapp.dao.DeviceConfigRepository;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;
import rent.vehicle.dto.DeviceConfigCreateUpdateDto;
import rent.vehicle.dto.DeviceConfigDto;
import rent.vehicle.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

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

        return modelMapper.map(deviceConfigRepository.save(deviceConfig),  DeviceConfigDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public DeviceConfigDto findDeviceConfigById(long id) {
        DeviceConfig deviceConfig = deviceConfigRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(deviceConfig, DeviceConfigDto.class);
    }

    @Override
    public List<DeviceConfigCreateUpdateDto> getListDevicesConfig() {
        return List.of();
    }

    @Transactional (readOnly = true)
    @Override
    public Page<DeviceConfigDto> findAllDeviceConfig(Pageable pageable) {
        return deviceConfigRepository.findAll(pageable)
                .map(deviceConfig -> modelMapper.map(deviceConfig, DeviceConfigDto.class));
    }

    @Transactional
    @Override
    public DeviceConfigCreateUpdateDto updateDeviceConfig(long id, DeviceConfigCreateUpdateDto deviceConfigCreateUpdateDto) {
        DeviceConfig deviceConfig = deviceConfigRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        deviceConfig.setName(deviceConfigCreateUpdateDto.getName());
        return modelMapper.map(deviceConfigRepository.save(deviceConfig), DeviceConfigCreateUpdateDto.class);
    }

    @Transactional
    @Override
    public void removeDeviceConfig(long id) {
        DeviceConfig deviceConfig = deviceConfigRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        deviceConfigRepository.delete(deviceConfig);
    }




}
