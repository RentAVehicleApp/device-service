package rent.vehicle.deviceserviceapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import rent.vehicle.deviceserviceapp.service.DeviceConfigService;
import rent.vehicle.dto.DeviceConfigCreateUpdateDto;
import rent.vehicle.dto.DeviceConfigDto;
import rent.vehicle.dto.ListDeviceConfigsRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/deviceconfig")
public class DeviceConfigController {
    final DeviceConfigService deviceConfigService;

    @PostMapping
    public DeviceConfigDto createDeviceConfig (@RequestBody DeviceConfigCreateUpdateDto deviceConfigCreateUpdateDto) {
        return deviceConfigService.createDeviceConfig(deviceConfigCreateUpdateDto);
    }

    @PutMapping("/{id}")
    public DeviceConfigCreateUpdateDto updateDeviceConfig (@PathVariable long id, @RequestBody DeviceConfigCreateUpdateDto deviceConfigCreateUpdateDto) {
        return deviceConfigService.updateDeviceConfig (id, deviceConfigCreateUpdateDto);
    }

    @GetMapping("/{id}")
    public DeviceConfigDto findDeviceConfigById (@PathVariable long id) {
        return deviceConfigService.findDeviceConfigById (id);
    }

    @GetMapping("/list")
    public Page<DeviceConfigDto> findAllDeviceConfig(
            @PageableDefault(size = 2)
            Pageable pageable) {
        return deviceConfigService.findAllDeviceConfig(pageable);
    }

    @GetMapping("/search")
    public Page<DeviceConfigDto> getListDevicesConfigByParam(
            @ModelAttribute ListDeviceConfigsRequest listDeviceConfigsRequest,
            @PageableDefault(size = 2)
            Pageable pageable) {
        return deviceConfigService.getListDevicesConfigByParam(listDeviceConfigsRequest, pageable);
    }

    @DeleteMapping("/{id}")
    public void removeDeviceConfig (@PathVariable long id) {
        deviceConfigService.removeDeviceConfig (id);
    }




}
