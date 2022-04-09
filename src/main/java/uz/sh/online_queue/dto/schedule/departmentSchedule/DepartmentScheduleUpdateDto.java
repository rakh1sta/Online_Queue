package uz.sh.online_queue.dto.schedule.departmentSchedule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.sh.online_queue.dto.GenericDto;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentScheduleUpdateDto extends GenericDto {
    @Schema(description = "Department id shouldn't be empty", example = "jsndkwj32983uehf832983", required = true)
    @NotBlank(message = "department id is null")
    private String departmentId;


    private LocalTime toTime;

    private LocalTime fromTime;

    @Builder(builderMethodName = "childBuilder")
    public DepartmentScheduleUpdateDto(String id, String departmentId, LocalTime toTime, LocalTime fromTIme) {
        super(id);
        this.departmentId = departmentId;
        this.toTime = toTime;
        this.fromTime = fromTIme;
    }
}
