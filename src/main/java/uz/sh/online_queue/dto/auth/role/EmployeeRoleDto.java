package uz.sh.online_queue.dto.auth.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.sh.online_queue.enums.Role;

import java.util.List;

@Getter
@NoArgsConstructor
public class EmployeeRoleDto {
    private List<String> roles;

    public List<String> getRoles() {
        roles.add(Role.ADMIN.getName());
        roles.add(Role.DOCTOR.getName());
        roles.add(Role.LABORATORY_ASSISTANT.getName());
        roles.add(Role.RECEPTIONIST.getName());
        return roles;
    }
}
