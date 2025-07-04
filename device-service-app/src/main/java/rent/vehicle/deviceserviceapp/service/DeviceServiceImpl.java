package rent.vehicle.deviceserviceapp.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rent.vehicle.deviceserviceapp.dao.DeviceRepository;
import rent.vehicle.deviceserviceapp.model.Device;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;
import rent.vehicle.dto.DeviceCreateUpdateDto;
import rent.vehicle.dto.DeviceDto;
import rent.vehicle.exception.DuplicateDeviceException;
import rent.vehicle.exception.EntityNotFoundException;
import rent.vehicle.exception.RelatedEntityInUseException;

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

        if (device.getDeviceConfig() != null) {
            throw new RelatedEntityInUseException(
                    "Невозможно удалить устройство. Сначала отвяжите его от транспортного средства."
            );
        }
        deviceRepository.delete(device);
    }

    @Override
    public Page<DeviceDto> findAllDevices(Pageable pageable) {
        return deviceRepository.findAll(pageable)
                .map(device -> modelMapper.map(device, DeviceDto.class));

    }

    @Override
    public Page<Device> findAllBySpec(Specification<Device> spec, Pageable pageable) {
        return deviceRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Device> findDevicesWithoutVehicle(Pageable pageable) {
        return deviceRepository.findDevicesWithoutVehicle(pageable);
    }

}
