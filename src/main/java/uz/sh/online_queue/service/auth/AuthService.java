package uz.sh.online_queue.service.auth;

import uz.sh.online_queue.dto.auth.AuthDto;
import uz.sh.online_queue.dto.auth.PasswordChangesDto;
import uz.sh.online_queue.dto.auth.SessionDto;
import uz.sh.online_queue.entity.auth.Auth;
import uz.sh.online_queue.properties.ServerProperties;
import uz.sh.online_queue.repository.mongorepository.AuthRepository;
import uz.sh.online_queue.repository.mongorepository.MongoTemplateRepository;
import uz.sh.online_queue.response.AppError;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.BaseService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class AuthService implements BaseService , UserDetailsService {

    private final AuthRepository repository;
    private final ServerProperties serverProperties;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder encoder;
    private final MongoTemplateRepository mongoTemplateRepository;


    public AuthService(AuthRepository repository, ServerProperties serverProperties, ObjectMapper objectMapper, PasswordEncoder encoder, MongoTemplateRepository mongoTemplateRepository) {
        this.repository = repository;
        this.serverProperties = serverProperties;
        this.objectMapper = objectMapper;
        this.encoder = encoder;
        this.mongoTemplateRepository = mongoTemplateRepository;
    }

    public ResponseEntity<Data<AuthDto>> login (String passportSerial) {
        Optional<Auth> auth = repository.findByPassportSerialAndDeletedFalse(passportSerial);
        if(auth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("user was not found by passport serial %s".formatted(passportSerial))
                    .build()), HttpStatus.OK);
        }

        return new ResponseEntity<>(new Data<>(mapping(auth.get())),HttpStatus.OK);
    }

    public ResponseEntity<Data<AuthDto>> loginByPassword(String password,String passportSerial) {
        Optional<Auth> auth = repository.findByPasswordAndPassportSerialAndDeletedFalse(encoder.encode(password),passportSerial);
        if(auth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Bad Credentials")
                    .build()), HttpStatus.OK);
        }
        if(auth.get().isBlocked()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .message("You were blocked")
                    .build()),HttpStatus.OK);
        }
        return new ResponseEntity<>(new Data<>(mapping(auth.get())),HttpStatus.OK);
    }


    private AuthDto mapping(Auth auth) {
        AuthDto authDto = AuthDto.builder()
                .passportSerial(auth.getPassportSerial())
                .password(auth.getPassword())
                .build();
        return authDto;
    }

    public ResponseEntity<Data<SessionDto>> getToken(AuthDto dto) {

        try {

            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(serverProperties.getServerUrl() + "/api/v1/auth/login");
            byte[] bytes = objectMapper.writeValueAsBytes(dto);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            httppost.setEntity(new InputStreamEntity(byteArrayInputStream));

            HttpResponse response = httpclient.execute(httppost);

            JsonNode json_auth = objectMapper.readTree(EntityUtils.toString(response.getEntity()));

            if (json_auth.has("success") && json_auth.get("success").asBoolean()) {
                JsonNode node = json_auth.get("data");
                SessionDto sessionDto = objectMapper.readValue(node.toString(), SessionDto.class);
                return new ResponseEntity<>(new Data<>(sessionDto), HttpStatus.OK);
            }
            return new ResponseEntity<>(new Data<>(objectMapper.readValue(json_auth.get("error").toString(),
                    AppError.class)), HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .message(e.getLocalizedMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build()), HttpStatus.OK);
        }
    }

    public ResponseEntity<Data<SessionDto>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String passportSerial) throws UsernameNotFoundException {
        Auth user = repository.findByPassportSerialAndDeletedFalse(passportSerial).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found");
        });
        return User.builder()
                .username(user.getPassportSerial())
                .password(user.getPassword())
                .authorities(user.getAuthority())
                .accountLocked(false)
                .accountExpired(false)
                .disabled(false)
                .credentialsExpired(false)
                .build();
    }

    public ResponseEntity<Data<Void>> changePassword(PasswordChangesDto dto) {
        Optional<Auth> auth = repository.findByIdAndPasswordAndDeletedFalse(dto.getId(),encoder.encode(dto.getOldPassword()));
        if(auth.isEmpty()) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Bad request")
                    .build()),HttpStatus.OK);
        }
        if(!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            return new ResponseEntity<>(new Data<>(AppError.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Bad request")
                    .build()),HttpStatus.OK);
        }
        mongoTemplateRepository.changePassword(new ObjectId(dto.getId()),encoder.encode(dto.getConfirmPassword()));
        return new ResponseEntity<>(new Data<>(true),HttpStatus.OK);
    }

}
