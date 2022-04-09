package uz.sh.online_queue.dto.department;

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
public class DepartmentUpdateDto extends GenericDto {
    @Schema(description = "Department name not be empty", example = "Boom",defaultValue = "default value (Boom)")
    @NotBlank(message = "Department name not be empty")
    @Size(min = 5 , message = "Department name should have at least 5 characters")
    private String name;

    @Schema(description = "Region name not be empty", example = "Tashkent",defaultValue = "default value (Tashkent)")
    private boolean block;


    @Builder(builderMethodName = "childBuilder")
    public DepartmentUpdateDto(String id, String name, boolean block) {
        super(id);
        this.name = name;
        this.block = block;
    }
}
