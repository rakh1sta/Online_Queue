package uz.sh.online_queue.repository.mongorepository;

import uz.sh.online_queue.entity.auth.Auth;
import uz.sh.online_queue.repository.AbstractRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AuthRepository extends MongoRepository<Auth, String>, AbstractRepository {

    Optional<Auth> findByPassportSerialAndDeletedFalse(String passportSerial);

    Optional<Auth> findByPasswordAndPassportSerialAndDeletedFalse(String password, String passportSerial);


    Optional<Auth> findByDeletedFalse(String id);

    Optional<Auth> findByIdAndDeletedFalse(String id);

    List<Auth> findAllByBlockedFalseAndDeletedFalse(Pageable pageable);

    List<Auth> findAllByOrganizationIdAndRoleAndBlockedFalseAndDeletedFalse(Pageable pageable, String id, String role);



    Optional<Auth> findByIdAndRoleAndDeletedFalse(String id,String role);

    Optional<Auth> findByIdAndPasswordAndDeletedFalse(String id, String oldPassword);
}
