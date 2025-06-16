package rent.vehicle.deviceserviceapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;

public interface DeviceConfigRepository extends JpaRepository<DeviceConfig,Long> {
//    Optional<DeviceConfig> findByNameOfConfig(String nameOfConfig);
}
