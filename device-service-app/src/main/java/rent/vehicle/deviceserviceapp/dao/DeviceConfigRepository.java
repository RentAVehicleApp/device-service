package rent.vehicle.deviceserviceapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;

public interface DeviceConfigRepository extends JpaRepository<DeviceConfig, Long>, JpaSpecificationExecutor<DeviceConfig> {

    @Modifying
    @Query("UPDATE Device d SET d.deviceConfig = null WHERE d.deviceConfig = :deviceConfig")
    void detachDevices(@Param("deviceConfig") DeviceConfig deviceConfig);

}
