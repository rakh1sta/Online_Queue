package uz.sh.online_queue.mapper.queue;

import uz.sh.online_queue.dto.queue.QueueCreateDto;
import uz.sh.online_queue.dto.queue.QueueDto;
import uz.sh.online_queue.dto.queue.QueueUpdateDto;
import uz.sh.online_queue.entity.queue.Queue;
import uz.sh.online_queue.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface QueueMapper extends BaseMapper<
        Queue,
        QueueDto,
        QueueCreateDto,
        QueueUpdateDto> {
}
