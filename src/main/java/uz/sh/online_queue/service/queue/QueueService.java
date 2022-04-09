package uz.sh.online_queue.service.queue;

import uz.sh.online_queue.criteria.queue.QueueCriteria;
import uz.sh.online_queue.dto.queue.QueueCreateDto;
import uz.sh.online_queue.dto.queue.QueueDto;
import uz.sh.online_queue.dto.queue.QueueUpdateDto;
import uz.sh.online_queue.entity.queue.Queue;
import uz.sh.online_queue.service.BaseService;
import uz.sh.online_queue.service.GenericCrudService;

public interface QueueService extends GenericCrudService<
        Queue,
        QueueDto,
        QueueCreateDto,
        QueueUpdateDto,
        QueueCriteria,
        String>,
        BaseService {
}
