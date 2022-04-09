package uz.sh.online_queue.dto.auth.employee;

import lombok.*;
import uz.sh.online_queue.dto.Dto;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminCreateDto implements Dto {
    @NotBlank(message = "first name can not be blank or null")
    private String firstName;
    @NotBlank(message = "last name can not be blank or null")
    private String lastName;
    @NotBlank(message = "passport serial name can not be blank or null")
    private String passportSerial;
    @NotBlank(message = "phone number can not be blank or null")
    private String phoneNumber;
    private String role;
    private Byte experience;
    @NotBlank(message = "password can not be blank or null")
    private String password;
    @NotBlank(message = "organization id can not be blank or null")
    private String organizationId;
}
