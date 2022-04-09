package uz.sh.online_queue.service.schedule.departmentSchedule;

import uz.sh.online_queue.criteria.GenericCriteria;
import uz.sh.online_queue.dto.Dto;
import uz.sh.online_queue.dto.GenericDto;
import uz.sh.online_queue.dto.schedule.departmentSchedule.DepSchedule;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;

public interface DepartmentScheduleService <
        D extends GenericDto,
        CD extends Dto,
        UD extends GenericDto,
        C extends GenericCriteria,
        K extends Serializable> extends
        BaseService {

    ResponseEntity<Data<List<DepSchedule>>> getAll(C criteria,K id);

    ResponseEntity<Data<K>> create(CD createDto, K doctorId);

    ResponseEntity<Data<D>> get (K id);

    ResponseEntity<Data<D>> update(UD updateDto);

    ResponseEntity<Data<Void>> delete(K id);
}
