package uz.sh.online_queue.entity.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.sh.online_queue.entity.Auditable;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Department extends Auditable {
    private String organizationId;
    private String name;
    private boolean block;
}
