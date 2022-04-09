package uz.sh.online_queue.dto.auth.employee;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import uz.sh.online_queue.dto.Dto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto implements Dto {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String firstName;
    private String lastName;
    private String passportSerial;
    private String phoneNumber;
    private String role;
    private Byte experience;
    private boolean deleted;
    private boolean blocked;
    private String departmentId;
    private String organizationId;

}
