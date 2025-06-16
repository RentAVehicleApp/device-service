package rent.vehicle.deviceserviceapp.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import rent.vehicle.deviceserviceapp.model.Vehicle;

import java.awt.print.Pageable;
import java.util.Collection;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    boolean existsByRegistrationNumber(String registrationNumber);


}
