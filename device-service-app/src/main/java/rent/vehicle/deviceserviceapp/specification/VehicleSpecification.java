package rent.vehicle.deviceserviceapp.specification;


import org.springframework.data.jpa.domain.Specification;
import rent.vehicle.deviceserviceapp.model.Vehicle;
import rent.vehicle.dto.ListVehiclesRequest;
import rent.vehicle.enums.Availability;
import rent.vehicle.enums.VehicleModel;

import java.util.Set;


public class VehicleSpecification {

    public static Specification<Vehicle> equalRegistrationNumber(String registrationNumber) {
        return (root, query, criteriaBuilder) -> {
            if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("registrationNumber"), registrationNumber);
        };
    }

    public static Specification<Vehicle> containRegistrationNumber(String registrationNumberPart) {
        return (root, query, criteriaBuilder) -> {
            if (registrationNumberPart == null || registrationNumberPart.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("registrationNumber")),
                    "%" + registrationNumberPart.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Vehicle> equalVehicleModel(VehicleModel vehicleModel) {
        return (root, query, criteriaBuilder) -> {
            if (vehicleModel == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("vehicleModel"), vehicleModel);
        };
    }

    public static Specification<Vehicle> equalAvailability(Availability availability) {
        return (root, query, criteriaBuilder) -> {
            if (availability == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("availability"), availability);
        };
    }

    //TODO search by geolocation
//    public static Specification<Vehicle> equalPoint(Point point) {
//        return (root, query, criteriaBuilder) -> {
//            if (point == null) {
//                return criteriaBuilder.conjunction();
//            }
//            return criteriaBuilder.equal(root.get("point"), point);
//        };
//    }

    public static Specification<Vehicle> checkIfBatteryStatusBetween(int batteryStatusMin, int batteryStatusMax) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.between(root.get("batteryStatus"), batteryStatusMin, batteryStatusMax);
        };
    }

    public static Specification<Vehicle> containNodes(String nodesPart) {
        return (root, query, criteriaBuilder) -> {
            if (nodesPart == null || nodesPart.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nodes")),
                    "%" + nodesPart.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Vehicle> containSetDeviceId(Set<Long> deviceIds) {
        return (root, query, criteriaBuilder) -> {
            if (deviceIds == null || deviceIds.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("device").get("id").in(deviceIds);
        };
    }

    public static Specification<Vehicle> buildSpecification(ListVehiclesRequest request) {
        Specification<Vehicle> spec = (root, query, cb) -> cb.conjunction();

        if (request.getRegistrationNumber() != null && !request.getRegistrationNumber().trim().isEmpty()) {
            spec = spec.and(equalRegistrationNumber(request.getRegistrationNumber()));
        }

        if (request.getRegistrationNumberPart() != null && !request.getRegistrationNumberPart().trim().isEmpty()) {
            spec = spec.and(containRegistrationNumber(request.getRegistrationNumberPart()));
        }

        if (request.getVehicleModel() != null) {
            spec = spec.and(equalVehicleModel(request.getVehicleModel()));
        }

        if (request.getAvailability() != null) {
            spec = spec.and(equalAvailability(request.getAvailability()));
        }
        //TODO Search by point
        if (request.getLatitude() != 0 && request.getLongitude() != 0 && request.getRadius() != 0) {
//            spec = spec.and(equalPoint(request.getPoint()));
        }

        int min = request.getBatteryStatusMin() != null ? request.getBatteryStatusMin() : 0;
        int max = request.getBatteryStatusMax() != null ? request.getBatteryStatusMax() : 100;
        spec = spec.and(checkIfBatteryStatusBetween(min, max));


        if (request.getNodes() != null && !request.getNodes().trim().isEmpty()) {
            spec = spec.and(containNodes(request.getNodes()));
        }

        if (request.getDeviceIds() != null && !request.getDeviceIds().isEmpty()) {
            spec = spec.and(containSetDeviceId(request.getDeviceIds()));
        }

        return spec;
    }
    //todo посмотреть как он генерирует query в логах.
}