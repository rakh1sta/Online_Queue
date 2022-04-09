package uz.sh.online_queue.service.auth.employee;

import uz.sh.online_queue.criteria.user.UserCriteria;
import uz.sh.online_queue.dto.auth.employee.AdminCreateDto;
import uz.sh.online_queue.dto.auth.employee.AdminDto;
import uz.sh.online_queue.dto.auth.employee.AdminUpdateDto;
import uz.sh.online_queue.entity.auth.Auth;
import uz.sh.online_queue.mapper.user.AdminMapper;
import uz.sh.online_queue.repository.mongorepository.AuthRepository;
import uz.sh.online_queue.repository.mongorepository.MongoTemplateRepository;
import uz.sh.online_queue.response.AppError;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.AbstractService;
import uz.sh.online_queue.service.BaseService;
import uz.sh.online_queue.utils.validator.auth.employee.EmployeeValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Component
@Service
public class AdminCrudService extends AbstractService<AuthRepository, AdminMapper, EmployeeValidator> implements BaseService {

    private final MongoTemplateRepository mongoTemplateRepository;
    private final PasswordEncoder encoder;

    public AdminCrudService(AuthRepository repository, AdminMapper mapper, EmployeeValidator validator, MongoTemplateRepository mongoTemplateRepository, PasswordEncoder encoder) {
        super(repository, mapper, validator);
        this.mongoTemplateRepository = mongoTemplateRepository;
        this.encoder = encoder;
    }


    public ResponseEntity<Data<String>> create(AdminCreateDto createDto) {
        Optional<Auth> optionalAuth = repository.findByPassportSerialAndDeletedFalse(createDto.getPassportSerial());
        if(!optionalAuth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.CONFLICT)
                    .message("Passport serial must be checked")
                    .build()),HttpStatus.OK);
        }
        Auth auth = mapper.fromCreateDto(createDto);
        auth.setPassword(encoder.encode(auth.getPassword()));
        repository.save(auth);
        return new ResponseEntity<>(new Data<>(auth.getId().toString()), HttpStatus.OK);
    }


    public ResponseEntity<Data<AdminDto>> update(AdminUpdateDto updateDto) {
        Optional<Auth> auth = repository.findById(updateDto.getId());
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
        mongoTemplateRepository.updateAdmin(updateDto);
        return new ResponseEntity<>(new Data<>(mapper.toDto(repository.findById(updateDto.getId()).get())),HttpStatus.OK);
    }


    public ResponseEntity<Data<Void>> delete(String id) {
        Optional<Auth> auth = repository.findById(id);
        if(auth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("User was not found by id %s".formatted(id))
                    .build()),HttpStatus.OK);
        }
        mongoTemplateRepository.softDelete(id);
        return new ResponseEntity<>(new Data<>(true),HttpStatus.OK);
    }


    public ResponseEntity<Data<List<AdminDto>>> getAll(UserCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        if (criteria.getRole().isBlank()) {
            criteria.setRole("admin");
        }
        Page<Auth> authList = mongoTemplateRepository.getAll(criteria.getRole(), pageable);
        if(authList.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("There are not any admins yet")
                    .build()),HttpStatus.OK);
        }
        List<Auth> list = authList.stream().toList();
        return new ResponseEntity<>(new Data<>(mapper.toDto(list)),HttpStatus.OK);
    }


    public ResponseEntity<Data<AdminDto>> get(String id) {
        Optional<Auth> auth = repository.findByIdAndRoleAndDeletedFalse(id,"admin");
        if(auth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("User was not found by id %s".formatted(id))
                    .build()),HttpStatus.OK);
        }
        return new ResponseEntity<>(new Data<>(mapper.toDto(repository.findById(id).get())),HttpStatus.OK);
    }

    public ResponseEntity<Data<List<AdminDto>>> getAllBlocked(UserCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        if (criteria.getRole().isBlank()) {
            criteria.setRole("admin");
        }
        Page<Auth> authList = mongoTemplateRepository.getAllBlocked(criteria.getRole(),pageable);
        if(authList.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("There are not any blocked admins yet")
                    .build()),HttpStatus.OK);
        }
        List<Auth> list = authList.stream().toList();
        return new ResponseEntity<>(new Data<>(mapper.toDto(list)),HttpStatus.OK);

    }

    public ResponseEntity<Data<Void>> block(String id) {
        Optional<Auth> auth = repository.findByIdAndRoleAndDeletedFalse(id,"admin");
        if(auth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("User was not found by id %s".formatted(id))
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


    public ResponseEntity<Data<Void>> unblock(String id) {
        Optional<Auth> auth = repository.findByIdAndRoleAndDeletedFalse(id,"admin");
        if (auth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Admin was not found by id %s".formatted(id))
                    .build()), HttpStatus.OK);
        }
        if (!auth.get().isBlocked()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.ALREADY_REPORTED)
                    .message("this admin is already unblocked")
                    .build()), HttpStatus.OK);
        }
        mongoTemplateRepository.unblock(id);
        return new ResponseEntity<>(new Data<>(true), HttpStatus.OK);
    }

    public ResponseEntity<Data<Void>> addRole(String id, String role) {
        return null;
    }
}
