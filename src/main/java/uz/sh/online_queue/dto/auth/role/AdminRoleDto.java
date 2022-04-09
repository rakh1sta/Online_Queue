package uz.sh.online_queue.dto.auth.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.sh.online_queue.enums.Role;

import java.util.List;

@Getter
@NoArgsConstructor
public class AdminRoleDto {

    private List<String> roles;

    public List<String> getRoles() {
        roles.add(Role.SUPER_ADMIN.getName());
        roles.add(Role.ADMIN.getName());
        return roles;
    }

}
