package uz.sh.online_queue.utils.validator.organization;

import uz.sh.online_queue.dto.organization.OrganizationCreateDto;
import uz.sh.online_queue.dto.organization.OrganizationUpdateDto;
import uz.sh.online_queue.utils.BaseUtils;
import uz.sh.online_queue.utils.validator.AbstractValidator;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;

@Component
public class OrganizationValidator extends AbstractValidator<
        OrganizationCreateDto,
        OrganizationUpdateDto,
        Long> {

    protected OrganizationValidator(BaseUtils baseUtils) {
        super(baseUtils);
    }

    @Override
    public void validateKey(Long id) throws ValidationException {

    }

    @Override
    public void validOnCreate(OrganizationCreateDto organizationCreateDto) throws ValidationException {

    }

    @Override
    public void validOnUpdate(OrganizationUpdateDto cd) throws ValidationException {

    }
}
