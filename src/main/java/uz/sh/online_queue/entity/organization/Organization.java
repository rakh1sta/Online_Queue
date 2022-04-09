package uz.sh.online_queue.entity.organization;

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
public class Organization extends Auditable {
    private String region;
    private String name;
    private boolean blocked;
    private String district;
}