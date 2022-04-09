package uz.sh.online_queue.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.sh.online_queue.dto.GenericDto;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentDto extends GenericDto {
    @Schema(description = "id of organization in region", example = "Boom")
    private String organizationId;

    @Schema(description = "Name of department in Organization", example = "Doctor")
    private String name;

    @Schema(description = "Department is block ? ", example = "true")
    private boolean block;

    @Builder(builderMethodName = "childBuilder")
    public DepartmentDto(String id, String organizationId, String name, boolean block) {
        super(id);
        this.organizationId = organizationId;
        this.name = name;
        this.block = block;
    }
}
