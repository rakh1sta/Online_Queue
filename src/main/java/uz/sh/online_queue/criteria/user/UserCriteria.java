package uz.sh.online_queue.criteria.user;

import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;
import uz.sh.online_queue.criteria.BaseCriteria;

@Getter
@Setter
@ParameterObject
public class UserCriteria extends BaseCriteria {
    String role;
}
