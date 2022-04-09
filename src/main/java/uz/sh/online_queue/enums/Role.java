package uz.sh.online_queue.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    SUPER_ADMIN("super_admin"),
    ADMIN("admin"),
    RECEPTIONIST("receptionist"),
    LABORATORY_ASSISTANT("laboratory_assistant"),
    DOCTOR("doctor");

    private final String name;

}
