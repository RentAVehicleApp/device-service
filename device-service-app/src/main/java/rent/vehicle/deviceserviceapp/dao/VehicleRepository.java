package rent.vehicle.deviceserviceapp.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import rent.vehicle.deviceserviceapp.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    boolean existsByRegistrationNumber(String registrationNumber);


    Page<Vehicle> findAll(Specification<Vehicle> spec, Pageable pageable);
}
