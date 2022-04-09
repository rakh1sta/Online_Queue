package uz.sh.online_queue.dto.schedule.departmentSchedule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.sh.online_queue.dto.Dto;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class DepSchedule implements Dto {

    @Schema(description = "Department id shouldn't be empty", example = "jsndkwj32983uehf832983", required = true)
    @NotBlank(message = "department id is null")
    private String departmentId;

    @Schema(description = "Name of department in Organization", example = "Doctor")
    private String name;

    private LocalTime toTime;

    private LocalTime fromTime;

    @Builder(builderMethodName = "childBuilder")
    public DepSchedule(String departmentId, String name, LocalTime toTime, LocalTime fromTIme) {
        this.departmentId = departmentId;
        this.name = name;
        this.toTime = toTime;
        this.fromTime = fromTIme;
    }
}
