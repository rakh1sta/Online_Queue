package uz.sh.online_queue.mapper.schedule;

import uz.sh.online_queue.dto.schedule.doctorSchedule.DoctorScheduleCreateDto;
import uz.sh.online_queue.dto.schedule.doctorSchedule.DoctorScheduleDto;
import uz.sh.online_queue.dto.schedule.doctorSchedule.DoctorScheduleUpdateDto;
import uz.sh.online_queue.entity.schedule.DoctorSchedule;
import uz.sh.online_queue.mapper.BaseMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
@Component
@Mapper(componentModel = "spring")
public interface DoctorScheduleMapper extends BaseMapper<
        DoctorSchedule,
        DoctorScheduleDto,
        DoctorScheduleCreateDto,
        DoctorScheduleUpdateDto> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DoctorSchedule fromUpdateDto(DoctorScheduleUpdateDto d, @MappingTarget DoctorSchedule meal);

}
