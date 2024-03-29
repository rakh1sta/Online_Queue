package uz.sh.online_queue.dto.auth;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChangesDto {
    @NotBlank(message = "id can not be blank or null")
    private String id;
    @NotBlank(message = "old password can not be blank or null")
    private String oldPassword;
    @NotBlank(message = "new password can not be blank or null")
    private String newPassword;
    @NotBlank(message = "confirm password can not be blank or null")
    private String confirmPassword;
}
