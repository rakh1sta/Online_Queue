package uz.sh.online_queue.utils.validator.auth.user;

import uz.sh.online_queue.dto.auth.user.UserCreateDto;
import uz.sh.online_queue.dto.auth.user.UserUpdateDto;
import uz.sh.online_queue.utils.BaseUtils;
import uz.sh.online_queue.utils.validator.AbstractValidator;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;

@Component
public class UserValidator extends AbstractValidator<UserCreateDto, UserUpdateDto, ObjectId> {

    protected UserValidator(BaseUtils baseUtils) {
        super(baseUtils);
    }

    @Override
    public void validateKey(ObjectId id) throws ValidationException {

    }

    @Override
    public void validOnCreate(UserCreateDto userCreateDto) throws ValidationException {

    }

    @Override
    public void validOnUpdate(UserUpdateDto cd) throws ValidationException {

    }
}
