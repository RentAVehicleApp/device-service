package rent.vehicle.deviceserviceapp.specification;

import org.springframework.data.jpa.domain.Specification;
import rent.vehicle.deviceserviceapp.model.Device;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;
import rent.vehicle.dto.ListDeviceConfigsRequest;

public class DeviceSpecification {

    public static Specification<Device> equalSerialNumber(String serialNumber) {
        return (root, query, criteriaBuilder) -> {
            if (serialNumber == null || serialNumber.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("serialNumber"), serialNumber);
        };
    }

    public static Specification<Device> containSerialNumber(String serialNumberPart) {
        return (root, query, criteriaBuilder) -> {
            if (serialNumberPart == null || serialNumberPart.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("serialNumber")), "%" + serialNumberPart.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Device> containNode(String nodePart) {
        return ((root, query, criteriaBuilder) ->)
    }

    public static Specification<DeviceConfig> buildSpecification(ListDeviceConfigsRequest request) {
        Specification<DeviceConfig> spec = (root, query, cb) -> cb.conjunction();

//        if (request.getName() != null && !request.getName().trim().isEmpty()) {
//            spec = spec.and(equalName(request.getName()));
//        }
//
//        if (request.getNamePart() != null && !request.getNamePart().trim().isEmpty()) {
//            spec = spec.and(containName(request.getNamePart()));
//        }

        return spec;
    }
}
