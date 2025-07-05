package rent.vehicle.deviceserviceapp.config;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rent.vehicle.deviceserviceapp.model.Vehicle;
import rent.vehicle.dto.PointFromLatLonDto;
import rent.vehicle.dto.VehicleDto;

@Configuration
public class ServiceConfig {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.addMappings(new PropertyMap<Vehicle, VehicleDto>() {
            @Override
            protected void configure() {
                using(ctx -> {
                    Point point = (Point) ctx.getSource();
                    if (point == null) {
                        return null;
                    }
                    return PointFromLatLonDto.builder()
                            .longitude(String.valueOf(point.getX()))
                            .latitude(String.valueOf(point.getY()))
                            .build();
                }).map(source.getPoint(), destination.getPointFromLatLonDto());
            }
        });

        modelMapper.addMappings(new PropertyMap<VehicleDto, Vehicle>() {
            @Override
            protected void configure() {
                using(ctx -> {
                    PointFromLatLonDto pointFromLatLonDto = (PointFromLatLonDto) ctx.getSource();
                    if (pointFromLatLonDto == null) {
                        return null;
                    }
                    GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
                    Point point = geometryFactory.createPoint(
                            new Coordinate(
                                    Double.parseDouble(pointFromLatLonDto.getLongitude()),
                                    Double.parseDouble(pointFromLatLonDto.getLatitude())
                            ));
                    return point;
                }).map(source.getPointFromLatLonDto(), destination.getPoint());
            }
        });
        return modelMapper;
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*") // Разрешить ВСЕ домены
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }


}

/* Вариант 2: Хранить origins в БД и проверять в CORS фильтре
Сделай собственный CorsFilter, который проверяет Origin в заголовке запроса.

Разрешённые адреса храни в БД или Redis — можно менять без перезапуска.
https://chatgpt.com/share/6852c4ce-e7a8-800b-bcb5-57e2259a11a1
*/