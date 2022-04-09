package uz.sh.online_queue.dto.schedule.doctorSchedule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import uz.sh.online_queue.dto.GenericDto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorScheduleDto extends GenericDto {
    @Schema(description = "Doctor id shouldn't be empty", example = "jsndkwj32983uehf832983", required = true)
    @NotBlank(message = "department id is null")
    @MongoId(FieldType.OBJECT_ID)
    private String doctorId;

    private LocalDate date;

    private LocalTime toTime;

    private LocalTime fromTIme;

    @Builder(builderMethodName = "childBuilder")
    public DoctorScheduleDto(String id, String doctorId, LocalDate dayOfWeek, LocalTime to, LocalTime from) {
        super(id);
        this.doctorId = doctorId;
        this.date = dayOfWeek;
        this.toTime = to;
        this.fromTIme = from;
    }
}
