package rent.vehicle.deviceserviceapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;

public interface DeviceConfigRepository
        extends JpaRepository<DeviceConfig, Long>,
        JpaSpecificationExecutor<DeviceConfig> {
//    Optional<DeviceConfig> findByNameOfConfig(String nameOfConfig);
}
