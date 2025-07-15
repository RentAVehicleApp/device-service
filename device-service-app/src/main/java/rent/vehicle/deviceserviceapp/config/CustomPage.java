package rent.vehicle.deviceserviceapp.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomPage<T> {
    private List<T> content;
    private int number;
    private int size;
    private long totalElements;

    @JsonCreator
    public CustomPage(
            @JsonProperty("content") List<T> content,
            @JsonProperty("number") int number,
            @JsonProperty("size") int size,
            @JsonProperty("totalElements") long totalElements
    ) {
        this.content = content;
        this.number = number;
        this.size = size;
        this.totalElements = totalElements;
    }

}
