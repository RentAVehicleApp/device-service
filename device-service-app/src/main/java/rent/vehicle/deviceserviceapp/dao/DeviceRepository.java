package rent.vehicle.deviceserviceapp.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import rent.vehicle.deviceserviceapp.model.Device;

public interface DeviceRepository extends JpaRepository<Device,Long> {
    boolean existsBySerialNumber(String serialNumber);

    Page<Device> findAll(Specification<Device> spec, Pageable pageable);
}
