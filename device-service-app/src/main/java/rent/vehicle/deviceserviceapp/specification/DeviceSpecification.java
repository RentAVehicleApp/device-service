package rent.vehicle.deviceserviceapp.specification;

import org.springframework.data.jpa.domain.Specification;
import rent.vehicle.deviceserviceapp.model.Device;
import rent.vehicle.dto.list_request_dto.ListDevicesRequest;
import rent.vehicle.enums.ConnectionStatus;
import rent.vehicle.enums.DeviceModel;


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

    public static Specification<Device> containNode(String nodesPart) {
        return (root, query, criteriaBuilder) -> {
            if (nodesPart == null || nodesPart.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nodes")), "%" + nodesPart.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Device> equalDeviceModel(DeviceModel deviceModel) {
        return (root, query, criteriaBuilder) -> {
            if (deviceModel == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("deviceModel"), deviceModel);
        };
    }

    public static Specification<Device> equalConnectionStatus(ConnectionStatus connectionStatus) {
        return (root, query, criteriaBuilder) -> {
            if (connectionStatus == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("connectionStatus"), connectionStatus);
        };
    }

    // TODO search by DeviceConfig
    // TODO search by Vehicle
//    public static Specification<Device> deviceConfigsIdIn(Set<Long> deviceConfigsIdList) {
//        return (root, query, criteriaBuilder) -> {
//            if (deviceConfigsIdList == null || deviceConfigsIdList.isEmpty()) {
//                return criteriaBuilder.conjunction();
//            }
//            return root.get("deviceConfigsIdList").get("id").in(deviceConfigsIdList);
//        };
//    }


    public static Specification<Device> buildSpecification(ListDevicesRequest request) {
        Specification<Device> spec = (root, query, cb) -> cb.conjunction();

        if (request.getSerialNumber() != null && !request.getSerialNumber().trim().isEmpty()) {
            spec = spec.and(equalSerialNumber(request.getSerialNumber()));
        }

        if (request.getSerialNumberPart() != null && !request.getSerialNumberPart().trim().isEmpty()) {
            spec = spec.and(containSerialNumber(request.getSerialNumberPart()));
        }

        if (request.getNodesPart() != null && !request.getNodesPart().trim().isEmpty()) {
            spec = spec.and(containNode(request.getNodesPart()));
        }

        if (request.getDeviceModel() != null) {
            spec = spec.and(equalDeviceModel(request.getDeviceModel()));
        }

        if (request.getConnectionStatus() != null) {
            spec = spec.and(equalConnectionStatus(request.getConnectionStatus()));
        }

//        if (request.getDeviceConfigsIdList() != null) {
//            Specification<DeviceConfig> configSpec =
//                    DeviceConfigSpecification.buildSpecification(request.getDeviceConfig());
//            spec = spec.and(equalDeviceConfig(configSpec));
//        }

        return spec;
    }
}
