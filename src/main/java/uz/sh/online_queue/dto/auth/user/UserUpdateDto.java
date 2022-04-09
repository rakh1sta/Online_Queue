package uz.sh.online_queue.dto.auth.user;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import uz.sh.online_queue.dto.Dto;
import uz.sh.online_queue.entity.auth.Auth;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto implements Dto {
    @NotBlank(message = "id can not be blank or null")
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String firstName;
    private String lastName;
    private String passportSerial;
    private String phoneNumber;

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
