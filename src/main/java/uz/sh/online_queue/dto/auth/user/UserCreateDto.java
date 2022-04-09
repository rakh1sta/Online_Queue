package uz.sh.online_queue.dto.auth.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.sh.online_queue.dto.Dto;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto implements Dto {

    @NotBlank(message = "first name can not be blank or null")
    private String firstName;
    @NotBlank(message = "last name can not be blank or null")
    private String lastName;
    @NotBlank(message = "phone number can not be blank or null")
    private String phoneNumber;
    @NotBlank(message = "passport serial can not be blank or null")
    private String passportSerial;
    @NotBlank(message = "passport serial can not be blank or null")
    private String password;

}
