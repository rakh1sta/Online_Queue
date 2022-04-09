package uz.sh.online_queue.dto.schedule.doctorSchedule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.sh.online_queue.dto.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorScheduleCreateDto implements Dto {
    @Schema(description = "the date to find out which day is the workday", example = "2022-03-25", required = true)
    private LocalDate date;

    @Schema(description = "start time of working day", example = "23:20:29", required = true)
    private LocalTime toTime;

    @Schema(description = "end time of working day", example = "23:20:29", required = true)
    private LocalTime fromTIme;
}
