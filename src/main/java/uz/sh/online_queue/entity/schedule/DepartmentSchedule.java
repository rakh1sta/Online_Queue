package uz.sh.online_queue.entity.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.sh.online_queue.entity.Auditable;

import javax.persistence.Entity;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DepartmentSchedule extends Auditable {
    private String departmentId;
    private LocalTime toTime;
    private LocalTime fromTIme;
}
