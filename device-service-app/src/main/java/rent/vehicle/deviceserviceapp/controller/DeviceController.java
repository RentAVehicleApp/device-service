package rent.vehicle.deviceserviceapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import rent.vehicle.constants.ApiPaths;
import rent.vehicle.deviceserviceapp.service.DeviceService;
import rent.vehicle.deviceserviceapp.service.SearchService;
import rent.vehicle.dto.DeviceCreateUpdateDto;
import rent.vehicle.dto.DeviceDto;
import rent.vehicle.dto.ListDevicesRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.PATH_DEVICE)
public class DeviceController {
    final DeviceService deviceService;
    final SearchService searchService;

    @PostMapping
    public DeviceDto createDevice (@RequestBody DeviceCreateUpdateDto deviceCreateUpdateDto) {
        return deviceService.createDevice(deviceCreateUpdateDto);
    }

    @GetMapping(ApiPaths.PATH_ID)
    public DeviceDto findDeviceById (@PathVariable long id) {
        return deviceService.findDeviceById (id);
    }

    @GetMapping(ApiPaths.PATH_SEARCH)
    public Page<DeviceDto> findDevicesByParams (
            @ModelAttribute ListDevicesRequest listDevicesRequest,
            @PageableDefault (size = 2)
            Pageable pageable) {
        return searchService.findDevicesByParams(listDevicesRequest, pageable);
    }

    @GetMapping(ApiPaths.WITHOUT_VEHICLE)
    public Page<DeviceDto> findDevicesWithoutVehicle(Pageable pageable) {
        return searchService.findDevicesWithoutVehicle(pageable);
    }

    @GetMapping(ApiPaths.PATH_LIST)
    public Page<DeviceDto> findAllDevices (
            @PageableDefault (size = 2)
            Pageable pageable) {
        return deviceService.findAllDevices(pageable);
    }

    @PutMapping(ApiPaths.PATH_ID)
    public DeviceDto updateDevice (@PathVariable long id, @RequestBody DeviceCreateUpdateDto deviceCreateUpdateDto) {
        return deviceService.updateDevice (id, deviceCreateUpdateDto);
    }

    @DeleteMapping(ApiPaths.PATH_ID)
    public void removeDevice (@PathVariable long id) {
        deviceService.removeDevice (id);
    }

}
