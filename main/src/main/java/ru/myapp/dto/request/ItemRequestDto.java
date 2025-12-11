package ru.myapp.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record ItemRequestDto(

    @NotBlank
    @Size(min = 3, max = 255)
    @JsonProperty(value = "name", required = true)
    String name,

    @NotBlank
    @Size(min = 10, max = 255)
    @JsonProperty(value = "description", required = true)
    String description,

    @Positive
    @JsonProperty(value = "amount")
    Integer amount,

    @Past
    @JsonProperty(value = "offsetDateTime", required = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    OffsetDateTime offsetDateTime) {

}
