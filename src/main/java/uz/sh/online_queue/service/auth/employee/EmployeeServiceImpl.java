package uz.sh.online_queue.service.auth.employee;

import uz.sh.online_queue.criteria.user.UserCriteria;
import uz.sh.online_queue.dto.auth.employee.EmployeeCreateDto;
import uz.sh.online_queue.dto.auth.employee.EmployeeDto;
import uz.sh.online_queue.dto.auth.employee.EmployeeUpdateDto;
import uz.sh.online_queue.entity.auth.Auth;
import uz.sh.online_queue.mapper.user.EmployeeMapper;
import uz.sh.online_queue.repository.mongorepository.AuthRepository;
import uz.sh.online_queue.repository.mongorepository.MongoTemplateRepository;
import uz.sh.online_queue.response.AppError;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.AbstractService;
import uz.sh.online_queue.utils.validator.auth.employee.EmployeeValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl extends AbstractService<AuthRepository, EmployeeMapper, EmployeeValidator>
        implements EmployeeService {

    private final MongoTemplateRepository mongoTemplateRepository;
    private final PasswordEncoder encoder;

    public EmployeeServiceImpl(AuthRepository repository, EmployeeMapper mapper, EmployeeValidator validator, MongoTemplateRepository mongoTemplateRepository, PasswordEncoder encoder) {
        super(repository, mapper,validator);
        this.mongoTemplateRepository = mongoTemplateRepository;
        this.encoder = encoder;
    }

    @Override
    public ResponseEntity<Data<String>> create(EmployeeCreateDto createDto) {
        Optional<Auth> optionalAuth = repository.findByPassportSerialAndDeletedFalse(createDto.getPassportSerial());
        if(optionalAuth.isPresent()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.CONFLICT)
                    .message("Passport serial must be checked")
                    .build()),HttpStatus.OK);
        }
        Auth auth = mapper.fromCreateDto(createDto);
        auth.setPassword(encoder.encode(auth.getPassword()));
        repository.save(auth);
        return new ResponseEntity<>(new Data<>(auth.getId()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<EmployeeDto>> update(EmployeeUpdateDto updateDto) {
        Optional<Auth> auth = repository.findByDeletedFalse(updateDto.getId());
        if(auth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("User was not found by id %s".formatted(updateDto.getId())).
                    build()),HttpStatus.OK);
        }
        if(!updateDto.getPassportSerial().isBlank()) {
            Optional<Auth> optionalAuth = repository.findByPassportSerialAndDeletedFalse(updateDto.getPassportSerial());
            if(optionalAuth.isPresent()) {
                return new ResponseEntity<>(new Data<>(AppError.builder()
                        .status(HttpStatus.CONFLICT)
                        .message("Passport serial must be checked")
                        .build()),HttpStatus.OK);
            }
        }
        mongoTemplateRepository.updateEmployee(updateDto);
        return new ResponseEntity<>(new Data<>(mapper.toDto(repository.findById(updateDto.getId()).get())),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<Void>> delete(String id) {
        Optional<Auth> auth = repository.findByDeletedFalse(id);
        if(auth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("User was not found by id %s".formatted(id))
                    .build()),HttpStatus.OK);
        }
        mongoTemplateRepository.softDelete(id);
        return new ResponseEntity<>(new Data<>(true),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<List<EmployeeDto>>> getAll(UserCriteria criteria) {
        List<Auth> authList = repository.findAllByBlockedFalseAndDeletedFalse(Pageable.ofSize(criteria.getPage()));
        if(authList.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("There are not any employees yet")
                    .build()),HttpStatus.OK);
        }
        return new ResponseEntity<>(new Data<>(mapper.toDto(authList)),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<EmployeeDto>> get(String id) {
        Optional<Auth> auth = repository.findByDeletedFalse(id);
        if(auth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Employee was not found by id %s".formatted(id))
                    .build()),HttpStatus.OK);
        }
        return new ResponseEntity<>(new Data<>(mapper.toDto(repository.findById(id).get())),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<Void>> block(String id) {
        Optional<Auth> auth = repository.findByDeletedFalse(id);
        if(auth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Employee was not found by id %s".formatted(id))
                    .build()),HttpStatus.OK);
        }
        if(auth.get().isBlocked()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.ALREADY_REPORTED)
                    .message("this user is already blocked")
                    .build()),HttpStatus.OK);
        }
        mongoTemplateRepository.block(id);
        return new ResponseEntity<>(new Data<>(true),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<Void>> unblock(String id) {
        Optional<Auth> auth = repository.findByDeletedFalse(id);
        if(auth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("User was not found by id %s".formatted(id))
                    .build()),HttpStatus.OK);
        }
        if(!auth.get().isBlocked()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.ALREADY_REPORTED)
                    .message("this user is already unblocked")
                    .build()),HttpStatus.OK);
        }
        mongoTemplateRepository.unblock(id);
        return new ResponseEntity<>(new Data<>(true),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<List<EmployeeDto>>> getAllByOrgId(UserCriteria criteria,String orgId) {
        Pageable pageable = PageRequest.of(criteria.getPage(),criteria.getSize());
        Page<Auth> authList = mongoTemplateRepository.getAllByOrgId(criteria.getRole(), pageable,orgId);
        if(authList.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("There are not any employees yet")
                    .build()),HttpStatus.OK);
        }
        List<Auth> list = authList.stream().toList();
        return new ResponseEntity<>(new Data<>(mapper.toDto(list)),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<List<EmployeeDto>>> getAllBlockedEmployees(UserCriteria criteria,String orgId) {
        Pageable pageable = PageRequest.of(criteria.getPage(),criteria.getSize());
        Page<Auth> authList = mongoTemplateRepository.getAllBlockedByOrgId(criteria.getRole(), pageable,orgId);
        if(authList.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("There are not any blocked employees yet")
                    .build()),HttpStatus.OK);
        }
        List<Auth> list = authList.stream().toList();
        return new ResponseEntity<>(new Data<>(mapper.toDto(list)),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Data<Void>> addRole(String id,String role) {
        Optional<Auth> auth = repository.findByDeletedFalse(id);
        if(auth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Employee was not found by id %s".formatted(id))
                    .build()),HttpStatus.OK);
        }
        mongoTemplateRepository.addRole(id,role);
        return new ResponseEntity<>(new Data<>(true),HttpStatus.OK);
    }

}
