package uz.sh.online_queue.criteria.queue;

import uz.sh.online_queue.criteria.GenericCriteria;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QueueCriteria implements GenericCriteria {
    private int size = 6;
    private int page = 0;
}
