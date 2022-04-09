package uz.sh.online_queue.dto.schedule.departmentSchedule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.sh.online_queue.dto.Dto;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentScheduleCreateDto implements Dto {

    @Schema(description = "start time of working day", example = "23:20:29", required = true)
    private LocalTime toTime;

    @Schema(description = "end time of working day", example = "23:20:29", required = true)
    private LocalTime fromTime;
}
