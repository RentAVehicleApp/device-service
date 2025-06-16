package rent.vehicle.deviceserviceapp.init;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rent.vehicle.deviceserviceapp.dao.DeviceConfigRepository;
import rent.vehicle.deviceserviceapp.dao.DeviceRepository;
import rent.vehicle.deviceserviceapp.dao.VehicleRepository;
import rent.vehicle.deviceserviceapp.model.Device;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;
import rent.vehicle.deviceserviceapp.model.Vehicle;
import rent.vehicle.deviceserviceapp.service.DeviceConfigService;
import rent.vehicle.deviceserviceapp.service.DeviceService;
import rent.vehicle.deviceserviceapp.service.VehicleService;
import rent.vehicle.enums.Availibility;
import rent.vehicle.enums.ConnectionStatus;
import rent.vehicle.enums.DeviceModel;
import rent.vehicle.enums.VehicleModel;

@Component
@RequiredArgsConstructor
public class CreateDateInDBInitializer implements CommandLineRunner {

    private final DeviceConfigService deviceConfigService;
    private final DeviceService deviceService;
    private final VehicleService vehicleService;

    private final DeviceConfigRepository deviceConfigRepository;
    private final DeviceRepository deviceRepository;
    private final VehicleRepository vehicleRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public void run(String... args) throws Exception {
        DeviceConfig deviceConfig = DeviceConfig.builder()
                .name("Created config 1")
                .build();
        deviceConfig = deviceConfigRepository.save(deviceConfig);

        System.out.println("log1: " + deviceConfig.getId());

//        DeviceConfig

        Device device1 = Device.builder()
                .serialNumber("A1-1522")
                .deviceConfig(deviceConfig)
                .deviceModel(DeviceModel.MODEL3)
                .nodes("blablabla")
                .connectionStatus(ConnectionStatus.CONNECTED)
                .build();
        deviceRepository.save(device1);
        System.out.println("log2: " + device1.toString());

        Device device2 = Device.builder()
                .serialNumber("A2-1827")
                .deviceConfig(deviceConfig)
                .deviceModel(DeviceModel.MODEL2)
                .nodes("blablabla bla bla")
                .connectionStatus(ConnectionStatus.CONNECTED)
                .build();

        deviceRepository.save(device2);
        System.out.println("log3: " + device2.toString());

        Device device3 = Device.builder()
                .serialNumber("A3-0711")
                .deviceConfig(deviceConfig)
                .deviceModel(DeviceModel.MODEL3)
                .nodes("Ленин - гриб")
                .connectionStatus(ConnectionStatus.CONNECTED)
                .build();

        deviceRepository.save(device3);
        System.out.println("log4: " + device3.toString());

//        Vehicle

        Vehicle vehicle1 = Vehicle.builder()
                .availibility(Availibility.IN_USE)
                .batteryStatus(75)
                .vehicleModel(VehicleModel.MODEL1)
                .device(device1)
                .registrationNumber("Vh17-22")
                .nodes("red and nice")
                .build();
        vehicleRepository.save(vehicle1);
        System.out.println("log5: " + vehicle1.toString());

        Vehicle vehicle2 = Vehicle.builder()
                .availibility(Availibility.IN_USE)
                .batteryStatus(11)
                .vehicleModel(VehicleModel.MODEL1)
                .device(device2)
                .registrationNumber("Ar12-78")
                .nodes("green")
                .build();
        vehicleRepository.save(vehicle2);
        System.out.println("log6: " + vehicle2.toString());

        Vehicle vehicle3 = Vehicle.builder()
                .availibility(Availibility.AVAILABLE)
                .batteryStatus(100)
                .vehicleModel(VehicleModel.MODEL1)
                .device(device3)
                .registrationNumber("Cv09-54")
                .nodes("black, has a dented side")
                .build();
        vehicleRepository.save(vehicle3);
        System.out.println("log7: " + vehicle3.toString());

    }
}
