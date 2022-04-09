package uz.sh.online_queue.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthDto {
    private String passportSerial;
    private String password;
}
