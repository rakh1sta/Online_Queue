package uz.sh.online_queue.service.schedule.doctorSchedule;

import uz.sh.online_queue.criteria.schedule.DoctorScheduleCriteria;
import uz.sh.online_queue.dto.schedule.doctorSchedule.DocSchedule;
import uz.sh.online_queue.dto.schedule.doctorSchedule.DoctorScheduleCreateDto;
import uz.sh.online_queue.dto.schedule.doctorSchedule.DoctorScheduleDto;
import uz.sh.online_queue.dto.schedule.doctorSchedule.DoctorScheduleUpdateDto;
import uz.sh.online_queue.entity.auth.Auth;
import uz.sh.online_queue.entity.organization.Organization;
import uz.sh.online_queue.entity.schedule.DoctorSchedule;
import uz.sh.online_queue.mapper.schedule.DoctorScheduleMapper;
import uz.sh.online_queue.repository.mongorepository.AuthRepository;
import uz.sh.online_queue.repository.organization.OrganizationRepository;
import uz.sh.online_queue.repository.schedule.DoctorScheduleRepository;
import uz.sh.online_queue.response.AppError;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.AbstractService;
import uz.sh.online_queue.utils.validator.schedule.DoctorScheduleValidator;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DoctorScheduleServiceImpl extends AbstractService<DoctorScheduleRepository, DoctorScheduleMapper, DoctorScheduleValidator>
        implements DoctorScheduleService<DoctorScheduleDto, DoctorScheduleCreateDto, DoctorScheduleUpdateDto, DoctorScheduleCriteria, String> {

    private final AuthRepository authRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public DoctorScheduleServiceImpl(DoctorScheduleRepository repository, DoctorScheduleMapper mapper, DoctorScheduleValidator validator, AuthRepository authRepository, OrganizationRepository organizationRepository) {
        super(repository, mapper, validator);
        this.authRepository = authRepository;
        this.organizationRepository = organizationRepository;
    }


    @Override
    public ResponseEntity<Data<Map<LocalDate,String>>> create(List<DoctorScheduleCreateDto> createDtoList, String doctorId) {
        Optional<Auth> auth = authRepository.findByIdAndDeletedFalse(doctorId);
        if (auth.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Doctor not found").build()), HttpStatus.OK);

        if (auth.get().isBlocked())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.FORBIDDEN)
                    .message("Doctor Blocked").build()), HttpStatus.OK);

        if (createDtoList.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.BAD_REQUEST)
                    .message("list can not be empty ").build()), HttpStatus.OK);

        Map<LocalDate, String> ids = mapResponse(createDtoList, doctorId);
        return new ResponseEntity<>(new Data<>(ids), HttpStatus.OK);
    }

    @Override
    @CachePut(value = "doctorSchedules", key = "#updateDto.id")
    public ResponseEntity<Data<DoctorScheduleDto>> update(DoctorScheduleUpdateDto updateDto) {
        if (repository.findDoctorScheduleByIdAndDeletedFalse(updateDto.getId()).isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Not found ").build()), HttpStatus.OK);

        DoctorSchedule doctorSchedule = mapper.fromUpdateDto(updateDto);
        repository.save(doctorSchedule);
        return new ResponseEntity<>(new Data<>(mapper.toDto(doctorSchedule)), HttpStatus.OK);
    }

    @Override
    @CacheEvict(value = "doctorSchedules", key = "#doctorScheduleId")
    public ResponseEntity<Data<Void>> delete(String doctorScheduleId) {
        Optional<DoctorSchedule> workingDayOptional = repository.findDoctorScheduleByIdAndDeletedFalse(doctorScheduleId);
        if (workingDayOptional.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Date not found that you chosen").build()), HttpStatus.OK);

        repository.deleteByIdTIme(workingDayOptional.get().getId());
        return new ResponseEntity<>(new Data<>(true), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<List<DocSchedule>>> getAll(DoctorScheduleCriteria criteria, String OrgID) {
        Optional<Organization> byId = organizationRepository.findOrganizationByIdAndDeletedFalseAndBlockedFalse(OrgID);
        if (byId.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND)
                    .message("Not found organization like this %s id".formatted(OrgID)).build()), HttpStatus.OK);

        List<Auth> allByOrganizationId = authRepository.findAllByOrganizationIdAndRoleAndBlockedFalseAndDeletedFalse(PageRequest.of(criteria.getPage(), criteria.getSize()), OrgID, "doctor");
        if (allByOrganizationId.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.BAD_REQUEST)
                    .message("Not found doctors for this medicine : %s".formatted(byId.get().getName())).build()), HttpStatus.OK);

        List<DocSchedule> docSchedules = mapDoctorWorkingDay(allByOrganizationId);
        if (docSchedules.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.BAD_REQUEST)
                    .message("Not found doctors work time for in this medicine : %s".formatted(byId.get().getName())).build()), HttpStatus.OK);

        return new ResponseEntity<>(new Data<>(docSchedules), HttpStatus.OK);
    }

    @Override
    @Cacheable(value = "doctorSchedules", key = "#doctorId")
    public ResponseEntity<Data<DoctorScheduleDto>> get(String doctorId) {
        Optional<DoctorSchedule> allByDoctorIdAndDate = repository.findByDoctorIdAndDateAndDeletedFalse(doctorId, LocalDate.now());
        if (allByDoctorIdAndDate.isEmpty())
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.BAD_GATEWAY)
                    .message("Not found doctors work time for this %s  day".formatted(LocalDate.now())).build()), HttpStatus.OK);

        return new ResponseEntity<>(new Data<>(mapper.toDto(allByDoctorIdAndDate.get())), HttpStatus.OK);
    }

    private List<DocSchedule> mapDoctorWorkingDay(List<Auth> users) {
        List<DocSchedule> workingDays = new ArrayList<>();
        for (Auth auth : users) {
            Optional<DoctorSchedule> workTime = repository.findByDoctorIdAndDateAndDeletedFalse(auth.getId(), LocalDate.now());
            if (workTime.isEmpty()) {
                workingDays.add(DocSchedule.childBuilder()
                        .id(auth.getId())
                        .firstName(auth.getFirstName())
                        .lastName(auth.getLastName())
                        .build());
            } else {
                workingDays.add(DocSchedule.childBuilder()
                        .id(auth.getId())
                        .firstName(auth.getFirstName())
                        .lastName(auth.getLastName())
                        .date(workTime.get().getDate())
                        .to(workTime.get().getToTime())
                        .from(workTime.get().getFromTIme())
                        .build());
            }
        }
        return workingDays;
    }

    private Map<LocalDate, String> mapResponse(List<DoctorScheduleCreateDto> createDtoList, String doctorId) {
        Map<LocalDate,String> ids = Maps.newHashMap();
        for (DoctorScheduleCreateDto createDto : createDtoList) {
            Optional<DoctorSchedule> byDoctorIdAndDate = repository.findByDoctorIdAndDateAndDeletedFalse(doctorId, createDto.getDate());
            if (byDoctorIdAndDate.isPresent()) {
                ids.put(createDto.getDate(),"Work time already exist for this date ⚠⚠");
                continue;
            }
            DoctorSchedule doctorSchedule = mapper.fromCreateDto(createDto);
            doctorSchedule.setDoctorId(doctorId);
            repository.save(doctorSchedule);
            ids.put(doctorSchedule.getDate(),"Successfully created");
        }
        return ids;
    }


}
