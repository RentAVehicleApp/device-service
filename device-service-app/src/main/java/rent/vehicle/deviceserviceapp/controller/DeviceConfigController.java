package rent.vehicle.deviceserviceapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import rent.vehicle.constants.ApiPaths;
import rent.vehicle.deviceserviceapp.service.DeviceConfigService;
import rent.vehicle.deviceserviceapp.service.SearchService;
import rent.vehicle.dto.DeviceConfigCreateUpdateDto;
import rent.vehicle.dto.DeviceConfigDto;
import rent.vehicle.dto.ListDeviceConfigsRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.PATH_DEVICE_CONFIG)
public class DeviceConfigController {

    final DeviceConfigService deviceConfigService;
    final SearchService searchService;

    @PostMapping
    public DeviceConfigDto createDeviceConfig (@RequestBody DeviceConfigCreateUpdateDto deviceConfigCreateUpdateDto) {
        return deviceConfigService.createDeviceConfig(deviceConfigCreateUpdateDto);
    }

    @PutMapping(ApiPaths.PATH_ID)
    public DeviceConfigCreateUpdateDto updateDeviceConfig (@PathVariable long id, @RequestBody DeviceConfigCreateUpdateDto deviceConfigCreateUpdateDto) {
        return deviceConfigService.updateDeviceConfig (id, deviceConfigCreateUpdateDto);
    }

    @GetMapping(ApiPaths.PATH_ID)
    public DeviceConfigDto findDeviceConfigById (@PathVariable long id) {
        return deviceConfigService.findDeviceConfigById (id);
    }

    @GetMapping(ApiPaths.PATH_LIST)
    public Page<DeviceConfigDto> findAllDeviceConfig(
            @PageableDefault(size = 2)
            Pageable pageable) {
        return deviceConfigService.findAllDeviceConfig(pageable);
    }

    @GetMapping(ApiPaths.PATH_SEARCH)
    public Page<DeviceConfigDto> findListDevicesConfigByParam(
            @ModelAttribute ListDeviceConfigsRequest listDeviceConfigsRequest,
            @PageableDefault(size = 2)
            Pageable pageable) {
        return searchService.getListDevicesConfigByParam(listDeviceConfigsRequest, pageable);
    }

    @DeleteMapping(ApiPaths.PATH_ID)
    public void removeDeviceConfig (@PathVariable long id) {
        deviceConfigService.removeDeviceConfig (id);
    }




}
