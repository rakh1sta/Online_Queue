package uz.sh.online_queue.dto.organization;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.sh.online_queue.dto.Dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@Builder
public class OrganizationCreateDto implements Dto {

    @Schema(description = "Unique name for a district", example = "Boom", required = true)
    @NotBlank(message = "Organization name not be empty")
    @Size(min = 5, message = "Organization name should have at least 5 characters")
    private String name;


    @Schema(description = "District name of region", example = "Chorsu", required = true)
    @NotBlank(message = "Organization district not be empty")
    @Size(min = 5, message = "Organization district should have at least 5 characters")
    private String district;
}
