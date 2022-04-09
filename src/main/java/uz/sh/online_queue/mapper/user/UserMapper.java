package uz.sh.online_queue.mapper.user;

import uz.sh.online_queue.dto.auth.user.UserCreateDto;
import uz.sh.online_queue.dto.auth.user.UserDto;
import uz.sh.online_queue.dto.auth.user.UserUpdateDto;
import uz.sh.online_queue.entity.auth.Auth;
import uz.sh.online_queue.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<
        Auth,
        UserDto,
        UserCreateDto,
        UserUpdateDto> {

}
