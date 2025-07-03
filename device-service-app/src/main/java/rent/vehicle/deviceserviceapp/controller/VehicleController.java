package rent.vehicle.deviceserviceapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import rent.vehicle.constants.ApiPaths;
import rent.vehicle.deviceserviceapp.service.SearchService;
import rent.vehicle.deviceserviceapp.service.VehicleService;
import rent.vehicle.dto.ListVehiclesRequest;
import rent.vehicle.dto.VehicleCreateUpdateDto;
import rent.vehicle.dto.VehicleDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.PATH_VEHICLE)
public class VehicleController {
    final VehicleService vehicleService;
    final SearchService searchService;

    @PostMapping
    public VehicleDto createVehicle (@RequestBody VehicleCreateUpdateDto vehicleCreateUpdateDto) {
        return vehicleService.createVehicle (vehicleCreateUpdateDto);
    }

    @GetMapping(ApiPaths.PATH_ID)
    public VehicleDto findVehicleById (@PathVariable long id) {
        return vehicleService.findVehicleById (id);
    }

    @GetMapping(ApiPaths.PATH_LIST)
    public Page<VehicleDto> findAllVehicles (
            @PageableDefault(
                    size = 2,
                    direction = Sort.Direction.DESC
            )
            Pageable pageable) {
        return vehicleService.findAllVehicles (pageable);
    }

    @GetMapping(ApiPaths.PATH_SEARCH)
    public Page<VehicleDto> findVehicleByParams (
            @ModelAttribute ListVehiclesRequest listVehiclesRequest,
            @PageableDefault(
                    size = 2,
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {
        return searchService.findVehicleByParams(listVehiclesRequest, pageable);
    }

    @PutMapping(ApiPaths.PATH_ID)
    public VehicleDto updateVehicle (@PathVariable long id, @RequestBody VehicleCreateUpdateDto vehicleCreateUpdateDto) {
        return vehicleService.updateVehicle (id, vehicleCreateUpdateDto);
    }

    @DeleteMapping(ApiPaths.PATH_ID)
    public void removeVehicle (@PathVariable long id) {
        vehicleService.removeVehicle (id);
    }

}
