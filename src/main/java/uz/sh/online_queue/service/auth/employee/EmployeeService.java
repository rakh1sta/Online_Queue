package uz.sh.online_queue.service.auth.employee;

import uz.sh.online_queue.criteria.user.UserCriteria;
import uz.sh.online_queue.dto.auth.employee.EmployeeCreateDto;
import uz.sh.online_queue.dto.auth.employee.EmployeeDto;
import uz.sh.online_queue.dto.auth.employee.EmployeeUpdateDto;
import uz.sh.online_queue.entity.auth.Auth;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.BaseService;
import uz.sh.online_queue.service.GenericCrudService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService extends GenericCrudService<
        Auth,
        EmployeeDto,
        EmployeeCreateDto,
        EmployeeUpdateDto,
        UserCriteria,
        String> , BaseService {

    ResponseEntity<Data<Void>> block(String id);
    ResponseEntity<Data<Void>> unblock(String id);
    ResponseEntity<Data<Void>> addRole (String id,String role);
    ResponseEntity<Data<List<EmployeeDto>>> getAllByOrgId(UserCriteria criteria, String orgId);
    ResponseEntity<Data<List<EmployeeDto>>> getAllBlockedEmployees(UserCriteria criteria,String orgId);
}
