package uz.sh.online_queue.dto.queue;

import lombok.*;
import uz.sh.online_queue.dto.GenericDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QueueUpdateDto extends GenericDto {
    @Size(min = 1, max = 255, message = "Department ID field length between 1 and 255")
    @NotNull(message = "Department ID is required")
    private String departmentId;
}
