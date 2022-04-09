package uz.sh.online_queue.repository.queue;

import uz.sh.online_queue.entity.queue.Queue;
import uz.sh.online_queue.repository.AbstractRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface QueueRepository extends JpaRepository<Queue, String>, AbstractRepository {
    @Transactional
    @Modifying
    @Query("update Queue set deleted = true where id = :id and deleted = false")
    void updateDeleteById(String id);

    @Transactional
    @Query(value = "select * from public.queue q where q.deleted = false limit :size offset :page", nativeQuery = true)
    List<Queue> findAllByCriteria(Integer size, Integer page);

    @Transactional
    @Query("from Queue where id = :id and deleted = false")
    Optional<Queue> findByIdAndByDeleted(String id);

    @Transactional
    @Query(value = "select * from public.queue q where q.deleted = false and q.department_id = :departmentId limit :size offset :page", nativeQuery = true)
    List<Queue> findAllByCriteriaAndDepartmentId(Integer size, Integer page, String departmentId);
}
