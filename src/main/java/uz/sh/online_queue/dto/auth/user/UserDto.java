package uz.sh.online_queue.dto.auth.user;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import uz.sh.online_queue.dto.Dto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Dto {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String passportSerial;


}