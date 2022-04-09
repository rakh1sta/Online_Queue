package uz.sh.online_queue.dto.organization;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.sh.online_queue.dto.GenericDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class OrganizationUpdateDto extends GenericDto {

    @Schema(description = "Region name not be empty", example = "Tashkent",defaultValue = "default value (Tashkent)")
    @NotBlank(message = "Organization region not be empty")
    @Size(min = 5 , message = "Organization region should have at least 5 characters")
    private String region;

    @Schema(description = "Region name not be empty", example = "Boom",defaultValue = "default value (Boom)")
    @NotBlank(message = "Organization name not be empty")
    @Size(min = 5 , message = "Organization name should have at least 5 characters")
    private String name;

    @Schema(description = "Organization is block?",defaultValue = "true",example = "false")
    private boolean blocked;

    @Schema(description = "District name of region", example = "Chorsu",defaultValue = "default value (Chorsu)")
    @NotBlank(message = "Organization district not be empty")
    @Size(min = 5, message = "Organization district should have at least 5 characters")
    private String district;

    @Builder(builderMethodName = "childBuilder")
    public OrganizationUpdateDto(String id, String region, String name, boolean blocked, String district) {
        super(id);
        this.region = region;
        this.name = name;
        this.blocked = blocked;
        this.district = district;
    }
}