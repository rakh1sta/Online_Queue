package uz.sh.online_queue.service.organization;

import uz.sh.online_queue.criteria.organization.OrganizationCriteria;
import uz.sh.online_queue.dto.organization.OrganizationCreateDto;
import uz.sh.online_queue.dto.organization.OrganizationDto;
import uz.sh.online_queue.dto.organization.OrganizationUpdateDto;
import uz.sh.online_queue.entity.organization.Organization;
import uz.sh.online_queue.enums.Regions;
import uz.sh.online_queue.mapper.organization.OrganizationMapper;
import uz.sh.online_queue.repository.organization.OrganizationRepository;
import uz.sh.online_queue.response.AppError;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.AbstractService;
import uz.sh.online_queue.utils.validator.organization.OrganizationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "organizations")
public class OrganizationServiceImpl extends AbstractService<OrganizationRepository, OrganizationMapper, OrganizationValidator>
        implements OrganizationService<OrganizationDto, OrganizationCreateDto, OrganizationUpdateDto, OrganizationCriteria, String> {


    @Autowired
    public OrganizationServiceImpl(OrganizationRepository repository, OrganizationMapper mapper, OrganizationValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public ResponseEntity<Data<String>> create(OrganizationCreateDto createDto, String name) {
        String region = Regions.getRegion(name);
        if (region.equals(Regions.UNDEFINED.getEng()))
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Organization not found by id : '%s".formatted(name)).build()), HttpStatus.OK);

        Optional<Organization> org = repository.findOrganizationByNameAndDistrictAndDeletedFalseAndBlockedFalse(createDto.getName(), createDto.getDistrict());
        if (org.isPresent())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.BAD_REQUEST)
                    .message("Already exist this %s clinical  in the %s district".formatted(createDto.getName(), createDto.getDistrict()))
                    .build()), HttpStatus.OK);

        Organization organization = mapper.fromCreateDto(createDto);
        organization.setRegion(name);
        repository.save(organization);
        return new ResponseEntity<>(new Data<>(organization.getId()), HttpStatus.OK);
    }

    @Override
    @CachePut(key = "#updateDto.id")
    public ResponseEntity<Data<OrganizationDto>> update(OrganizationUpdateDto updateDto) {
        Optional<Organization> organization = repository.findOrganizationByIdAndDeletedFalseAndBlockedFalse(updateDto.getId());
        if (organization.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Organization not found by id : '%s".formatted(updateDto.getId())).build()), HttpStatus.OK);

        Organization org = mapper.fromUpdateDto(updateDto);
        repository.save(org);
        return new ResponseEntity<>(new Data<>(mapper.toDto(org)), HttpStatus.OK);
    }


    @Override
    @CacheEvict(key = "#id")
    public ResponseEntity<Data<Void>> delete(String id) {
        Optional<Organization> organization = repository.findOrganizationByIdAndDeletedFalseAndBlockedFalse(id);
        if (organization.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Not found Organization like this id : %s".formatted(id)).build()), HttpStatus.OK);

        repository.deleteOrganization(id);
        return new ResponseEntity<>(new Data<>(true), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<List<OrganizationDto>>> getAll(OrganizationCriteria criteria) {
        String region = Regions.getRegion(criteria.getRegions().name());
        if (region.equals(Regions.UNDEFINED.getEng()))
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Organization not found : '%s".formatted(criteria.getRegions().name())).build()), HttpStatus.OK);

        List<Organization> organization = repository.findAllByCriteria(criteria.getPage()* criteria.getSize(), criteria.getSize(), region);
        if (organization.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Organizations not exist for this region : '%s".formatted(criteria.getRegions().name())).build()), HttpStatus.OK);

        return new ResponseEntity<>(new Data<>(mapper.toDto(organization)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<List<OrganizationDto>>> blockList(OrganizationCriteria criteria) {
        String region = Regions.getRegion(criteria.getRegions().name());
        if (region.equals(Regions.UNDEFINED.getEng()))
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Organization not found : '%s".formatted(region)).build()), HttpStatus.OK);

        List<Organization> organization = repository.findAllByCriteriaAndRegion(criteria.getPage()* criteria.getSize(), criteria.getSize(), region);
        if (organization.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Blocked Organizations not found for this region : '%s".formatted(region)).build()), HttpStatus.OK);

        return new ResponseEntity<>(new Data<>(mapper.toDto(organization)), HttpStatus.OK);
    }

    @Override
    @Cacheable(key ="#id")
    public ResponseEntity<Data<OrganizationDto>> get(String id) {
        Optional<Organization> organization = repository.findOrganizationByIdAndDeletedFalseAndBlockedFalse(id);
        if (organization.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Not found Organization like this id : %s".formatted(id)).build()), HttpStatus.OK);

        return new ResponseEntity<>(new Data<>(mapper.toDto(organization.get())), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<Void>> block(String id) {
        Optional<Organization> organization = repository.findOrganizationByIdAndDeletedFalse(id);
        if (organization.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Organization was not found by id %s".formatted(id)).build()), HttpStatus.OK);
        if (organization.get().isBlocked())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.ALREADY_REPORTED)
                    .message("This organization is already blocked").build()), HttpStatus.OK);
        repository.block(id);
        return new ResponseEntity<>(new Data<>(true), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<Void>> unblock(String id) {
        Optional<Organization> organization = repository.findOrganizationByIdAndDeletedFalse(id);
        if (organization.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Organization was not found by id %s".formatted(id)).build()), HttpStatus.OK);
        if (!organization.get().isBlocked())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.ALREADY_REPORTED)
                    .message("Organization not found from blocked organizations").build()), HttpStatus.OK);
        repository.unblock(id);
        return new ResponseEntity<>(new Data<>(true), HttpStatus.OK);
    }
}