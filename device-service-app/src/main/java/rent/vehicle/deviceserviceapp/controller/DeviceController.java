package rent.vehicle.deviceserviceapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import rent.vehicle.deviceserviceapp.service.DeviceService;
import rent.vehicle.deviceserviceapp.service.SearchService;
import rent.vehicle.dto.DeviceCreateUpdateDto;
import rent.vehicle.dto.DeviceDto;
import rent.vehicle.dto.list_request_dto.ListDevicesRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/devices")
public class DeviceController {
    final DeviceService deviceService;
    final SearchService searchService;

    @PostMapping
    public DeviceDto createDevice (@RequestBody DeviceCreateUpdateDto deviceCreateUpdateDto) {
        return deviceService.createDevice(deviceCreateUpdateDto);
    }

    @GetMapping("/{id}")
    public DeviceDto findDeviceById (@PathVariable long id) {
        return deviceService.findDeviceById (id);
    }

    @GetMapping("/search")
    public Page<DeviceDto> findDevicesByParams (
            @ModelAttribute ListDevicesRequest listDevicesRequest,
            @PageableDefault (size = 2)
            Pageable pageable) {
        return searchService.findDevicesByParams(listDevicesRequest, pageable);
    }

    @GetMapping("/list")
    public Page<DeviceDto> findAllDevices (
            @PageableDefault (size = 2)
            Pageable pageable) {
        return deviceService.findAllDevices(pageable);
    }

    @PutMapping("/{id}")
    public DeviceDto updateDevice (@PathVariable long id, @RequestBody DeviceCreateUpdateDto deviceCreateUpdateDto) {
        return deviceService.updateDevice (id, deviceCreateUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void removeDevice (@PathVariable long id) {
        deviceService.removeDevice (id);
    }



}
