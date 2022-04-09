package uz.sh.online_queue.criteria.organization;

import lombok.*;
import org.springdoc.api.annotations.ParameterObject;
import uz.sh.online_queue.criteria.BaseCriteria;
import uz.sh.online_queue.enums.Regions;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ParameterObject
public class OrganizationCriteria extends BaseCriteria {
    private Regions regions;
}
