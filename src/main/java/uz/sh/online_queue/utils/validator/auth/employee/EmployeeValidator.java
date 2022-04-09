package uz.sh.online_queue.utils.validator.auth.employee;

import uz.sh.online_queue.dto.auth.employee.EmployeeCreateDto;
import uz.sh.online_queue.dto.auth.employee.EmployeeUpdateDto;
import uz.sh.online_queue.utils.BaseUtils;
import uz.sh.online_queue.utils.validator.AbstractValidator;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;

@Component
public class EmployeeValidator extends AbstractValidator<EmployeeCreateDto, EmployeeUpdateDto, ObjectId> {

    protected EmployeeValidator(BaseUtils baseUtils) {
        super(baseUtils);
    }

    @Override
    public void validateKey(ObjectId id) throws ValidationException {

    }

    @Override
    public void validOnCreate(EmployeeCreateDto employeeCreateDto) throws ValidationException {

    }

    @Override
    public void validOnUpdate(EmployeeUpdateDto cd) throws ValidationException {

    }
}
