package uz.sh.online_queue.utils.validator.schedule;

import uz.sh.online_queue.dto.schedule.departmentSchedule.DepartmentScheduleCreateDto;
import uz.sh.online_queue.dto.schedule.departmentSchedule.DepartmentScheduleUpdateDto;
import uz.sh.online_queue.utils.BaseUtils;
import uz.sh.online_queue.utils.validator.AbstractValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;

@Component
public class DepartmentScheduleValidator extends AbstractValidator<DepartmentScheduleCreateDto, DepartmentScheduleUpdateDto,String> {

    @Autowired
    protected DepartmentScheduleValidator(BaseUtils baseUtils) {
        super(baseUtils);
    }

    @Override
    public void validateKey(String id) throws ValidationException {

    }

    @Override
    public void validOnCreate(DepartmentScheduleCreateDto departmentScheduleCreateDto) throws ValidationException {

    }

    @Override
    public void validOnUpdate(DepartmentScheduleUpdateDto cd) throws ValidationException {

    }

}
