package uz.sh.online_queue.mapper.schedule;

import uz.sh.online_queue.dto.schedule.departmentSchedule.DepartmentScheduleCreateDto;
import uz.sh.online_queue.dto.schedule.departmentSchedule.DepartmentScheduleDto;
import uz.sh.online_queue.dto.schedule.departmentSchedule.DepartmentScheduleUpdateDto;
import uz.sh.online_queue.entity.schedule.DepartmentSchedule;
import uz.sh.online_queue.mapper.BaseMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface DepartmentScheduleMapper extends BaseMapper<
        DepartmentSchedule,
        DepartmentScheduleDto,
        DepartmentScheduleCreateDto,
        DepartmentScheduleUpdateDto> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DepartmentSchedule fromUpdateDto(DepartmentScheduleUpdateDto d, @MappingTarget DepartmentSchedule entity);


}
