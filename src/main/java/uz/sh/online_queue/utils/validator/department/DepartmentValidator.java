package uz.sh.online_queue.utils.validator.department;

import uz.sh.online_queue.dto.department.DepartmentCreateDto;
import uz.sh.online_queue.dto.department.DepartmentUpdateDto;
import uz.sh.online_queue.utils.BaseUtils;
import uz.sh.online_queue.utils.validator.AbstractValidator;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;

@Component
public class DepartmentValidator  extends AbstractValidator<DepartmentCreateDto, DepartmentUpdateDto,String> {
    protected DepartmentValidator(BaseUtils baseUtils) {
        super(baseUtils);
    }

    @Override
    public void validateKey(String id) throws ValidationException {

    }

    @Override
    public void validOnCreate(DepartmentCreateDto departmentCreateDto) throws ValidationException {

    }

    @Override
    public void validOnUpdate(DepartmentUpdateDto cd) throws ValidationException {

    }
}
