package uz.sh.online_queue.mapper.department;

import uz.sh.online_queue.dto.department.DepartmentCreateDto;
import uz.sh.online_queue.dto.department.DepartmentDto;
import uz.sh.online_queue.dto.department.DepartmentUpdateDto;
import uz.sh.online_queue.entity.department.Department;
import uz.sh.online_queue.mapper.BaseMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
@Component
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends BaseMapper<
        Department,
        DepartmentDto,
        DepartmentCreateDto,
        DepartmentUpdateDto> {

        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        Department fromUpdateDto(DepartmentUpdateDto d, @MappingTarget Department meal);

}
