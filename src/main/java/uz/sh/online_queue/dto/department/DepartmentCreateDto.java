package uz.sh.online_queue.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.sh.online_queue.dto.Dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentCreateDto implements Dto {
    @NotBlank(message = "Department name not be empty")
    @Schema(description = "Department  name should be unique for a organization.Not be empty", example = "Doctor", required = true)
    @Size(min = 5, message = "Department name should have at least 5 characters")
    private String name;
}

