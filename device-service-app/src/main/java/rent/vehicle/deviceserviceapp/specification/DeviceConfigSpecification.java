package rent.vehicle.deviceserviceapp.specification;

import org.springframework.data.jpa.domain.Specification;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;
import rent.vehicle.dto.list_request_dto.ListDeviceConfigsRequest;


public class DeviceConfigSpecification {

    public static Specification<DeviceConfig> equalName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    public static Specification<DeviceConfig> containName(String namePart) {
        return (root, query, criteriaBuilder) -> {
            if (namePart == null || namePart.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")), "%" + namePart.toLowerCase() + "%"
            );
        };
    }

    public static Specification<DeviceConfig> buildSpecification(ListDeviceConfigsRequest request) {
        Specification<DeviceConfig> spec = (root, query, cb) -> cb.conjunction();

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            spec = spec.and(equalName(request.getName()));
        }

        if (request.getNamePart() != null && !request.getNamePart().trim().isEmpty()) {
            spec = spec.and(containName(request.getNamePart()));
        }

        return spec;
    }
}
