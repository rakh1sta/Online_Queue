package uz.sh.online_queue.dto.queue;

import lombok.*;
import uz.sh.online_queue.dto.GenericDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QueueDto extends GenericDto {
    @Size(min = 1, max = 255, message = "Department ID field length between 1 and 255")
    @NotNull(message = "Department ID is required")
    private String departmentId;

    @Size(min = 1, max = 255, message = "User ID field length between 1 and 255")
    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Queue Number is required")
    private int queue;

    @NotNull(message = "Day is required")
    private LocalDateTime day;
}
