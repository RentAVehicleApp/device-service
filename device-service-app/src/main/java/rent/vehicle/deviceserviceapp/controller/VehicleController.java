package rent.vehicle.deviceserviceapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import rent.vehicle.deviceserviceapp.service.VehicleService;
import rent.vehicle.dto.ListVehiclesRequest;
import rent.vehicle.dto.VehicleCreateUpdateDto;
import rent.vehicle.dto.VehicleDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vehicles")
public class VehicleController {
    final VehicleService vehicleService;

    @PostMapping
    public VehicleDto createVehicle (@RequestBody VehicleCreateUpdateDto vehicleCreateUpdateDto) {
        return vehicleService.createVehicle (vehicleCreateUpdateDto);
    }

    @GetMapping("/{id}")
    public VehicleDto findVehicleById (@PathVariable long id) {
        return vehicleService.findVehicleById (id);
    }

    @GetMapping("/list")
    public Page<VehicleDto> findAllVehicles (
            @PageableDefault(
                    size = 2,
                    direction = Sort.Direction.DESC
            )
            Pageable pageable) {
        return vehicleService.findAllVehicles (pageable);
    }

    @GetMapping("/search")
    public Page<VehicleDto> findVehicleByParams (
            @ModelAttribute ListVehiclesRequest listVehiclesRequest,
            @PageableDefault(
                    size = 2,
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {
        return vehicleService.findVehicleByParams (listVehiclesRequest, pageable);
    }

    @PutMapping("/{id}")
    public VehicleDto updateVehicle (@PathVariable long id, @RequestBody VehicleCreateUpdateDto vehicleCreateUpdateDto) {
        return vehicleService.updateVehicle (id, vehicleCreateUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void removeVehicle (@PathVariable long id) {
        vehicleService.removeVehicle (id);
    }

}
