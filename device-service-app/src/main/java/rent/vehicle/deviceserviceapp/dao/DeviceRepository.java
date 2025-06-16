package rent.vehicle.deviceserviceapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rent.vehicle.deviceserviceapp.model.Device;

public interface DeviceRepository extends JpaRepository<Device,Long> {
    boolean existsBySerialNumber(String serialNumber);
}
