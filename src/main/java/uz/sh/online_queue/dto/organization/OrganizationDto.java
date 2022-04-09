package uz.sh.online_queue.dto.organization;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.sh.online_queue.dto.GenericDto;

@Getter
@Setter
@NoArgsConstructor
public class OrganizationDto extends GenericDto {

    @Schema(description = "Name of region in Uzbekistan", example = "Tashkent")
    private String region;

    @Schema(description = "Name of organization", example = "Boom")
    private String name;

    @Schema(description = "Organization is block ?", example = "true")
    private boolean blocked;

    @Schema(description = "District name of region", example = "Chorsu")
    private String district;

    @Builder(builderMethodName = "childBuilder")
    public OrganizationDto(String id, String region, String name, boolean blocked, String district) {
        super(id);
        this.region = region;
        this.name = name;
        this.blocked = blocked;
        this.district = district;
    }
}
