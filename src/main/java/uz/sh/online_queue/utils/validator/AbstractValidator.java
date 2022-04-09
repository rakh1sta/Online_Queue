package uz.sh.online_queue.utils.validator;

import uz.sh.online_queue.utils.BaseUtils;

import javax.xml.bind.ValidationException;

public abstract class AbstractValidator<CD, UD, K> implements Validator {
    protected final BaseUtils baseUtils;

    protected AbstractValidator(BaseUtils baseUtils) {
        this.baseUtils = baseUtils;
    }

    public abstract void validateKey(K id) throws ValidationException;

    public abstract void validOnCreate(CD cd) throws ValidationException;

    public abstract void validOnUpdate(UD cd) throws ValidationException;
}
