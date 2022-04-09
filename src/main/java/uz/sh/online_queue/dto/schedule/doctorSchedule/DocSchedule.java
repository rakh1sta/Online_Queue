package uz.sh.online_queue.dto.schedule.doctorSchedule;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import uz.sh.online_queue.dto.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class DocSchedule implements Dto {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String firstName;
    private String lastName;

    private LocalDate date;

    private LocalTime toTime;

    private LocalTime fromTIme;


    @Builder(builderMethodName = "childBuilder")
    public DocSchedule(String id, String firstName, String lastName, LocalDate date, LocalTime to, LocalTime from) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.toTime = to;
        this.fromTIme = from;
    }
}
