package uz.sh.online_queue.mapper.user;

import uz.sh.online_queue.dto.auth.employee.AdminCreateDto;
import uz.sh.online_queue.dto.auth.employee.AdminDto;
import uz.sh.online_queue.dto.auth.employee.AdminUpdateDto;
import uz.sh.online_queue.entity.auth.Auth;
import uz.sh.online_queue.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface AdminMapper extends BaseMapper<
        Auth,
        AdminDto,
        AdminCreateDto,
        AdminUpdateDto> {
}
