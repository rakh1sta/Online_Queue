package uz.sh.online_queue.mapper.user;

import uz.sh.online_queue.dto.auth.employee.EmployeeCreateDto;
import uz.sh.online_queue.dto.auth.employee.EmployeeDto;
import uz.sh.online_queue.dto.auth.employee.EmployeeUpdateDto;
import uz.sh.online_queue.entity.auth.Auth;
import uz.sh.online_queue.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends BaseMapper<
        Auth,
        EmployeeDto,
        EmployeeCreateDto,
        EmployeeUpdateDto> {

}
