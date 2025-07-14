package rent.vehicle.deviceserviceapp.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rent.vehicle.deviceserviceapp.config.CustomPage;
import rent.vehicle.deviceserviceapp.dao.DeviceRepository;
import rent.vehicle.deviceserviceapp.model.Device;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;
import rent.vehicle.dto.DeviceCreateUpdateDto;
import rent.vehicle.dto.DeviceDto;
import rent.vehicle.exception.DuplicateDeviceException;
import rent.vehicle.exception.EntityNotFoundException;

import java.util.List;

@Order(20)
@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    final DeviceRepository deviceRepository;
    final DeviceConfigService deviceConfigService;
    final ModelMapper modelMapper;

    @Transactional
    @Override
    public DeviceDto createDevice(DeviceCreateUpdateDto deviceCreateUpdateDto) {

        if (deviceRepository.existsBySerialNumber(deviceCreateUpdateDto.getSerialNumber())) {
            throw new DuplicateDeviceException("Device with this serial number already exists");
        }


        Device device = Device.builder()
                .deviceConfig(modelMapper.map(
                        deviceConfigService.findDeviceConfigById(deviceCreateUpdateDto.getDeviceConfigId())
                                , DeviceConfig.class))
                .deviceModel(deviceCreateUpdateDto.getDeviceModel())
                .nodes(deviceCreateUpdateDto.getNodes())
                .serialNumber(deviceCreateUpdateDto.getSerialNumber())
                .build();

        return modelMapper.map(deviceRepository.save(device), DeviceDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public DeviceDto findDeviceById(long id) {
        Device device = deviceRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(device, DeviceDto.class);
    }



    @Transactional
    @Override
    public DeviceDto updateDevice(long id, DeviceCreateUpdateDto deviceCreateUpdateDto) {
        Device device = deviceRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if (deviceCreateUpdateDto.getSerialNumber() != null) {
            device.setSerialNumber(deviceCreateUpdateDto.getSerialNumber());
        }
        
        if (deviceCreateUpdateDto.getDeviceModel() != null) {
            device.setDeviceModel(deviceCreateUpdateDto.getDeviceModel());
        }

        if (deviceCreateUpdateDto.getConnectionStatus() != null) {
            device.setConnectionStatus(deviceCreateUpdateDto.getConnectionStatus());
        }

        if (deviceCreateUpdateDto.getNodes() != null) {
            device.setNodes(deviceCreateUpdateDto.getNodes());
        }

        if (deviceCreateUpdateDto.getDeviceConfigId() != 0) {
            device.setDeviceConfig(modelMapper.map
                    (deviceConfigService.findDeviceConfigById(deviceCreateUpdateDto.getDeviceConfigId()
                    ), DeviceConfig.class)
            );

        }

        deviceRepository.save(device);
        return modelMapper.map(device, DeviceDto.class);
    }

    @Transactional
    @Override
    public void removeDevice(long id) {
        Device device = deviceRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        deviceRepository.detachVehicles(device);
        deviceRepository.delete(device);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomPage<DeviceDto> findAllDevices(Pageable pageable) {
        Page<Device> devicePage = deviceRepository.findAll(pageable);
        List<DeviceDto> dtoContent = devicePage.getContent().stream()
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .toList();

        return new CustomPage<>(dtoContent, devicePage.getNumber(), devicePage.getSize(), devicePage.getTotalElements());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Device> findAllBySpec(Specification<Device> spec, Pageable pageable) {
        return deviceRepository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomPage<DeviceDto> findDevicesWithoutVehicle(Pageable pageable) {

        Page<Device> devicePage = deviceRepository.findDevicesWithoutVehicle(pageable);

        List<DeviceDto> dtoContent = devicePage.getContent().stream()
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .toList();

        return new CustomPage<>(dtoContent, devicePage.getNumber(), devicePage.getSize(), devicePage.getTotalElements());
    }

}
