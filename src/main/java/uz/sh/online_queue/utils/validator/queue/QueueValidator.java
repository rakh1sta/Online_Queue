package uz.sh.online_queue.utils.validator.queue;

import uz.sh.online_queue.dto.queue.QueueCreateDto;
import uz.sh.online_queue.dto.queue.QueueUpdateDto;
import uz.sh.online_queue.utils.BaseUtils;
import uz.sh.online_queue.utils.validator.AbstractValidator;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;

@Component
public class QueueValidator extends AbstractValidator<QueueCreateDto, QueueUpdateDto, String> {
    protected QueueValidator(BaseUtils baseUtils) {
        super(baseUtils);
    }

    @Override
    public void validateKey(String id) throws ValidationException {

    }

    @Override
    public void validOnCreate(QueueCreateDto queueCreateDto) throws ValidationException {

    }

    @Override
    public void validOnUpdate(QueueUpdateDto cd) throws ValidationException {

    }
}
