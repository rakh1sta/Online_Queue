package uz.sh.online_queue.entity.queue;

import lombok.*;
import uz.sh.online_queue.entity.Auditable;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Queue extends Auditable {
    private String departmentId;
    private String userId;
    private int queue;
    private LocalDateTime day;
}
