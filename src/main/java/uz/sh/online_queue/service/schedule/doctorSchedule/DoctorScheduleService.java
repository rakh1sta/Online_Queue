package uz.sh.online_queue.service.schedule.doctorSchedule;

import uz.sh.online_queue.criteria.GenericCriteria;
import uz.sh.online_queue.dto.Dto;
import uz.sh.online_queue.dto.GenericDto;
import uz.sh.online_queue.dto.schedule.doctorSchedule.DocSchedule;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DoctorScheduleService<
        D extends GenericDto,
        CD extends Dto,
        UD extends GenericDto,
        C extends GenericCriteria,
        K extends Serializable> extends
        BaseService {

    ResponseEntity<Data<List <DocSchedule>>> getAll(C criteria,K id);

    ResponseEntity<Data<Map<LocalDate,K>>> create(List<CD> createDto, String doctorId);

    ResponseEntity<Data<D>> get (String id);

    ResponseEntity<Data<D>> update(UD updateDto);

    ResponseEntity<Data<Void>> delete(K id);
}
