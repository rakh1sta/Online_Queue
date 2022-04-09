package uz.sh.online_queue.service.auth.user;

import uz.sh.online_queue.dto.auth.user.UserCreateDto;
import uz.sh.online_queue.dto.auth.user.UserDto;
import uz.sh.online_queue.dto.auth.user.UserUpdateDto;
import uz.sh.online_queue.entity.auth.Auth;
import uz.sh.online_queue.mapper.user.UserMapper;
import uz.sh.online_queue.repository.mongorepository.AuthRepository;
import uz.sh.online_queue.repository.mongorepository.MongoTemplateRepository;
import uz.sh.online_queue.response.AppError;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.AbstractService;
import uz.sh.online_queue.service.BaseService;
import uz.sh.online_queue.utils.validator.auth.user.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends AbstractService< AuthRepository, UserMapper, UserValidator> implements BaseService {

    private final MongoTemplateRepository mongoTemplateRepository;

    public UserService(AuthRepository repository, UserMapper mapper, UserValidator validator, MongoTemplateRepository mongoTemplateRepository) {
        super(repository, mapper,validator);
        this.mongoTemplateRepository = mongoTemplateRepository;
    }


    public ResponseEntity<Data<String>> create(UserCreateDto createDto) {
        Auth auth = mapper.fromCreateDto(createDto);
        Optional<Auth> optionalAuth = repository.findByPassportSerialAndDeletedFalse(createDto.getPassportSerial());
        if(optionalAuth.isPresent()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.CONFLICT)
                    .message("Passport serial must be checked")
                    .build()),HttpStatus.OK);
        }
        repository.save(auth);
        return new ResponseEntity<>(new Data<>(auth.getId().toString()), HttpStatus.OK);
    }


    public ResponseEntity<Data<UserDto>> update(UserUpdateDto updateDto) {
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
        mongoTemplateRepository.updateUser(updateDto);
        return new ResponseEntity<>(new Data<>(mapper.toDto(repository.findById(updateDto.getId()).get())),HttpStatus.OK);
    }


    public ResponseEntity<Data<UserDto>> get(String passportSerial) {
        Optional<Auth> auth = repository.findByPassportSerialAndDeletedFalse(passportSerial);
        if(auth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("User was not found by passport serial %s".formatted(passportSerial))
                    .build()),HttpStatus.OK);
        }
        return new ResponseEntity<>(new Data<>(mapper.toDto(repository.findByPassportSerialAndDeletedFalse(passportSerial).get())),HttpStatus.OK);
    }

}

