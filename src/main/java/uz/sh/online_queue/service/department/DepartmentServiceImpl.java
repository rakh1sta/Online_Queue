package uz.sh.online_queue.service.department;

import uz.sh.online_queue.criteria.department.DepartmentCriteria;
import uz.sh.online_queue.dto.department.DepartmentCreateDto;
import uz.sh.online_queue.dto.department.DepartmentDto;
import uz.sh.online_queue.dto.department.DepartmentUpdateDto;
import uz.sh.online_queue.entity.department.Department;
import uz.sh.online_queue.entity.organization.Organization;
import uz.sh.online_queue.mapper.department.DepartmentMapper;
import uz.sh.online_queue.repository.department.DepartmentRepository;
import uz.sh.online_queue.repository.organization.OrganizationRepository;
import uz.sh.online_queue.response.AppError;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.AbstractService;
import uz.sh.online_queue.utils.validator.department.DepartmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl extends AbstractService<DepartmentRepository, DepartmentMapper, DepartmentValidator>
        implements DepartmentService<DepartmentDto, DepartmentCreateDto, DepartmentUpdateDto, DepartmentCriteria, String> {

    private final OrganizationRepository organizationRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository repository, DepartmentMapper mapper, DepartmentValidator validator, OrganizationRepository organizationRepository) {
        super(repository, mapper, validator);
        this.organizationRepository = organizationRepository;
    }

    @Override
    public ResponseEntity<Data<String>> create(DepartmentCreateDto createDto, String orgId) {
        Optional<Organization> organization = organizationRepository.findOrganizationByIdAndDeletedFalse(orgId);
        if (organization.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Not found organization by this id : %s".formatted(orgId)).build()), HttpStatus.OK);

        if (organization.get().isBlocked())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.FORBIDDEN)
                    .message("This %s organization is blocked".formatted(organization.get().getName())).build()), HttpStatus.OK);

        Optional<Department> departmentByName = repository.findDepartmentByNameAndOrganizationIdAndDeletedFalse(createDto.getName(), orgId);
        if (departmentByName.isPresent())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.BAD_REQUEST)
                    .message("This %s department already exist ".formatted(createDto.getName())).build()), HttpStatus.OK);

        Department department = mapper.fromCreateDto(createDto);
        department.setOrganizationId(orgId);
        repository.save(department);
        return new ResponseEntity<>(new Data<>(department.getId()), HttpStatus.OK);
    }

    @Override
    @CachePut(value = "departments", key = "#updateDto.id")
    public ResponseEntity<Data<DepartmentDto>> update(DepartmentUpdateDto updateDto) {
        Optional<Department> departmentOptional = repository.findDepartmentByIdAndDeletedFalseAndBlockFalse(updateDto.getId());
        if (departmentOptional.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Department not found by id : '%s".formatted(updateDto.getId())).build()), HttpStatus.OK);

        Department department = mapper.fromUpdateDto(updateDto, departmentOptional.get());
        repository.save(department);
        return new ResponseEntity<>(new Data<>(mapper.toDto(department)), HttpStatus.OK);
    }

    @Override
    @CacheEvict(value = "departments",key = "#departmentId")
    public ResponseEntity<Data<Void>> delete(String departmentId) {
        Optional<Department> departmentOptional = repository.findDepartmentByIdAndDeletedFalse(departmentId);
        if (departmentOptional.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Department not found by id : '%s".formatted(departmentId)).build()), HttpStatus.OK);

        repository.deleteDepartment(departmentOptional.get().getId());
        return new ResponseEntity<>(new Data<>(true), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<List<DepartmentDto>>> getAll(DepartmentCriteria criteria, String orgId) {
        Optional<Organization> org = organizationRepository.findOrganizationByIdAndDeletedFalse(orgId);
        if (org.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Not found organization by this id : %s".formatted(orgId)).build()), HttpStatus.OK);

        if (org.get().isBlocked())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.FORBIDDEN)
                    .message("This %s organization is blocked".formatted(org.get().getName())).build()), HttpStatus.OK);

        List<Department> departments = repository.findAllByCriteria(criteria.getPage()* criteria.getSize(), criteria.getSize(), orgId);
        if (departments.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.BAD_REQUEST)
                    .message("Not have department for this %s organization yet".formatted(org.get().getName())).build()), HttpStatus.OK);

        return new ResponseEntity<>(new Data<>(mapper.toDto(departments), (long) departments.size()), HttpStatus.OK);
    }

    @Override
    @Cacheable(value = "departments",key = "#departmentId")
    public ResponseEntity<Data<DepartmentDto>> get(String departmentId) {
        Optional<Department> departmentOptional = repository.findDepartmentByIdAndDeletedFalse(departmentId);
        if (departmentOptional.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Not found department this id like %s".formatted(departmentId)).build()), HttpStatus.OK);

        if (departmentOptional.get().isBlock())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.FORBIDDEN)
                    .message("This %s department is blocked".formatted(departmentOptional.get().getName())).build()), HttpStatus.OK);

        return new ResponseEntity<>(new Data<>(mapper.toDto(departmentOptional.get())), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<Void>> block(String departmentId) {
        Optional<Department> departmentOptional = repository.findDepartmentByIdAndDeletedFalse(departmentId);
        if (departmentOptional.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Not found department this id like %s".formatted(departmentId)).build()), HttpStatus.OK);

        if (departmentOptional.get().isBlock())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.BAD_REQUEST)
                    .message("This %s department already blocked".formatted(departmentOptional.get().getName())).build()), HttpStatus.OK);

        repository.block(departmentId);
        return new ResponseEntity<>(new Data<>(true), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<Void>> unblock(String departmentId) {
        Optional<Department> departmentOptional = repository.findDepartmentByIdAndDeletedFalse(departmentId);
        if (departmentOptional.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Not found department this by id %s".formatted(departmentId)).build()), HttpStatus.OK);

        if (!departmentOptional.get().isBlock())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.BAD_REQUEST)
                    .message("Not found %s from department block list ".formatted(departmentOptional.get().getName())).build()), HttpStatus.OK);

        repository.unblock(departmentId);
        return new ResponseEntity<>(new Data<>(true), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<List<DepartmentDto>>> blockList(DepartmentCriteria criteria, String orgId) {
        Optional<Organization> org = organizationRepository.findOrganizationByIdAndDeletedFalse(orgId);
        if (org.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Not found organization by this id : %s".formatted(orgId)).build()), HttpStatus.OK);

        if (org.get().isBlocked())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.FORBIDDEN)
                    .message("This %s organization is blocked".formatted(org.get().getName())).build()), HttpStatus.OK);

        List<Department> departments = repository.findAllByCriteriaAndOrganizationId(criteria.getPage()* criteria.getSize(), criteria.getSize(), orgId);
        if (departments.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.BAD_REQUEST)
                    .message("Not have block department for this %s organization yet".formatted(orgId)).build()), HttpStatus.OK);

        return new ResponseEntity<>(new Data<>(mapper.toDto(departments), (long) departments.size()), HttpStatus.OK);
    }
}
