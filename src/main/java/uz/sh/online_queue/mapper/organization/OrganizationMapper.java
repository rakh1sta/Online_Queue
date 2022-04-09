package uz.sh.online_queue.mapper.organization;

import uz.sh.online_queue.dto.organization.OrganizationCreateDto;
import uz.sh.online_queue.dto.organization.OrganizationDto;
import uz.sh.online_queue.dto.organization.OrganizationUpdateDto;
import uz.sh.online_queue.entity.organization.Organization;
import uz.sh.online_queue.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface OrganizationMapper extends BaseMapper<
        Organization,
        OrganizationDto,
        OrganizationCreateDto,
        OrganizationUpdateDto> {


}