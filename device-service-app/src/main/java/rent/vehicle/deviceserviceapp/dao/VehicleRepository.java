package rent.vehicle.deviceserviceapp.dao;

import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rent.vehicle.deviceserviceapp.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    boolean existsByRegistrationNumber(String registrationNumber);


    Page<Vehicle> findAll(Specification<Vehicle> spec, Pageable pageable);

    @Query("SELECT v FROM Vehicle v WHERE distance(v.point, :targetPoint) <= :radius ORDER BY distance(v.point, :targetPoint)")
    Page<Vehicle> findNearbyVehicles(
            @Param("targetPoint") Point targetPoint,
            @Param("radius") long radius,
            Pageable pageable
    );
}
