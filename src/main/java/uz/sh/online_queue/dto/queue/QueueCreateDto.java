package uz.sh.online_queue.dto.queue;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.sh.online_queue.dto.Dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QueueCreateDto implements Dto {
    @Size(min = 1, max = 255, message = "Department ID field length between 1 and 255")
    @NotBlank(message = "Department ID not be empty")
    private String departmentId;

    @Size(min = 1, max = 255, message = "User ID field length between 1 and 255")
    @NotBlank(message = "User ID not be empty")
    private String userId;

    @Schema(description = "Unique name for a district", example = "1", required = true)
    @NotBlank(message = "Queue not be empty")
    private int queue;

    @NotBlank(message = "Day not be empty")
    private LocalDateTime day;
}
