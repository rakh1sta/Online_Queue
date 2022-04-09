package uz.sh.online_queue.entity.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import uz.sh.online_queue.entity.Auditable;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DoctorSchedule extends Auditable {
    @MongoId(FieldType.OBJECT_ID)
    private String doctorId;
    private LocalDate date;
    private LocalTime toTime;
    private LocalTime fromTIme;
}
