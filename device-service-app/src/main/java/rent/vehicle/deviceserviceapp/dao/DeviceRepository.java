package rent.vehicle.deviceserviceapp.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rent.vehicle.deviceserviceapp.model.Device;

public interface DeviceRepository extends JpaRepository<Device,Long> {
    boolean existsBySerialNumber(String serialNumber);

    @EntityGraph(attributePaths = {"deviceConfig"})
    Page<Device> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"deviceConfig"})
    Page<Device> findAll(Specification<Device> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"deviceConfig"})
    @Query("SELECT d FROM Device d WHERE d.id NOT IN " +
            "(SELECT v.device.id FROM Vehicle v WHERE v.device IS NOT NULL)")
    Page<Device> findDevicesWithoutVehicle(Pageable pageable);

    @Modifying
    @Query("UPDATE Vehicle v SET v.device = null WHERE v.device = :device")
    void detachVehicles(@Param("device") Device device);

}
