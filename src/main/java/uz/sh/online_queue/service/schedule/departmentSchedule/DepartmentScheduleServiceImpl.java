package uz.sh.online_queue.service.schedule.departmentSchedule;

import uz.sh.online_queue.criteria.schedule.DepartmentScheduleCriteria;
import uz.sh.online_queue.dto.schedule.departmentSchedule.DepSchedule;
import uz.sh.online_queue.dto.schedule.departmentSchedule.DepartmentScheduleCreateDto;
import uz.sh.online_queue.dto.schedule.departmentSchedule.DepartmentScheduleDto;
import uz.sh.online_queue.dto.schedule.departmentSchedule.DepartmentScheduleUpdateDto;
import uz.sh.online_queue.entity.department.Department;
import uz.sh.online_queue.entity.organization.Organization;
import uz.sh.online_queue.entity.schedule.DepartmentSchedule;
import uz.sh.online_queue.mapper.schedule.DepartmentScheduleMapper;
import uz.sh.online_queue.repository.department.DepartmentRepository;
import uz.sh.online_queue.repository.organization.OrganizationRepository;
import uz.sh.online_queue.repository.schedule.DepartmentScheduleRepository;
import uz.sh.online_queue.response.AppError;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.AbstractService;
import uz.sh.online_queue.utils.validator.schedule.DepartmentScheduleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DepartmentScheduleServiceImpl extends AbstractService<DepartmentScheduleRepository, DepartmentScheduleMapper, DepartmentScheduleValidator>
        implements DepartmentScheduleService<DepartmentScheduleDto, DepartmentScheduleCreateDto, DepartmentScheduleUpdateDto, DepartmentScheduleCriteria, String> {

    private final DepartmentRepository departmentRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public DepartmentScheduleServiceImpl(DepartmentScheduleRepository repository, DepartmentScheduleMapper mapper, DepartmentScheduleValidator validator, DepartmentRepository departmentRepository, OrganizationRepository organizationRepository) {
        super(repository, mapper, validator);
        this.departmentRepository = departmentRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public ResponseEntity<Data<String>> create(DepartmentScheduleCreateDto createDto, String departmentId) {
        Optional<Department> department = departmentRepository.findDepartmentByIdAndDeletedFalse(departmentId);
        if (department.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Department not found").build()), HttpStatus.OK);

        if (department.get().isBlock())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.FORBIDDEN)
                    .message("Department is blocked").build()), HttpStatus.OK);

        if (Objects.isNull(createDto))
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.BAD_REQUEST)
                    .message("Department schedule list is empty").build()), HttpStatus.OK);

        DepartmentSchedule departmentSchedule = mapper.fromCreateDto(createDto);
        departmentSchedule.setDepartmentId(departmentId);
        repository.save(departmentSchedule);
        return new ResponseEntity<>(new Data<>(departmentSchedule.getId()), HttpStatus.OK);
    }

    @Override
    @Cacheable(value = "departmentSchedules", key = "#departmentId")
    public ResponseEntity<Data<DepartmentScheduleDto>> get(String departmentId) {
        DepartmentSchedule allByDoctorIdAndDate = repository.findByDepartmentIdAndAndDeletedFalse(departmentId);
        if (Objects.isNull(allByDoctorIdAndDate))
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Not found department schedule for this %s  day".formatted(LocalDate.now())).build()), HttpStatus.OK);

        return new ResponseEntity<>(new Data<>(mapper.toDto(allByDoctorIdAndDate)), HttpStatus.OK);
    }

    @Override
    @CachePut(value = "departmentSchedules", key = "#updateDto.id")
    public ResponseEntity<Data<DepartmentScheduleDto>> update(DepartmentScheduleUpdateDto updateDto) {
        if (Objects.isNull(repository.findByIdAndDeletedFalse(updateDto.getId())))
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Not found department schedule ").build()), HttpStatus.OK);

        DepartmentSchedule doctorSchedule = mapper.fromUpdateDto(updateDto);
        repository.save(doctorSchedule);
        return new ResponseEntity<>(new Data<>(mapper.toDto(doctorSchedule)), HttpStatus.OK);
    }

    @Override
    @CacheEvict(value = "departmentSchedules", key = "#scheduleId")
    public ResponseEntity<Data<Void>> delete(String scheduleId) {
        DepartmentSchedule departmentSchedule = repository.findByIdAndDeletedFalse(scheduleId);
        if (Objects.isNull(departmentSchedule))
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Not found department schedule").build()), HttpStatus.OK);

        repository.deleteByIdTIme(scheduleId);
        return new ResponseEntity<>(new Data<>(true), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<List<DepSchedule>>> getAll(DepartmentScheduleCriteria criteria, String orgId) {
        Optional<Organization> byId = organizationRepository.findOrganizationByIdAndDeletedFalse(orgId);
        if (byId.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Not found organization like this %s id".formatted(orgId)).build()), HttpStatus.OK);

        if (byId.get().isBlocked())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.FORBIDDEN)
                    .message("%s organization is blocked".formatted(byId.get().getName())).build()), HttpStatus.OK);

        List<Department> allByOrganizationId = departmentRepository.findAllByCriteria(criteria.getPage()* criteria.getSize(), criteria.getSize(), orgId);
        if (allByOrganizationId.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.BAD_REQUEST)
                    .message("Not found department for this medicine : %s".formatted(byId.get().getName())).build()), HttpStatus.OK);

        List<DepSchedule> depSchedules = mapDepWorkingDay(allByOrganizationId);
        if (depSchedules.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.BAD_REQUEST)
                    .message("Not found department work time for in this medicine : %s".formatted(byId.get().getName())).build()), HttpStatus.OK);

        return new ResponseEntity<>(new Data<>(depSchedules), HttpStatus.OK);
    }

    private List<DepSchedule> mapDepWorkingDay(List<Department> department) {
        List<DepSchedule> workingDays = new ArrayList<>();
        for (Department department1 : department) {
            DepartmentSchedule workTime = repository.findByDepartmentIdAndAndDeletedFalse(department1.getId());
            if (Objects.isNull(workTime)) {
                workingDays.add(DepSchedule.childBuilder()
                        .departmentId(department1.getId())
                        .name(department1.getName())
                        .build());
            } else {
                workingDays.add(DepSchedule.childBuilder()
                        .departmentId(department1.getId())
                        .name(department1.getName())
                        .toTime(workTime.getToTime())
                        .fromTIme(workTime.getFromTIme())
                        .build());
            }
        }
        return workingDays;
    }


}
