package uz.sh.online_queue.service.department;

import uz.sh.online_queue.criteria.GenericCriteria;
import uz.sh.online_queue.dto.Dto;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;

public interface DepartmentService<
        D extends Dto,
        CD extends Dto,
        UD extends Dto,
        C extends GenericCriteria,
        K extends Serializable> extends
        BaseService {

    ResponseEntity<Data<D>> get(K id);

    ResponseEntity<Data<Void>> block(K id);

    ResponseEntity<Data<Void>> delete(K id);

    ResponseEntity<Data<Void>> unblock(K id);

    ResponseEntity<Data<D>> update(UD updateDto);

    ResponseEntity<Data<K>> create(CD createDto, K id);

    ResponseEntity<Data<List<D>>> getAll(C criteria, K id);

    ResponseEntity<Data<List<D>>> blockList(C criteria,K id);

}
