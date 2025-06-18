package rent.vehicle.deviceserviceapp.specification;

import org.springframework.data.jpa.domain.Specification;
import rent.vehicle.deviceserviceapp.model.DeviceConfig;
import rent.vehicle.dto.ListDeviceConfigsRequest;

import java.util.List;

public class DeviceConfigSpecification {

    public static Specification<DeviceConfig> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    public static Specification<DeviceConfig> nameContains(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<DeviceConfig> nameByListNames(List<String> names) {
        return (root, query, criteriaBuilder) -> {
            if (names == null || names.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("name").in(names);
        };
    }

    public static Specification<DeviceConfig> buildSpecification(ListDeviceConfigsRequest listDeviceConfigsRequest) {
        return nameContains(listDeviceConfigsRequest.getNameOfConfig());
    }
}
