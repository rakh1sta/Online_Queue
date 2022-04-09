package uz.sh.online_queue.repository.schedule;

import uz.sh.online_queue.entity.schedule.DepartmentSchedule;
import uz.sh.online_queue.repository.AbstractRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DepartmentScheduleRepository extends JpaRepository<DepartmentSchedule, String>, AbstractRepository {
    DepartmentSchedule findByDepartmentIdAndAndDeletedFalse(String id);
    DepartmentSchedule findByIdAndDeletedFalse(String id);

    @Transactional
    @Modifying
    @Query("update DoctorSchedule set deleted = true where id = :id")
    void deleteByIdTIme(String id);
}
