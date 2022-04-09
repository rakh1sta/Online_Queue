package uz.sh.online_queue.service.queue;

import uz.sh.online_queue.criteria.queue.QueueCriteria;
import uz.sh.online_queue.dto.queue.QueueCreateDto;
import uz.sh.online_queue.dto.queue.QueueDto;
import uz.sh.online_queue.dto.queue.QueueUpdateDto;
import uz.sh.online_queue.entity.queue.Queue;
import uz.sh.online_queue.mapper.queue.QueueMapper;
import uz.sh.online_queue.repository.queue.QueueRepository;
import uz.sh.online_queue.response.AppError;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.AbstractService;
import uz.sh.online_queue.utils.validator.queue.QueueValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QueueServiceImpl extends AbstractService<QueueRepository, QueueMapper, QueueValidator> implements QueueService {
    public QueueServiceImpl(QueueRepository repository, QueueMapper mapper, QueueValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public ResponseEntity<Data<String>> create(QueueCreateDto createDto) {
        try {
            if (Objects.isNull(createDto)) {
                return new ResponseEntity<>(new Data<>("Queue create dto is null"), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Queue queue = mapper.fromCreateDto(createDto);
            if (Objects.isNull(queue)) {
                return new ResponseEntity<>(new Data<>("Queue is null"), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            repository.save(queue);
            return new ResponseEntity<>(new Data<>("Successfully created queue"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new Data<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Data<QueueDto>> update(QueueUpdateDto updateDto) {
        try {
            if (Objects.isNull(updateDto)) {
                return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("Queue update dto is null").build()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Optional<Queue> byId1 = repository.findByIdAndByDeleted(updateDto.getId());
            if (byId1.isEmpty()) {
                return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND).message("Queue not found").build()), HttpStatus.NOT_FOUND);
            }

            Queue queue = mapper.fromUpdateDto(updateDto);
            if (Objects.isNull(queue)) {
                return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("Queue is null").build()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            repository.save(queue);
            Optional<Queue> byId = repository.findByIdAndByDeleted(queue.getId());
            if (byId.isEmpty()) {
                return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.NOT_FOUND).message("Queue not found").build()), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new Data<>(mapper.toDto(byId.get())), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage()).build()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Data<Void>> delete(String id) {
        try {
            if (Objects.isNull(id)) {
                return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("ID is null").build()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Optional<Queue> optional = repository.findByIdAndByDeleted(id);
            if (optional.isEmpty()) {
                return new ResponseEntity<>(new Data<>(AppError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Queue not found").build()), HttpStatus.NOT_FOUND);
            }

            repository.updateDeleteById(id);
            return new ResponseEntity<>(new Data<>(null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage()).build()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Data<List<QueueDto>>> getAll(QueueCriteria criteria) {
        try {
            if (Objects.isNull(criteria)) {
                return new ResponseEntity<>(new Data<>(AppError
                        .builder().status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message("Queue criteria is null").build()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            List<Queue> allByCriteria = repository.findAllByCriteria(criteria.getSize(), (criteria.getPage() * criteria.getSize()));
            return new ResponseEntity<>(new Data<>(mapper.toDto(allByCriteria)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage()).build()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Data<QueueDto>> get(String id) {
        try {
            if (Objects.isNull(id)) {
                return new ResponseEntity<>(new Data<>(AppError
                        .builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message("Queue ID is null").build()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Optional<Queue> optional = repository.findByIdAndByDeleted(id);
            if (optional.isEmpty()) {
                return new ResponseEntity<>(new Data<>(
                        AppError.builder().status(HttpStatus.NOT_FOUND)
                                .message("Queue not found")
                                .build()), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new Data<>(mapper.toDto(optional.get())), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Data<>(AppError
                    .builder().status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(e.getMessage()).build()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Data<List<QueueDto>>> getAllByDepartmentId(String departmentId, QueueCriteria criteria) {
        try {
            if (Objects.isNull(departmentId) || Objects.isNull(criteria)) {
                return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("Queue criteria or department ID is null").build()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(new Data<>(mapper.toDto(repository.findAllByCriteriaAndDepartmentId(criteria.getSize(), criteria.getPage() * criteria.getSize(), departmentId))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Data<>(AppError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage()).build()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
