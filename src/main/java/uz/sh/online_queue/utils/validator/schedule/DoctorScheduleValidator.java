package uz.sh.online_queue.utils.validator.schedule;

import uz.sh.online_queue.dto.schedule.doctorSchedule.DoctorScheduleCreateDto;
import uz.sh.online_queue.dto.schedule.doctorSchedule.DoctorScheduleUpdateDto;
import uz.sh.online_queue.utils.BaseUtils;
import uz.sh.online_queue.utils.validator.AbstractValidator;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;

@Component
public class DoctorScheduleValidator extends AbstractValidator<DoctorScheduleCreateDto, DoctorScheduleUpdateDto,String> {
    @Override
    public void validateKey(String id) throws ValidationException {

    }

    @Override
    public void validOnCreate(DoctorScheduleCreateDto doctorScheduleCreateDto) throws ValidationException {

    }

    @Override
    public void validOnUpdate(DoctorScheduleUpdateDto cd) throws ValidationException {

    }

    protected DoctorScheduleValidator(BaseUtils baseUtils) {
        super(baseUtils);
    }
}

