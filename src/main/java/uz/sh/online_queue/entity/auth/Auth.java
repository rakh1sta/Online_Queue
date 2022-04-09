package uz.sh.online_queue.entity.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import uz.sh.online_queue.entity.BaseEntity;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Document("auth")
public class Auth implements BaseEntity, GrantedAuthority {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String firstName;
    private String lastName;
    private String passportSerial;
    private String phoneNumber;
    private String role;
    private Byte experience;
    private String password;
    private boolean deleted;
    private boolean blocked;
    private String departmentId;
    private String organizationId;


    @Builder(builderMethodName = "childBuilder")
    public Auth(String id, String firstName, String lastName, String passportSerial, String phoneNumber, String role, Byte experience, String password, boolean deleted, boolean blocked, String departmentId, String organizationId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportSerial = passportSerial;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.experience = experience;
        this.password = password;
        this.deleted = deleted;
        this.blocked = blocked;
        this.departmentId = departmentId;
        this.organizationId = organizationId;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Auth other = (Auth) obj;
        return Objects.equals(id,other.getId());
    }


}
