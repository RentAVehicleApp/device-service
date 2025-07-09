package rent.vehicle.deviceserviceapp.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rent.vehicle.deviceserviceapp.model.Device;

public interface DeviceRepository extends JpaRepository<Device,Long> {
    boolean existsBySerialNumber(String serialNumber);

    @EntityGraph(attributePaths = {"vehicle", "deviceConfig"})
    Page<Device> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"vehicle", "deviceConfig"})
    Page<Device> findAll(Specification<Device> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"vehicle", "deviceConfig"})
    @Query("SELECT d FROM Device d WHERE d.id NOT IN " +
            "(SELECT v.device.id FROM Vehicle v WHERE v.device IS NOT NULL)")
    Page<Device> findDevicesWithoutVehicle(Pageable pageable);
}
